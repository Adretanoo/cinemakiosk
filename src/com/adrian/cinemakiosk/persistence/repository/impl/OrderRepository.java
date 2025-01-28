package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Order;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class OrderRepository extends BaseRepository<Order> {

    public OrderRepository() {
        super("data/orders.json", new TypeToken<List<Order>>() {
        }.getType());
    }

    @Override
    protected int getId(Order order) {
        return order.getId();
    }

    @Override
    protected boolean matchesField(Order order, String fieldName, Object value) {
        switch (fieldName) {
            case "userId":
                return order.getUserId() == (int) value;
            case "status":
                return order.getStatus().equals(value);
            default:
                return false;
        }
    }
}