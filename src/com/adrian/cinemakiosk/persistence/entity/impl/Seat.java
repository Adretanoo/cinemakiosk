package com.adrian.cinemakiosk.persistence.entity.impl;

public class Seat {
    private int id;
    private int seatNumber;
    private int row;
    private int hallId;
    private String status;  // Field for seat status

    // Constructor
    public Seat(int id, int seatNumber, int row, int hallId, String status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.row = row;
        this.hallId = hallId;
        this.status = status;  // Initialize status
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Getter for seatNumber
    public int getSeatNumber() {
        return seatNumber;
    }

    // Getter for row
    public int getRow() {
        return row;
    }

    // Getter for hallId
    public int getHallId() {
        return hallId;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status (if needed)
    public void setStatus(String status) {
        this.status = status;
    }

    // Other methods as needed
}
