package com.CompleteProject.completeproject.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Payment – abstract base class for all payment types.
 *
 * OOP Concepts:
 *   - Abstraction    : processPayment() and getPaymentType() are abstract;
 *                      callers work through this interface without knowing
 *                      whether the underlying type is Online or Counter.
 *   - Encapsulation  : all financial fields are private; only exposed via
 *                      Lombok-generated getters/setters to protect data integrity.
 *   - Inheritance    : OnlinePayment and CounterPayment extend this class
 *                      and inherit all common fields and behaviour.
 *
 * File format (pipe-separated, 13 columns) in payments.txt:
 *   paymentId | bookingId | userId | amount | promoCode | discount |
 *   finalAmount | paymentDate | status | paymentType |
 *   typeField1 | typeField2 | typeField3
 *
 *   ONLINE  type fields: cardType | lastFour | transactionId
 *   COUNTER type fields: counterRef | receiptNumber | N/A
 *
 * status values: COMPLETED | PENDING | REFUNDED | FAILED
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Payment {

    private String paymentId;
    private String bookingId;
    private String userId;
    private double amount;         // original booking total
    private String promoCode;      // "NONE" if none applied
    private double discount;       // discount amount in LKR
    private double finalAmount;    // amount - discount
    private String paymentDate;    // ISO date e.g. "2026-05-02"
    private String status;         // COMPLETED | PENDING | REFUNDED | FAILED

    // ── Abstract methods (Abstraction) ─────────────────────────────────────

    /**
     * Processes the payment.
     * Online implementation simulates a card charge.
     * Counter implementation marks payment as pending collection.
     *
     * @return true if payment was processed successfully
     */
    public abstract boolean processPayment();

    /** Returns "ONLINE" or "COUNTER". Used for serialisation and display. */
    public abstract String getPaymentType();

    /** Returns the subclass-specific pipe-delimited tail fields for the file. */
    protected abstract String getTypeSpecificFields();

    // ── Derived helpers ────────────────────────────────────────────────────

    public boolean isCompleted() { return "COMPLETED".equalsIgnoreCase(status); }
    public boolean isPending()   { return "PENDING".equalsIgnoreCase(status); }
    public boolean isRefunded()  { return "REFUNDED".equalsIgnoreCase(status); }
    public boolean isFailed()    { return "FAILED".equalsIgnoreCase(status); }

    // ── Serialisation ──────────────────────────────────────────────────────

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

    /**
     * Factory method — reads the paymentType field (index 9) and delegates
     * to the correct subclass parser. Keeps deserialization logic centralised.
     */
    public static Payment fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 10) return null;
        String type = p[9].trim();
        return "ONLINE".equals(type)
                ? OnlinePayment.fromFileParts(p)
                : CounterPayment.fromFileParts(p);
    }

    /** Populates all base fields from the shared columns. Called by subclasses. */
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
