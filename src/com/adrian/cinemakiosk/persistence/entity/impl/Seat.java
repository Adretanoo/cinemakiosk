package com.adrian.cinemakiosk.persistence.entity.impl;

/**
 * Клас, що представляє місце в кінозалі.
 * Містить інформацію про номер місця, ряд, ідентифікатор залу та статус місця.
 */
public class Seat {

    private int id;
    private int seatNumber;
    private int row;
    private int hallId;
    private String status;

    /**
     * Конструктор для створення місця.
     *
     * @param id ідентифікатор місця.
     * @param seatNumber номер місця в ряду.
     * @param row номер ряду в кінозалі.
     * @param hallId ідентифікатор кінозалу.
     * @param status статус місця (наприклад, "вільне", "заброньоване").
     */
    public Seat(int id, int seatNumber, int row, int hallId, String status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.row = row;
        this.hallId = hallId;
        this.status = status;
    }

    /**
     * Отримує ідентифікатор місця.
     *
     * @return ідентифікатор місця.
     */
    public int getId() {
        return id;
    }

    /**
     * Отримує номер місця.
     *
     * @return номер місця.
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Отримує номер ряду, в якому знаходиться місце.
     *
     * @return номер ряду.
     */
    public int getRow() {
        return row;
    }

    /**
     * Отримує ідентифікатор кінозалу, в якому знаходиться місце.
     *
     * @return ідентифікатор кінозалу.
     */
    public int getHallId() {
        return hallId;
    }

    /**
     * Отримує статус місця.
     *
     * @return статус місця.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Встановлює новий статус для місця.
     *
     * @param status новий статус місця.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
