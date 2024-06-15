package com.ismhac.jspace.service.common.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.payment.request.PaymentRequest;
import com.ismhac.jspace.mapper.PurchaseHistoryMapper;
import com.ismhac.jspace.mapper.PurchasedProductMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import com.ismhac.jspace.repository.CartRepository;
import com.ismhac.jspace.repository.PurchaseHistoryRepository;
import com.ismhac.jspace.repository.PurchasedProductRepository;
import com.ismhac.jspace.service.common.PaymentService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final APIContext apiContext;
    private final CartRepository cartRepository;
    private final PurchasedProductRepository purchasedProductRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CandidateFollowCompanyRepository candidateFollowCompanyRepository;

    @Override
    public Payment requestPayment(PaymentRequest request) {
        Payment createdPayment;
        try {
            Transaction transaction = createTransaction(request);
            Payment payment = getPayment(request, transaction);
            createdPayment = payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(String.valueOf(e.getDetails()));
        }
        return createdPayment;
    }

    private Transaction createTransaction(PaymentRequest request) {
        Amount amount = new Amount(request.getCurrency(), request.getTotal());
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        JSONObject customParams = new JSONObject();
        customParams.put("cartIds", request.getCartIds());
        transaction.setCustom(customParams.toString());

        return transaction;
    }

    public static Payment getPayment(PaymentRequest request, Transaction transaction) {
        List<Transaction> transactions = Collections.singletonList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(request.getPaymentMethod());

        Payment payment = new Payment();
        payment.setIntent(request.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(request.getCancelUrl());
        redirectUrls.setReturnUrl(request.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }

    /* */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object handleResponse(String body) {
        Gson gson = new Gson();
        Map<String, Object> bodyObj = gson.fromJson(body, new TypeToken<Map<String, Object>>() {
        }.getType());
        Map<String, Object> resource = (Map<String, Object>) bodyObj.get("resource");
        List<Map<String, Object>> transactions = (List<Map<String, Object>>) resource.get("transactions");
        Map<String, Object> payer = (Map<String, Object>) resource.get("payer");
        String paymentMethod = (String) payer.get("payment_method");
        String status = (String) payer.get("status");
        Map<String, Object> amount = (Map<String, Object>) transactions.get(0).get("amount");
        String total = (String) amount.get("total");
        String custom = (String) transactions.get(0).get("custom");
        Map<String, List<Double>> customObj = gson.fromJson(custom, new TypeToken<Map<String, Object>>() {
        }.getType());
        List<Integer> cartIds = customObj.get("cartIds").stream().map(Double::intValue).collect(Collectors.toList());

        return processPurchasedProducts(cartIds, paymentMethod, status);
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
