package com.CompleteProject.completeproject.bean;

import java.util.UUID;


public class OnlinePayment extends Payment {
    private String cardType;
    private String cardLastFour;
    private String transactionId;

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public String getCardLastFour() { return cardLastFour; }
    public void setCardLastFour(String cardLastFour) { this.cardLastFour = cardLastFour; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public OnlinePayment() {}

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

    // Abstract implementations

 
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

    // Display helper used in JSP receipt views
    public String getMaskedCard() {
        return "**** **** **** " + (cardLastFour != null ? cardLastFour : "????");
    }

    // Deserialisation

    public static OnlinePayment fromFileParts(String[] p) {
        OnlinePayment op = new OnlinePayment();
        op.fillBaseFields(p);
        op.setCardType(     p.length > 10 ? p[10].trim() : "VISA");
        op.setCardLastFour( p.length > 11 ? p[11].trim() : "0000");
        op.setTransactionId(p.length > 12 ? p[12].trim() : "N/A");
        return op;
    }
}
