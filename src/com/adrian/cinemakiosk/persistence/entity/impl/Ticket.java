package com.adrian.cinemakiosk.persistence.entity.impl;

public class Ticket {

    private int id;
    private double price;
    private String status;
    private String ticketType;
    private int sessionId;
    private int seatNumber;
    private int quantity;
    private int orderId;
    private String movie;
    private String time;

    // Constructor with the new fields
    public Ticket(int id, double price, String status, String ticketType, int sessionId,
        int seatNumber, int quantity, int orderId, String movie, String time) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.ticketType = ticketType;
        this.sessionId = sessionId;
        this.seatNumber = seatNumber;
        this.quantity = quantity;
        this.orderId = orderId;
        this.movie = movie;
        this.time = time;
    }

    public Ticket(String movie, double price){
        this.movie = movie;
        this.price = price;
    }
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + id +
            ", price=" + price +
            ", status='" + status + '\'' +
            ", ticketType='" + ticketType + '\'' +
            ", sessionId=" + sessionId +
            ", seatNumber=" + seatNumber +
            ", quantity=" + quantity +
            ", orderId=" + orderId +
            ", movie='" + movie + '\'' +
            ", time='" + time + '\'' +
            '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Existing getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
