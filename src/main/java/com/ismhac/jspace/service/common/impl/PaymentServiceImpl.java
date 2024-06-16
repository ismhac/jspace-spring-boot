package com.ismhac.jspace.service.common.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.mapper.PurchaseHistoryMapper;
import com.ismhac.jspace.mapper.PurchasedProductMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import com.ismhac.jspace.repository.CartRepository;
import com.ismhac.jspace.repository.PurchaseHistoryRepository;
import com.ismhac.jspace.repository.PurchasedProductRepository;
import com.ismhac.jspace.service.common.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final CartRepository cartRepository;
    private final PurchasedProductRepository purchasedProductRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CandidateFollowCompanyRepository candidateFollowCompanyRepository;

    /* */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object handleResponse(String body) {
        Gson gson = new Gson();
        try {
            Map<String, Object> bodyObj = gson.fromJson(body, new TypeToken<Map<String, Object>>() {
            }.getType());
            Map<String, Object> resource = (Map<String, Object>) bodyObj.get("resource");
            List<Map<String, Object>> transactions = (List<Map<String, Object>>) resource.get("transactions");
            Map<String, Object> payer = (Map<String, Object>) resource.get("payer");
            String paymentMethod = (String) payer.get("payment_method");
            String status = (String) payer.get("status");
            String custom = (String) transactions.get(0).get("custom");
            List<Integer> cartIds = gson.fromJson(custom, new TypeToken<List<Integer>>() {
            }.getType());
            return processPurchasedProducts(cartIds, paymentMethod, status);
        } catch (Exception e) {
            log.error("Error processing PayPal webhook response: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing PayPal webhook response", e);
        }
    }

    private Object processPurchasedProducts(List<Integer> cartIds, String paymentMethod, String status) {
        List<PurchasedProduct> purchasedProducts = new ArrayList<>();
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();

        for (Integer item : cartIds) {
            Optional<Cart> cartOptional = cartRepository.findById(item);
            cartOptional.ifPresent(cart -> {
                Company company = cart.getCompany();
                Product product = cart.getProduct();
                int quantity = cart.getQuantity();

                PurchaseHistory purchaseHistory = createPurchaseHistory(company, product, quantity, paymentMethod, status);
                PurchasedProduct purchasedProduct = createPurchasedProduct(company, product, quantity);

                purchaseHistories.add(purchaseHistory);
                purchasedProducts.add(purchasedProduct);

                cartRepository.delete(cart);
            });
        }

        Map<String, Object> response = new HashMap<>();
        response.put("purchaseHistory", PurchaseHistoryMapper.instance.eListToDtoList(purchaseHistoryRepository.saveAll(purchaseHistories), candidateFollowCompanyRepository));
        response.put("purchasedProduct", PurchasedProductMapper.instance.eListToDtoList(purchasedProductRepository.saveAll(purchasedProducts), candidateFollowCompanyRepository));
        return response;
    }

    private PurchaseHistory createPurchaseHistory(Company company, Product product, int quantity, String paymentMethod, String status) {
        return PurchaseHistory.builder()
                .company(company)
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productNumberOfPost(product.getNumberOfPost())
                .productPostDuration(product.getPostDuration())
                .productDurationDayNumber(product.getDurationDayNumber())
                .expiryDate(LocalDate.now().plusDays(product.getDurationDayNumber()))
                .description(product.getDescription())
                .quantity(quantity)
                .totalPrice(product.getPrice() * quantity)
                .paymentMethod(paymentMethod)
                .status(status)
                .build();
    }

    private PurchasedProduct createPurchasedProduct(Company company, Product product, int quantity) {
        return PurchasedProduct.builder()
                .company(company)
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productNumberOfPost(product.getNumberOfPost() * quantity)
                .productPostDuration(product.getPostDuration())
                .productDurationDayNumber(product.getDurationDayNumber())
                .expiryDate(LocalDate.now().plusDays(product.getDurationDayNumber()))
                .description(product.getDescription())
                .quantity(quantity)
                .totalPrice(product.getPrice() * quantity)
                .build();
    }
}
