package com.adrian.cinemakiosk;

import com.adrian.cinemakiosk.domain.servise.uow.UnitOfWork;
import com.adrian.cinemakiosk.persistence.entity.impl.Payment;
import com.adrian.cinemakiosk.persistence.entity.impl.Seat;
import com.adrian.cinemakiosk.persistence.entity.impl.Session;
import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;

public class Main {

//    public static void main(String[] args) {
//        Faker faker = new Faker();
//        List<User> users = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            User user = new User(
//                i + 1,
//                faker.name().fullName(),
//                faker.internet().emailAddress(),
//                faker.internet().password(),
//                faker.bool().bool() ? "user" : "admin"
//            );
//            users.add(user);
//
//            System.out.println(user);
//        }
//        saveToJsonFile("data/users.json", users);
//    }
//
//    private static void saveToJsonFile(String filePath, Object data) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try (FileWriter writer = new FileWriter(filePath)) {
//            gson.toJson(data, writer);
//            System.out.println("Дані збережено до файлу: " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {

        UnitOfWork unitOfWork = new UnitOfWork();

        Ticket newTicket = new Ticket(1, 10.0, "available", "VIP", 1, 1, 1);

        unitOfWork.getRepository(Ticket.class).add(newTicket);

        Ticket ticketFromRepo = unitOfWork.getRepository(Ticket.class).getById(1);
        System.out.println("Збережений квиток: " + ticketFromRepo.getId() + ", Тип: "
            + ticketFromRepo.getTicketType());
        Session newSession = new Session(1, "2025-01-28 14:00", "2D", 1, 1);
        unitOfWork.getRepository(Session.class).add(newSession);

        Session sessionFromRepo = unitOfWork.getRepository(Session.class).getById(1);
        System.out.println("Збережена сесія: " + sessionFromRepo.getId() + ", Фільм ID: "
            + sessionFromRepo.getMovieId());

        Seat newSeat = new Seat(1, 10, 5, 1);
        unitOfWork.getRepository(Seat.class).add(newSeat);

        Seat seatFromRepo = unitOfWork.getRepository(Seat.class).getById(1);
        System.out.println("Збережене місце: " + seatFromRepo.getId() + ", Місце: "
            + seatFromRepo.getSeatNumber());

        Payment newPayment = new Payment(1, 20.0, "2025-01-28 15:00", "card", 1);
        unitOfWork.getRepository(Payment.class).add(newPayment);

        Payment paymentFromRepo = unitOfWork.getRepository(Payment.class).getById(1);
        System.out.println("Збережений платіж: " + paymentFromRepo.getId() + ", Сума: "
            + paymentFromRepo.getAmount());
    }

}
