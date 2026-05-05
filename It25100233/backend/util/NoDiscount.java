package com.example.movieticket.util;

/**
 * NoDiscount strategy - returns 0 discount
 * Concrete implementation of Discount interface
 */
public class NoDiscount implements Discount {
    
    @Override
    public double calculateDiscount(double originalAmount) {
        return 0;
    }
    
    @Override
    public String getPromoCode() {
        return "NONE";
    }
    
    @Override
    public int getDiscountPercentage() {
        return 0;
    }
}
