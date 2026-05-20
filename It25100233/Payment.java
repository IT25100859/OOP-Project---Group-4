package com.CompleteProject.completeproject.bean;


public abstract class Payment {
    private String paymentId;
    private String bookingId;
    private String userId;
    private double amount;
    private String promoCode;
    private double discount;
    private double finalAmount;
    private String paymentDate;
    private String status;

    public Payment() {}

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Abstract methods

 
    public abstract boolean processPayment();

    public abstract String getPaymentType();

    protected abstract String getTypeSpecificFields();

    // Derived helpers

    public boolean isCompleted() { return "COMPLETED".equalsIgnoreCase(status); }
    public boolean isPending()   { return "PENDING".equalsIgnoreCase(status); }
    public boolean isRefunded()  { return "REFUNDED".equalsIgnoreCase(status); }
    public boolean isFailed()    { return "FAILED".equalsIgnoreCase(status); }

    // Serialisation

    public String toFileString() {
        return String.join("|",
                paymentId,
                bookingId,
                userId,
                String.valueOf(amount),
                promoCode == null ? "NONE" : promoCode,
                String.valueOf(discount),
                String.valueOf(finalAmount),
                paymentDate,
                status,
                getPaymentType()
        ) + "|" + getTypeSpecificFields();
    }

   
    public static Payment fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 10) return null;
        String type = p[9].trim();
        return "ONLINE".equals(type)
                ? OnlinePayment.fromFileParts(p)
                : CounterPayment.fromFileParts(p);
    }

    // Populates all base fields from the shared columns. Called by subclasses.
    protected void fillBaseFields(String[] p) {
        setPaymentId(p[0].trim());
        setBookingId(p[1].trim());
        setUserId(p[2].trim());
        setAmount(Double.parseDouble(p[3].trim()));
        setPromoCode(p[4].trim());
        setDiscount(Double.parseDouble(p[5].trim()));
        setFinalAmount(Double.parseDouble(p[6].trim()));
        setPaymentDate(p[7].trim());
        setStatus(p[8].trim());
    }
}
