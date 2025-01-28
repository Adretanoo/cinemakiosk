package com.adrian.cinemakiosk.persistence.repository;

import java.util.List;

public interface Repository<T> {

    void add(T entity);

    T getById(int id);

    List<T> getAll();

    void update(T entity);

    void deleteById(int id);

    List<T> filter(String fieldName, Object value);
}
