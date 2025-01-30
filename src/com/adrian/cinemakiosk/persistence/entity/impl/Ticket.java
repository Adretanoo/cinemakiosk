package com.adrian.cinemakiosk.persistence.entity.impl;

/**
 * Клас, що представляє квиток для кіносеансу.
 * Містить інформацію про квиток, таку як ціна, статус, тип квитка, сеанс, місце та інші атрибути.
 */
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

    /**
     * Конструктор для створення нового квитка з усіма атрибутами.
     *
     * @param id унікальний ідентифікатор квитка.
     * @param price ціна квитка.
     * @param status статус квитка (наприклад, "продано", "доступно").
     * @param ticketType тип квитка (наприклад, "стандартний", "VIP").
     * @param sessionId ідентифікатор сеансу.
     * @param seatNumber номер місця в залі.
     * @param quantity кількість квитків.
     * @param orderId ідентифікатор замовлення.
     * @param movie назва фільму.
     * @param time час сеансу.
     */
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

    /**
     * Конструктор для створення квитка тільки з назвою фільму та ціною.
     *
     * @param movie назва фільму.
     * @param price ціна квитка.
     */
    public Ticket(String movie, double price) {
        this.movie = movie;
        this.price = price;
    }

    /**
     * Повертає строкове представлення квитка.
     *
     * @return строкове представлення об'єкта Ticket.
     */
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

    /**
     * Отримує ідентифікатор замовлення, до якого належить цей квиток.
     *
     * @return ідентифікатор замовлення.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Встановлює ідентифікатор замовлення для цього квитка.
     *
     * @param orderId новий ідентифікатор замовлення.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Отримує назву фільму, на який придбано квиток.
     *
     * @return назву фільму.
     */
    public String getMovie() {
        return movie;
    }

    /**
     * Встановлює назву фільму для цього квитка.
     *
     * @param movie нову назву фільму.
     */
    public void setMovie(String movie) {
        this.movie = movie;
    }

    /**
     * Отримує час сеансу.
     *
     * @return час сеансу.
     */
    public String getTime() {
        return time;
    }

    /**
     * Встановлює час сеансу для цього квитка.
     *
     * @param time новий час сеансу.
     */
    public void setTime(String time) {
        this.time = time;
    }

    // Геттери та сеттери для інших полів

    /**
     * Отримує ідентифікатор квитка.
     *
     * @return ідентифікатор квитка.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор квитка.
     *
     * @param id новий ідентифікатор квитка.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує ціну квитка.
     *
     * @return ціну квитка.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну квитка.
     *
     * @param price нову ціну квитка.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Отримує статус квитка.
     *
     * @return статус квитка.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Встановлює статус квитка.
     *
     * @param status новий статус квитка.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Отримує тип квитка.
     *
     * @return тип квитка.
     */
    public String getTicketType() {
        return ticketType;
    }

    /**
     * Встановлює тип квитка.
     *
     * @param ticketType новий тип квитка.
     */
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * Отримує ідентифікатор сеансу.
     *
     * @return ідентифікатор сеансу.
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Встановлює ідентифікатор сеансу для цього квитка.
     *
     * @param sessionId новий ідентифікатор сеансу.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Отримує номер місця в залі.
     *
     * @return номер місця.
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Встановлює номер місця для цього квитка.
     *
     * @param seatNumber новий номер місця.
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Отримує кількість квитків.
     *
     * @return кількість квитків.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Встановлює кількість квитків.
     *
     * @param quantity нову кількість квитків.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
