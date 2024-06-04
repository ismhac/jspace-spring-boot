package com.ismhac.jspace.service.common.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.dto.payment.request.PaymentCreateRequestV2;
import com.ismhac.jspace.mapper.PurchaseHistoryMapper;
import com.ismhac.jspace.mapper.PurchasedProductMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.common.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {

    private final APIContext apiContext;

    private final PurchasedProductRepository purchasedProductRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    private final CompanyRepository companyRepository;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    @Override
    public Payment createPayment(PaymentCreateRequest paymentCreateRequest) {
        Payment createPayment;
        try {
            Amount amount = new Amount(
                    paymentCreateRequest.getCurrency(),
                    paymentCreateRequest.getTotal());

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);

            // Add custom parameters
            JSONObject customParams = new JSONObject();
            customParams.put("companyId", paymentCreateRequest.getCompanyId());
            customParams.put("productId", paymentCreateRequest.getProductId());
            customParams.put("quantity", paymentCreateRequest.getQuantity());
            transaction.setCustom(customParams.toString());

            Payment payment = getPayment(paymentCreateRequest, transaction);

            createPayment = payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(String.valueOf(e.getDetails()));
        }
        return createPayment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object listenPaypalWebhooks(String body) {

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

        Map<String, Object> customObj = gson.fromJson(custom, new TypeToken<Map<String, Object>>() {
        }.getType());

        int companyId = ((Double) customObj.get("companyId")).intValue();
        int productId = ((Double) customObj.get("productId")).intValue();
        int quantity = ((Double) customObj.get("quantity")).intValue();

        Optional<Company> company = companyRepository.findById(companyId);

        Optional<Product> product = productRepository.findById(productId);

        PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                .company(company.get())
                .productName(product.get().getName())
                .productPrice(product.get().getPrice())
                .productNumberOfPost(product.get().getNumberOfPost())
                .productPostDuration(product.get().getPostDuration())
                .productDurationDayNumber(product.get().getDurationDayNumber())
                .expiryDate(LocalDate.now().plusDays(product.get().getDurationDayNumber()))
                .description(product.get().getDescription())
                .quantity(quantity)
                .totalPrice(product.get().getPrice() * quantity)
                .paymentMethod(paymentMethod)
                .status(status)
                .build();

        PurchasedProduct purchasedProduct = PurchasedProduct.builder()
                .company(company.get())
                .productName(product.get().getName())
                .productPrice(product.get().getPrice())
                .productNumberOfPost(product.get().getNumberOfPost() * quantity)
                .productPostDuration(product.get().getPostDuration())
                .productDurationDayNumber(product.get().getDurationDayNumber())
                .expiryDate(LocalDate.now().plusDays(product.get().getDurationDayNumber()))
                .description(product.get().getDescription())
                .quantity(quantity)
                .totalPrice(product.get().getPrice() * quantity)
                .build();

        PurchaseHistory savedPurchaseHistory = purchaseHistoryRepository.save(purchaseHistory);

        PurchasedProduct savedPurchasedProduct = purchasedProductRepository.save(purchasedProduct);

        return new HashMap<>() {{
            put("purchaseHistory", PurchaseHistoryMapper.instance.eToDto(savedPurchaseHistory));
            put("purchasedProduct", PurchasedProductMapper.instance.eToDto(savedPurchasedProduct));
        }};
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Object listenPaypalWebhooksV2(String body) {
//        Gson gson = new Gson();
//
//        Map<String, Object> bodyObj = gson.fromJson(body, new TypeToken<Map<String, Object>>() {
//        }.getType());
//
//        Map<String, Object> resource = (Map<String, Object>) bodyObj.get("resource");
//
//        List<Map<String, Object>> transactions = (List<Map<String, Object>>) resource.get("transactions");
//
//        Map<String, Object> payer = (Map<String, Object>) resource.get("payer");
//
//        String paymentMethod = (String) payer.get("payment_method");
//        String status = (String) payer.get("status");
//
//        Map<String, Object> amount = (Map<String, Object>) transactions.get(0).get("amount");
//
//        String total = (String) amount.get("total");
//
//        String custom = (String) transactions.get(0).get("custom");
//
//        Map<String, List<Double>> customObj = gson
//                .fromJson(custom, new TypeToken<Map<String, Object>>() {}.getType());
//
//        List<Integer> cartIds = (customObj.get("cartIds")).stream().map(Double::intValue).toList();
//
//        List<PurchasedProduct> purchasedProducts = new ArrayList<>();
//        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
//
//        for(Integer item: cartIds){
//            Optional<Cart> cart = cartRepository.findById(item);
//            Company company = cart.get().getCompany();
//            Product product = cart.get().getProduct();
//            int quantity =cart.get().getQuantity();
//
//            PurchaseHistory purchaseHistory = PurchaseHistory.builder()
//                    .company(company)
//                    .productName(product.getName())
//                    .productPrice(product.getPrice())
//                    .productNumberOfPost(product.getNumberOfPost())
//                    .productPostDuration(product.getPostDuration())
//                    .productDurationDayNumber(product.getDurationDayNumber())
//                    .expiryDate(LocalDate.now().plusDays(product.getDurationDayNumber()))
//                    .description(product.getDescription())
//                    .quantity(quantity)
//                    .totalPrice(product.getPrice() * quantity)
//                    .paymentMethod(paymentMethod)
//                    .status(status)
//                    .build();
//
//            PurchasedProduct purchasedProduct = PurchasedProduct.builder()
//                    .company(company)
//                    .productName(product.getName())
//                    .productPrice(product.getPrice())
//                    .productNumberOfPost(product.getNumberOfPost() * quantity)
//                    .productPostDuration(product.getPostDuration())
//                    .productDurationDayNumber(product.getDurationDayNumber())
//                    .expiryDate(LocalDate.now().plusDays(product.getDurationDayNumber()))
//                    .description(product.getDescription())
//                    .quantity(quantity)
//                    .totalPrice(product.getPrice() * quantity)
//                    .build();
//
//            purchaseHistories.add(purchaseHistory);
//            purchasedProducts.add(purchasedProduct);
//
//            cartRepository.delete(cart.get());
//        }
//
//        return new HashMap<>() {{
//            put("purchaseHistory", PurchaseHistoryMapper.instance.eListToDtoList(purchaseHistoryRepository.saveAll(purchaseHistories)));
//            put("purchasedProduct", PurchasedProductMapper.instance.eListToDtoList(purchasedProductRepository.saveAll(purchasedProducts)));
//        }};
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object listenPaypalWebhooksV2(String body) {
        Gson gson = new Gson();

        Map<String, Object> bodyObj = gson.fromJson(body, new TypeToken<Map<String, Object>>() {}.getType());

        Map<String, Object> resource = (Map<String, Object>) bodyObj.get("resource");

        List<Map<String, Object>> transactions = (List<Map<String, Object>>) resource.get("transactions");

        Map<String, Object> payer = (Map<String, Object>) resource.get("payer");

        String paymentMethod = (String) payer.get("payment_method");
        String status = (String) payer.get("status");

        Map<String, Object> amount = (Map<String, Object>) transactions.get(0).get("amount");

        String total = (String) amount.get("total");

        String custom = (String) transactions.get(0).get("custom");

        Map<String, List<Double>> customObj = gson
                .fromJson(custom, new TypeToken<Map<String, Object>>() {}.getType());

        List<Integer> cartIds = (customObj.get("cartIds")).stream().map(Double::intValue).toList();

        List<PurchasedProduct> purchasedProducts = new ArrayList<>();
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();

        List<Cart> carts = cartRepository.findAllById(cartIds);

        for(Cart cart: carts){
            Company company = cart.getCompany();
            Product product = cart.getProduct();
            int quantity = cart.getQuantity();

            PurchaseHistory purchaseHistory = PurchaseHistory.builder()
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

            PurchasedProduct purchasedProduct = PurchasedProduct.builder()
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

            purchaseHistories.add(purchaseHistory);
            purchasedProducts.add(purchasedProduct);

            cartRepository.delete(cart);
        }

        return new HashMap<>() {{
            put("purchaseHistory", PurchaseHistoryMapper.instance.eListToDtoList(purchaseHistoryRepository.saveAll(purchaseHistories)));
            put("purchasedProduct", PurchasedProductMapper.instance.eListToDtoList(purchasedProductRepository.saveAll(purchasedProducts)));
        }};
    }


    @Override
    public Payment createPaymentV2(PaymentCreateRequestV2 paymentCreateRequestV2) {
        Payment createPayment;
        try {
            Amount amount = new Amount(
                    paymentCreateRequestV2.getCurrency(),
                    paymentCreateRequestV2.getTotal());

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);

            // Add custom parameters
            JSONObject customParams = new JSONObject();
            customParams.put("cartIds", paymentCreateRequestV2.getCartIds());
            transaction.setCustom(customParams.toString());

            Payment payment = getPaymentV2(paymentCreateRequestV2, transaction);

            createPayment = payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(String.valueOf(e.getDetails()));
        }
        return createPayment;
    }

    public static Payment getPayment(PaymentCreateRequest paymentCreateRequest, Transaction transaction) {
        List<Transaction> transactions = Arrays.asList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paymentCreateRequest.getCancelUrl());
        redirectUrls.setReturnUrl(paymentCreateRequest.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }

    public static Payment getPaymentV2(PaymentCreateRequestV2 paymentCreateRequestV2, Transaction transaction) {
        List<Transaction> transactions = Arrays.asList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paymentCreateRequestV2.getCancelUrl());
        redirectUrls.setReturnUrl(paymentCreateRequestV2.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }
}
