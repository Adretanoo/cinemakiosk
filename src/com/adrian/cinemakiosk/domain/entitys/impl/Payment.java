package com.adrian.cinemakiosk.domain.entitys.impl;

public class Payment {

    private int id;
    private double amount;
    private String dateTime;
    private String paymentMethod;
    private int orderId;

    public Payment(int id, double amount, String dateTime, String paymentMethod, int orderId) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + id +
            ", amount=" + amount +
            ", dateTime='" + dateTime + '\'' +
            ", paymentMethod='" + paymentMethod + '\'' +
            ", orderId=" + orderId +
            '}';
    }
}
