package com.adrian.cinemakiosk.persistence.entity.impl;

/**
 * Клас, що представляє зал кінотеатру.
 * Містить інформацію про ідентифікатор, назву залу та місткість місць.
 */
public class Hall {

    private int id;
    private String name;
    private int seatCapacity;

    /**
     * Конструктор для створення об'єкта зала.
     *
     * @param id ідентифікатор залу.
     * @param name назва залу.
     * @param seatCapacity місткість залу (кількість місць).
     */
    public Hall(int id, String name, int seatCapacity) {
        this.id = id;
        this.name = name;
        this.seatCapacity = seatCapacity;
    }

    /**
     * Отримує ідентифікатор залу.
     *
     * @return ідентифікатор залу.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор залу.
     *
     * @param id новий ідентифікатор залу.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує назву залу.
     *
     * @return назва залу.
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву залу.
     *
     * @param name нова назва залу.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Отримує місткість залу (кількість місць).
     *
     * @return місткість залу.
     */
    public int getSeatCapacity() {
        return seatCapacity;
    }

    /**
     * Встановлює місткість залу.
     *
     * @param seatCapacity нова місткість залу.
     */
    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    /**
     * Повертає рядкове представлення об'єкта зала.
     *
     * @return рядкове представлення зала.
     */
    @Override
    public String toString() {
        return "Hall{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", seatCapacity=" + seatCapacity +
            '}';
    }
}
