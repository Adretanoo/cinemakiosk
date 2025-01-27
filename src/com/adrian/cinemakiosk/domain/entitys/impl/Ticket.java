package com.adrian.cinemakiosk.domain.entitys.impl;

public class Ticket {

    private int id;
    private double price;
    private String status;
    private String ticketType;
    private int sessionId;
    private int seatId;
    private int orderId;

    public Ticket(int id, double price, String status, String ticketType, int sessionId, int seatId,
        int orderId) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.ticketType = ticketType;
        this.sessionId = sessionId;
        this.seatId = seatId;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + id +
            ", price=" + price +
            ", status='" + status + '\'' +
            ", ticketType='" + ticketType + '\'' +
            ", sessionId=" + sessionId +
            ", seatId=" + seatId +
            ", orderId=" + orderId +
            '}';
    }
}

