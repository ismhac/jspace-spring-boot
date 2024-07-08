package com.ismhac.jspace.service.common.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.event.SendBillEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.primaryKey.PaymentTransactionId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.common.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final APIContext apiContext;
    private final CartRepository cartRepository;
    private final PurchasedProductRepository purchasedProductRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object checkPayment(String mac, String paymentId, String payerId) throws PayPalRESTException {
        Gson gson = new Gson();
        var paymentTransaction = paymentTransactionRepository.findById(PaymentTransactionId.builder()
                .mac(mac)
                .paymentId(paymentId)
                .build());

        if(paymentTransaction.isEmpty()){
            throw new AppException(ErrorCode.NOT_FOUND_PAYMENT_TRANSACTION);
        }

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        Payment executedPayment = payment.execute(apiContext, paymentExecute);
        if (executedPayment.getState().equals("approved")) {
            List<Integer> cartsId = gson.fromJson(paymentTransaction.get().getCartValue(), new TypeToken<List<Integer>>(){}.getType());
            processPurchasedProducts(cartsId, executedPayment.getPayer().getPaymentMethod(), executedPayment.getState());
            return new HashMap<>(){{
               put("status", true);
            }};
        }
        return new HashMap<>(){{
            put("status", false);
        }};
    }


    @Transactional(rollbackFor = Exception.class)
    protected void processPurchasedProducts(List<Integer> cartIds, String paymentMethod, String status) {
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
        purchasedProductRepository.saveAll(purchasedProducts);
        List<PurchaseHistory> savedPurchaseHistories = purchaseHistoryRepository.saveAll(purchaseHistories);
        this.sendBill(savedPurchaseHistories);
    }


    private void sendBill(List<PurchaseHistory> purchaseHistories){
        String billContent = readEmailTemplate("classpath:templates/payments/Bill.txt");
        Company company = purchaseHistories.stream().findFirst().get().getCompany();

        billContent = billContent.replace("#{company_logo}", company.getLogo());
        billContent = billContent.replace("#{company_name}", company.getName());
        billContent = billContent.replace("#{company_email}", company.getEmail());
        billContent = billContent.replace("#{company_phone_number}", company.getPhone());


        double totalAmount = 0;
        String tableContent = " ";
        for(PurchaseHistory item :purchaseHistories){
            String itemText = readEmailTemplate("classpath:templates/payments/BillItems.txt");
            itemText = itemText.replace("#{product_name}", item.getProductName());
            itemText = itemText.replace("#{quantity}", String.valueOf(item.getQuantity()));
            itemText = itemText.replace("#{price}", String.valueOf(item.getProductPrice()));
            itemText = itemText.replace("#{total_price}", String.valueOf(item.getTotalPrice()));

            totalAmount += item.getTotalPrice();
            tableContent.concat(itemText).concat(" ");
        }

        billContent = billContent.replace("#{total_amount}", String.valueOf(totalAmount));


        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(company.getEmail())
                .body(billContent)
                .subject("Verification of Company Information")
                .build();

        SendBillEvent sendBillEvent = new SendBillEvent(this, sendMailRequest);

        applicationEventPublisher.publishEvent(sendBillEvent);
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

    private String readEmailTemplate(String filePath) {
        Resource resource = resourceLoader.getResource(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
