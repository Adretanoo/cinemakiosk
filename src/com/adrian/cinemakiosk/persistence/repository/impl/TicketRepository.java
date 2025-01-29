package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Session;
import com.adrian.cinemakiosk.persistence.entity.impl.Seat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TicketRepository {

    private static final String FILE_PATH = "data/tickets.json";
    private Gson gson;

    public TicketRepository() {
        gson = new Gson();
    }

    // Читання всіх сеансів та місць з файлу
    public List<Session> getAllSessions() throws IOException {
        FileReader reader = new FileReader(FILE_PATH);
        TicketsData data = gson.fromJson(reader, TicketsData.class);
        reader.close();
        return data.getSessions();
    }

    // Оновлення даних про місце (заняте/вільне)
    public void updateSeatStatus(int sessionId, int seatNumber, String newStatus) throws IOException {
        FileReader reader = new FileReader(FILE_PATH);
        TicketsData data = gson.fromJson(reader, TicketsData.class);
        reader.close();

        Session session = findSessionById(sessionId, data.getSessions());
        if (session != null) {
            Seat seat = findSeatByNumber(seatNumber, session.getSeats());
            if (seat != null) {
                seat.setStatus(newStatus);

                // Записуємо зміни назад у файл
                FileWriter writer = new FileWriter(FILE_PATH);
                gson.toJson(data, writer);
                writer.close();
            }
        }
    }

    // Знайти сеанс по ID
    private Session findSessionById(int sessionId, List<Session> sessions) {
        for (Session session : sessions) {
            if (session.getId() == sessionId) {
                return session;
            }
        }
        return null;
    }

    // Знайти місце по номеру
    private Seat findSeatByNumber(int seatNumber, List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }

    // Клас для зберігання даних з tickets.json
    static class TicketsData {
        private List<Session> sessions;

        public List<Session> getSessions() {
            return sessions;
        }

        public void setSessions(List<Session> sessions) {
            this.sessions = sessions;
        }
    }
}
