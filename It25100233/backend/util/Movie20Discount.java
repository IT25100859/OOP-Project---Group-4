package com.example.movieticket.util;

/**
 * Movie20Discount strategy - applies 20% discount
 * Concrete implementation of Discount interface
 * Promo Code: MOVIE20
 */
public class Movie20Discount implements Discount {
    
    private static final int DISCOUNT_PERCENTAGE = 20;
    private static final String PROMO_CODE = "MOVIE20";
    
    @Override
    public double calculateDiscount(double originalAmount) {
        return originalAmount * (DISCOUNT_PERCENTAGE / 100.0);
    }
    
    @Override
    public String getPromoCode() {
        return PROMO_CODE;
    }
    
    @Override
    public int getDiscountPercentage() {
        return DISCOUNT_PERCENTAGE;
    }
}
