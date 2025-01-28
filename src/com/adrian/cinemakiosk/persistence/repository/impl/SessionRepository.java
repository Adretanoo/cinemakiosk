package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Session;
import com.google.gson.reflect.TypeToken;
import java.util.List;

class SessionRepository extends BaseRepository<Session> {

    public SessionRepository() {
        super("data/sessions.json", new TypeToken<List<Session>>() {
        }.getType());
    }

    @Override
    protected int getId(Session session) {
        return session.getId();
    }

    @Override
    protected boolean matchesField(Session session, String fieldName, Object value) {
        switch (fieldName) {
            case "movieId":
                return session.getMovieId() == (int) value;
            case "hallId":
                return session.getHallId() == (int) value;
            default:
                return false;
        }
    }
}
