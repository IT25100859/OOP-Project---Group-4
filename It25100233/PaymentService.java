package com.CompleteProject.completeproject.service;

import com.CompleteProject.completeproject.bean.Payment;

import java.util.List;
import java.util.Map;


public interface PaymentService {

   
    Payment processOnlinePayment(String bookingId, String userId,
                                 double amount, String promoCode,
                                 String cardType, String cardLastFour);

    Payment processCounterPayment(String bookingId, String userId,
                                  double amount, String promoCode);

    
    Payment getPaymentById(String paymentId);

    // READ – payment for a specific booking (one-to-one)
    Payment getPaymentByBookingId(String bookingId);

    // READ – all payments for a customer (My Payments page)
    List<Payment> getPaymentsByUser(String userId);

    // READ (admin) – every payment in the system
    List<Payment> getAllPayments();


    boolean refundPayment(String paymentId, String requestingUserId);


    boolean deletePayment(String paymentId);

    
    double validatePromoCode(String code);

    // Returns the map of all valid promo codes and their labels, for the UI
    Map<String, String> getPromoCodeHints();
}
