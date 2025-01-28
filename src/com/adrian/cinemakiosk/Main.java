package com.adrian.cinemakiosk;

import com.adrian.cinemakiosk.domain.servise.uow.UnitOfWork;
import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.repository.Repository;
import java.util.List;

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
        Repository<Ticket> ticketRepo = (Repository<Ticket>) unitOfWork.getRepository(Ticket.class);

        // Create (Add)
        Ticket ticket1 = new Ticket(1, 500, "A1", "BB", 1, 1, 1);
        Ticket ticket2 = new Ticket(2, 300, "B2", "AA", 2, 2, 2);
        ticketRepo.add(ticket1);
        ticketRepo.add(ticket2);

        // Read (Get All)
        List<Ticket> tickets = ticketRepo.getAll();
        tickets.forEach(System.out::println);

        // Update
        ticket1.setPrice(220.00);
        ticketRepo.update(ticket1);

        // Get by ID
        Ticket foundTicket = ticketRepo.getById(1);
        System.out.println("Found Ticket: " + foundTicket);

        // Filter
        List<Ticket> filteredTickets = ticketRepo.filter("movieName", "Avatar");
        filteredTickets.forEach(System.out::println);

        // Delete
        ticketRepo.deleteById(2);
    }

}
