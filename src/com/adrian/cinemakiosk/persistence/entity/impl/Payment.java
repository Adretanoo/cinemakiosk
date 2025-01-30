package com.adrian.cinemakiosk.persistence.entity.impl;

/**
 * Клас, що представляє платіж.
 * Містить інформацію про суму платежу, дату та час, метод оплати та ідентифікатор замовлення.
 */
public class Payment {

    private int id;
    private double amount;
    private String dateTime;
    private String paymentMethod;
    private int orderId;

    /**
     * Конструктор для створення об'єкта платежу.
     *
     * @param id ідентифікатор платежу.
     * @param amount сума платежу.
     * @param dateTime дата та час платежу.
     * @param paymentMethod метод оплати (наприклад, "карта", "готівка").
     * @param orderId ідентифікатор замовлення, до якого відноситься платіж.
     */
    public Payment(int id, double amount, String dateTime, String paymentMethod, int orderId) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
    }

    /**
     * Отримує ідентифікатор платежу.
     *
     * @return ідентифікатор платежу.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор платежу.
     *
     * @param id новий ідентифікатор платежу.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує суму платежу.
     *
     * @return сума платежу.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Встановлює суму платежу.
     *
     * @param amount нова сума платежу.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Отримує дату та час платежу.
     *
     * @return дата та час платежу.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Встановлює дату та час платежу.
     *
     * @param dateTime нова дата та час платежу.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Отримує метод оплати.
     *
     * @return метод оплати.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Встановлює метод оплати.
     *
     * @param paymentMethod новий метод оплати.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Отримує ідентифікатор замовлення, до якого відноситься платіж.
     *
     * @return ідентифікатор замовлення.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Встановлює ідентифікатор замовлення для цього платежу.
     *
     * @param orderId новий ідентифікатор замовлення.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Повертає строкове представлення об'єкта Payment.
     *
     * @return строкове представлення платежу.
     */
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
