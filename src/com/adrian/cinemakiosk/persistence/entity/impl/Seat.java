package com.adrian.cinemakiosk.persistence.entity.impl;

public class Seat {

    private int id;
    private int seatNumber; // Номер місця
    private int row; // Номер ряду
    private int hallId; // Foreign Key до Hall

    public Seat(int id, int seatNumber, int row, int hallId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.row = row;
        this.hallId = hallId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    @Override
    public String toString() {
        return "Seat{" +
            "id=" + id +
            ", seatNumber=" + seatNumber +
            ", row=" + row +
            ", hallId=" + hallId +
            '}';
    }
}
