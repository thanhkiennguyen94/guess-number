package com.example.guessnumberservice.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    String createPayment();
    void handlePaymentSuccess(String txnRef, String hash);
}
