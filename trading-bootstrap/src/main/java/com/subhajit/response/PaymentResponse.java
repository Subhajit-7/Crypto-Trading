package com.subhajit.response;

import lombok.Data;

@Data
public class PaymentResponse {
    private String payment_url;
    private Long orderId;
    private String paymentId;
}
