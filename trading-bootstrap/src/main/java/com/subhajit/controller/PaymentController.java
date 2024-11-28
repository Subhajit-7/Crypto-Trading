package com.subhajit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.razorpay.RazorpayException;
import com.subhajit.domain.PaymentMethod;
import com.subhajit.modal.PaymentOrder;
import com.subhajit.modal.User;
import com.subhajit.response.PaymentResponse;
import com.subhajit.service.PaymentService;
import com.subhajit.service.UserService;

@RestController
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod, @PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception, RazorpayException, StripeException {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            paymentResponse = paymentService.createRazorpayPaymentLing(user, amount, order.getId());
        } else {
            paymentResponse = paymentService.createStripePaymentLing(user, amount, order.getId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
