package com.adrian.cinemakiosk.persistence.entity.impl;

public class Seat {
    private int id;
    private int seatNumber;
    private int row;
    private int hallId;
    private String status;

    public Seat(int id, int seatNumber, int row, int hallId, String status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.row = row;
        this.hallId = hallId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public int getRow() {
        return row;
    }

    public int getHallId() {
        return hallId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
