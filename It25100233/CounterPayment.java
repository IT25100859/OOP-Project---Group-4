package com.CompleteProject.completeproject.bean;


public class CounterPayment extends Payment {
    private String counterRef;
    private String receiptNumber;

    public CounterPayment() {}

    public String getCounterRef() { return counterRef; }
    public void setCounterRef(String counterRef) { this.counterRef = counterRef; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    public CounterPayment(String counterRef, String receiptNumber) {
        this.counterRef    = counterRef;
        this.receiptNumber = receiptNumber;
    }

    // Abstract implementations

   
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
