package com.example.movieticket.util;

/**
 * Save10Discount strategy - applies 10% discount
 * Concrete implementation of Discount interface
 * Promo Code: SAVE10
 */
public class Save10Discount implements Discount {
    
    private static final int DISCOUNT_PERCENTAGE = 10;
    private static final String PROMO_CODE = "SAVE10";
    
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
