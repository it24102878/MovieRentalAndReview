package com.movierental.model;

import java.time.LocalDate;

public class Payment {
    private String paymentId;
    private String rentalId;
    private String cardNumber;
    private String cardHolder;
    private LocalDate expiryDate;
    private double amount;

    public Payment(String paymentId, String rentalId, String cardNumber, String cardHolder, LocalDate expiryDate, double amount) {
        this.paymentId = paymentId;
        this.rentalId = rentalId;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.amount = amount;
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getRentalId() { return rentalId; }
    public void setRentalId(String rentalId) { this.rentalId = rentalId; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return paymentId + "," + rentalId + "," + cardNumber + "," + cardHolder + "," + expiryDate + "," + amount;
    }
}