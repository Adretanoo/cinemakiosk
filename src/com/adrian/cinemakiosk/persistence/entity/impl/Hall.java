package com.adrian.cinemakiosk.persistence.entity.impl;

public class Hall {

    private int id;
    private String name;
    private int seatCapacity;

    public Hall(int id, String name, int seatCapacity) {
        this.id = id;
        this.name = name;
        this.seatCapacity = seatCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    @Override
    public String toString() {
        return "Hall{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", seatCapacity=" + seatCapacity +
            '}';
    }
}
