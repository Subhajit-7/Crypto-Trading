package com.subhajit.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.subhajit.modal.Order;
import com.subhajit.modal.PaymentOrder;
import com.subhajit.modal.User;
import com.subhajit.modal.Wallet;
import com.subhajit.modal.WalletTransaction;
import com.subhajit.service.OrderService;
import com.subhajit.service.PaymentService;
import com.subhajit.service.UserService;
import com.subhajit.service.WalletService;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt, @PathVariable Long walletId, @RequestBody WalletTransaction req) throws Exception {

        User senderUser = userService.findUserProfileByJwt(jwt);

        Wallet receiverWallet = walletService.findWalletById(walletId);

        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, req.getAmount());

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);
        

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt, @RequestParam(name = "order_id") Long orderId, @RequestParam(name = "payment_id") String paymentId) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order = paymentService.getPaymentOrderById(orderId);

        Boolean status;
        try {
            status = paymentService.ProccedPaymentOrder(order, paymentId);
        } catch (RazorpayException | StripeException e) {
            // Log the error for debugging
            System.err.println("Error processing payment: " + e.getMessage());
            throw new Exception("Payment processing failed: " + e.getMessage());
        }

        if (wallet.getBalance() == null) {
            wallet.setBalance(BigDecimal.valueOf(0));
        }

        if (status) {
            wallet = walletService.addBalance(wallet, order.getAmount());
        }

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/wallet/session")
    public ResponseEntity<Map<String, String>> getPaymentIntentId(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String session_id) throws StripeException {
        // Set Stripe API key
        com.stripe.Stripe.apiKey = stripeSecretKey;
        
        // Retrieve the session from Stripe
        Session session = Session.retrieve(session_id);
        PaymentIntent paymentIntent = PaymentIntent.retrieve(session.getPaymentIntent());
        
        // Prepare the response
        Map<String, String> response = new HashMap<>();
        response.put("payment_id", paymentIntent.getId());
        
        return ResponseEntity.ok(response);
    }
}
