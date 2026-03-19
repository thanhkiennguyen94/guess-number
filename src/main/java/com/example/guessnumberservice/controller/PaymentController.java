package com.example.guessnumberservice.controller;

import com.example.guessnumberservice.response.ApiResponse;
import com.example.guessnumberservice.service.PaymentService;
import com.example.guessnumberservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final MessageUtil messageUtil;
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ApiResponse<String> createPayment() {

        String paymentUrl = paymentService.createPayment();

        return ApiResponse.success(paymentUrl, "Tạo link thanh toán thành công");
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String txnRef,
            @RequestParam String hash
    ) {
        paymentService.handlePaymentSuccess(txnRef, hash);
        return "Thanh toán thành công";
    }
}
