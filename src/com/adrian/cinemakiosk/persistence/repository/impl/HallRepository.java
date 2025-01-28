package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Hall;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class HallRepository extends BaseRepository<Hall> {

    public HallRepository() {
        super("data/halls.json", new TypeToken<List<Hall>>() {
        }.getType());
    }

    @Override
    protected int getId(Hall hall) {
        return hall.getId();
    }

    @Override
    protected boolean matchesField(Hall hall, String fieldName, Object value) {
        switch (fieldName) {
            case "name":
                return hall.getName().equals(value);
            case "capacity":
                return hall.getSeatCapacity() == (int) value;
            default:
                return false;
        }
    }
}