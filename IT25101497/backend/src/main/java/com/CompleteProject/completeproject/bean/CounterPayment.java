package com.CompleteProject.completeproject.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CounterPayment – concrete subclass of Payment for in-person counter payments.
 *
 * OOP Concepts:
 *   - Inheritance : extends Payment, inherits all base fields.
 *   - Abstraction : implements processPayment() with counter-specific logic.
 *                   Counter payments are marked PENDING until staff confirms cash.
 */
@Getter
@Setter
@NoArgsConstructor
public class CounterPayment extends Payment {

    /** Counter or cashier reference (e.g. "CTR-1", "WINDOW-3"). */
    private String counterRef;

    /** Physical receipt number issued at the counter. */
    private String receiptNumber;

    public CounterPayment(String counterRef, String receiptNumber) {
        this.counterRef    = counterRef;
        this.receiptNumber = receiptNumber;
    }

    // ── Abstract implementations ───────────────────────────────────────────

    /**
     * Counter payments are always marked PENDING — they require physical
     * cash collection by staff. The cashier updates the record to COMPLETED
     * once cash is received.
     * For this project, we simulate immediate confirmation.
     */
    @Override
    public boolean processPayment() {
        setStatus("COMPLETED");   // simulated — in reality staff confirms
        return true;
    }

    @Override
    public String getPaymentType() { return "COUNTER"; }

    @Override
    protected String getTypeSpecificFields() {
        return (counterRef    != null ? counterRef    : "CTR-1") + "|" +
               (receiptNumber != null ? receiptNumber : "N/A")   + "|N/A";
    }

    // ── Deserialisation ────────────────────────────────────────────────────

    public static CounterPayment fromFileParts(String[] p) {
        CounterPayment cp = new CounterPayment();
        cp.fillBaseFields(p);
        cp.setCounterRef(   p.length > 10 ? p[10].trim() : "CTR-1");
        cp.setReceiptNumber(p.length > 11 ? p[11].trim() : "N/A");
        return cp;
    }
}
