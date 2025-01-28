package com.adrian.cinemakiosk.persistence.repository.impl;


import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super("data/users.json", new TypeToken<List<User>>() {
        }.getType());
    }

    @Override
    protected int getId(User user) {
        return user.getId();
    }

    @Override
    protected boolean matchesField(User user, String fieldName, Object value) {
        switch (fieldName) {
            case "name":
                return user.getUsername().equals(value);
            case "email":
                return user.getEmail().equals(value);
            default:
                return false;
        }
    }
}
