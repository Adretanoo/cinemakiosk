package com.adrian.cinemakiosk.persistence.entity.impl;

import java.util.List;

public class Order {

    private int id;
    private String dataTime;
    private String status;
    private double totalPrice;
    private String paymentMethod;
    private int userId;
    private List<Ticket> tickets;


    public Order(int id, String dataTime, String status, double totalPrice, String paymentMethod,
        int userId, List<Ticket> tickets) {
        this.id = id;
        this.dataTime = dataTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", dataTime='" + dataTime + '\'' +
            ", status='" + status + '\'' +
            ", totalPrice=" + totalPrice +
            ", paymentMethod='" + paymentMethod + '\'' +
            ", userId=" + userId +
            ", tickets=" + tickets +
            '}';
    }
}
