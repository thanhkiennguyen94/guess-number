package com.example.guessnumberservice.service.impl;

import com.example.guessnumberservice.exception_handler.exception.NotFoundException;
import com.example.guessnumberservice.model.Payment;
import com.example.guessnumberservice.model.User;
import com.example.guessnumberservice.repository.PaymentRepository;
import com.example.guessnumberservice.repository.UserRepository;
import com.example.guessnumberservice.service.PaymentService;
import com.example.guessnumberservice.util.ConstMessageError;
import com.example.guessnumberservice.util.FuncHelper;
import com.example.guessnumberservice.util.HashUtil;
import com.example.guessnumberservice.util.MessageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final MessageUtil messageUtil;
    @Value("${payment.secret}")
    private String paymentSecretKey;
    @Override
    @Transactional
    public String createPayment() {
        String username = FuncHelper.getUsernameByToken();
        if (username == null) {
            throw new NotFoundException(messageUtil.getMessage(ConstMessageError.User.USERNAME_NOT_FOUND));
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        String txnRef = UUID.randomUUID().toString();
        int amount = 10000;

        Payment payment = new Payment();
        payment.setUserId(user.getId());
        payment.setAmount(10000);
        payment.setStatus("PENDING");
        payment.setTxnRef(txnRef);

        paymentRepository.save(payment);
        String hash = HashUtil.hmacSHA512(paymentSecretKey, txnRef);
        String hashEncoded = URLEncoder.encode(hash, StandardCharsets.UTF_8);
        //        hard link thanh toan
        return "http://localhost:8080/payment/success?txnRef=" + txnRef + "&hash=" + hashEncoded;
    }

    @Override
    @Transactional
    public void handlePaymentSuccess(String txnRef, String hash) {
        Payment payment = paymentRepository.findByTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Payment không tồn tại"));
//chong hack fake request
        String expectedHash = HashUtil.hmacSHA512(paymentSecretKey, txnRef);
        String hashDecoded = URLDecoder.decode(hash, StandardCharsets.UTF_8);
        if (!expectedHash.equals(hashDecoded)) {
            throw new RuntimeException("Request không hợp lệ (fake request)");
        }
//        xu ly concurrency
        int updated = paymentRepository.updateStatusIfPending(txnRef);
        //        chong spam hack luot
        if (updated == 0) {
            return;
        }
        User user = userRepository.findById(payment.getUserId())
                .orElseThrow();

        user.setTurns(user.getTurns() + 5);
        userRepository.save(user);
    }

}
