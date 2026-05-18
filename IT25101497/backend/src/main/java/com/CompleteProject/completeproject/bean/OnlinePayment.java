package com.CompleteProject.completeproject.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * OnlinePayment – concrete subclass of Payment for card/online payments.
 *
 * OOP Concepts:
 *   - Inheritance : extends Payment, inherits all base fields and helpers.
 *   - Abstraction : implements processPayment() with card-specific logic.
 *   - Encapsulation: stores only the last 4 digits of the card — never the
 *                    full number — protecting sensitive financial data.
 */
@Getter
@Setter
@NoArgsConstructor
public class OnlinePayment extends Payment {

    /** VISA | MASTERCARD | AMEX */
    private String cardType;

    /** Last 4 digits only — full card number is never stored. */
    private String cardLastFour;

    /** Auto-generated unique transaction reference. */
    private String transactionId;

    public OnlinePayment(String cardType, String cardLastFour) {
        this.cardType     = cardType;
        this.cardLastFour = cardLastFour;
        // Generate transaction ID on construction
        this.transactionId = "TXN" + UUID.randomUUID()
                                         .toString()
                                         .replace("-", "")
                                         .substring(0, 10)
                                         .toUpperCase();
    }

    // ── Abstract implementations ───────────────────────────────────────────

    /**
     * Simulates a card charge.
     * In a production system this would call a payment gateway (Stripe, PayHere etc.)
     * Here we validate that card details are present and mark COMPLETED.
     */
    @Override
    public boolean processPayment() {
        if (cardLastFour == null || cardLastFour.length() != 4) {
            setStatus("FAILED");
            return false;
        }
        setStatus("COMPLETED");
        return true;
    }

    @Override
    public String getPaymentType() { return "ONLINE"; }

    @Override
    protected String getTypeSpecificFields() {
        return (cardType      != null ? cardType      : "VISA")   + "|" +
               (cardLastFour  != null ? cardLastFour  : "0000")   + "|" +
               (transactionId != null ? transactionId : "N/A");
    }

    /** Display helper used in JSP receipt views. */
    public String getMaskedCard() {
        return "**** **** **** " + (cardLastFour != null ? cardLastFour : "????");
    }

    // ── Deserialisation ────────────────────────────────────────────────────

    public static OnlinePayment fromFileParts(String[] p) {
        OnlinePayment op = new OnlinePayment();
        op.fillBaseFields(p);
        op.setCardType(     p.length > 10 ? p[10].trim() : "VISA");
        op.setCardLastFour( p.length > 11 ? p[11].trim() : "0000");
        op.setTransactionId(p.length > 12 ? p[12].trim() : "N/A");
        return op;
    }
}
