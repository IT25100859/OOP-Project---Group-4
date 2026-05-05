package com.example.movieticket.util;

/**
 * Discount interface for implementing different discount strategies
 * Using Strategy Design Pattern (Polymorphism)
 */
public interface Discount {
    /**
     * Calculate discount amount for given price
     * @param originalAmount - original price
     * @return discount amount
     */
    double calculateDiscount(double originalAmount);
    
    /**
     * Get promo code associated with this discount
     * @return promo code
     */
    String getPromoCode();
    
    /**
     * Get discount percentage
     * @return discount percentage (e.g., 10 for 10%)
     */
    int getDiscountPercentage();
}
