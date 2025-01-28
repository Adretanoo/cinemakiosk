package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Seat;
import com.google.gson.reflect.TypeToken;
import java.util.List;

class SeatRepository extends BaseRepository<Seat> {

    public SeatRepository() {
        super("data/seats.json", new TypeToken<List<Seat>>() {
        }.getType());
    }

    @Override
    protected int getId(Seat seat) {
        return seat.getId();
    }

    @Override
    protected boolean matchesField(Seat seat, String fieldName, Object value) {
        switch (fieldName) {
            case "row":
                return seat.getRow() == (int) value;
            case "number":
                return seat.getSeatNumber() == (int) value;
            default:
                return false;
        }
    }
}