package com.subhajit.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.subhajit.domain.PaymentMethod;
import com.subhajit.modal.PaymentOrder;
import com.subhajit.modal.User;
import com.subhajit.response.PaymentResponse;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException, StripeException;

    PaymentResponse createRazorpayPaymentLing(User user, Long Amount, Long orderId) throws RazorpayException;

    PaymentResponse createStripePaymentLing(User user, Long amount, Long orderId) throws StripeException;
}
