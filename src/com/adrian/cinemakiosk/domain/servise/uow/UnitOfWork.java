package com.adrian.cinemakiosk.domain.servise.uow;

import com.adrian.cinemakiosk.persistence.entity.impl.Hall;
import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.adrian.cinemakiosk.persistence.entity.impl.Order;
import com.adrian.cinemakiosk.persistence.entity.impl.Payment;
import com.adrian.cinemakiosk.persistence.entity.impl.Seat;
import com.adrian.cinemakiosk.persistence.entity.impl.Session;
import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.Repository;
import com.adrian.cinemakiosk.persistence.repository.impl.HallRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.MovieRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.OrderRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.PaymentRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.SeatRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.SessionRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.TicketRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import java.io.IOException;

public class UnitOfWork {

    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final UserRepository userRepository;

    public UnitOfWork() throws IOException {
        this.ticketRepository = new TicketRepository();
        this.sessionRepository = new SessionRepository();
        this.seatRepository = new SeatRepository();
        this.paymentRepository = new PaymentRepository();
        this.orderRepository = new OrderRepository();
        this.movieRepository = new MovieRepository();
        this.hallRepository = new HallRepository();
        this.userRepository = new UserRepository();
    }

    public <T> Repository<T> getRepository(Class<T> entityClass) {
        if (entityClass == Ticket.class) {
            return (Repository<T>) ticketRepository;
        } else if (entityClass == Session.class) {
            return (Repository<T>) sessionRepository;
        } else if (entityClass == Seat.class) {
            return (Repository<T>) seatRepository;
        } else if (entityClass == Payment.class) {
            return (Repository<T>) paymentRepository;
        } else if (entityClass == Order.class) {
            return (Repository<T>) orderRepository;
        } else if (entityClass == Movie.class) {
            return (Repository<T>) movieRepository;
        } else if (entityClass == Hall.class) {
            return (Repository<T>) hallRepository;
        } else if (entityClass == User.class) {
            return (Repository<T>) userRepository;
        }
        throw new IllegalArgumentException(
            "Не знайдено репозиторій для класу: " + entityClass.getName());
    }
}
