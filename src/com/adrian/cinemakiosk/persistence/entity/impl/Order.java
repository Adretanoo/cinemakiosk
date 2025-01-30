package com.adrian.cinemakiosk.persistence.entity.impl;

import java.util.List;

/**
 * Клас, що представляє замовлення користувача.
 * Містить інформацію про час, статус, загальну вартість, метод оплати, ідентифікатор користувача та список квитків, які входять до складу замовлення.
 */
public class Order {

    private int id;
    private String dataTime;
    private String status;
    private double totalPrice;
    private String paymentMethod;
    private int userId;
    private List<Ticket> tickets;

    /**
     * Конструктор для створення замовлення.
     *
     * @param id ідентифікатор замовлення.
     * @param dataTime дата та час створення замовлення.
     * @param status статус замовлення (наприклад, "підтверджено", "скасовано").
     * @param totalPrice загальна вартість замовлення.
     * @param paymentMethod метод оплати (наприклад, "карта", "готівка").
     * @param userId ідентифікатор користувача, який зробив замовлення.
     * @param tickets список квитків, що належать замовленню.
     */
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

    /**
     * Отримує ідентифікатор замовлення.
     *
     * @return ідентифікатор замовлення.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор замовлення.
     *
     * @param id новий ідентифікатор замовлення.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує дату та час створення замовлення.
     *
     * @return дата та час замовлення.
     */
    public String getDataTime() {
        return dataTime;
    }

    /**
     * Встановлює дату та час замовлення.
     *
     * @param dataTime нові дата та час замовлення.
     */
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * Отримує статус замовлення.
     *
     * @return статус замовлення.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Встановлює статус замовлення.
     *
     * @param status новий статус замовлення.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Отримує загальну вартість замовлення.
     *
     * @return загальна вартість.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Встановлює загальну вартість замовлення.
     *
     * @param totalPrice нова загальна вартість замовлення.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Отримує метод оплати для замовлення.
     *
     * @return метод оплати.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Встановлює метод оплати для замовлення.
     *
     * @param paymentMethod новий метод оплати.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Отримує ідентифікатор користувача, який зробив замовлення.
     *
     * @return ідентифікатор користувача.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Встановлює ідентифікатор користувача для замовлення.
     *
     * @param userId новий ідентифікатор користувача.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Отримує список квитків, що належать замовленню.
     *
     * @return список квитків.
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Встановлює список квитків для замовлення.
     *
     * @param tickets новий список квитків.
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Повертає строкове представлення об'єкта Order.
     *
     * @return строкове представлення замовлення.
     */
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
