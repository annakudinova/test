package gym.controller.storage.impl.db;

import gym.controller.storage.api.ClientDAO;
import gym.controller.storage.api.DAO;
import gym.model.*;
import gym.util.GymException;

import java.sql.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) throws GymException {
        DAOFactory daoFactory = DAOFactory.getDaoFactory();
        DAO<Client, Long> clientDAO = daoFactory.getDAO(Client.class);
        ClientDAOImpl clientDAOImpl = (ClientDAOImpl) clientDAO;
//        DAO<Visit, Long> visitDAO = daoFactory.getDAO(Visit.class);
//        VisitDAOImpl visitDAOImpl = (VisitDAOImpl) visitDAO;
//        Visit visit1 = visitDAOImpl.save(new Visit(new Client(1L), new Date(2024 - 1900, 2, 10), 12));
//        String info = visit1.toString();
//        System.out.println(info);
//        Integer remainedClasses = clientDAOImpl.findRemainedClasses(new Client(6L));
//        System.out.println("The remaining classes: " + remainedClasses);
//        if (clientDAOImpl.findRemainedClasses(new Client(6L)) == 1) {
//            System.out.println("Pay attention that only 1 class remained");
//        }
        DAO<Membership, Long> membershipDAO = daoFactory.getDAO(Membership.class);
        MembershipDAOImpl membershipDAOImpl = (MembershipDAOImpl) membershipDAO;

        DAO<Trainer, Long> trainerDAO = daoFactory.getDAO(Trainer.class);
        TrainerDAOImpl trainerDAOImpl =(TrainerDAOImpl) trainerDAO;
        List<Trainer> foundTrainers = trainerDAOImpl.findByCategory(1);
        String info = foundTrainers.toString();
        System.out.println(info);
//        Membership foundMembership = membershipDAOImpl.find(109L);
//        String info = foundMembership.toString();
//        System.out.println(info);



//        Client client = clientDAOImpl.changeMembership(new Client(1L), new Membership(5L, 624));
//        String info = client.toString();
//        System.out.println(info);

        //бонус
//        List<Client> foundClient = clientDAOImpl.findBirthdayPerson();
//        String info = foundClient.toString();
//        System.out.println(info);
//        List<Client> foundClient = clientDAOImpl.showClientWithHighestNumberOfVisits();
//        String info = foundClient.toString();
//        System.out.println(info);
        //просмотреть сколько осталось занятий
//        int remainedClasses = clientDAOImpl.findRemainedClasses(new Client(7L));
//        System.out.println("Remaining classes: " + remainedClasses);
        //проверить баланс абонемента по id клиента
//        int clientBalance = clientDAOImpl.showMembershipBalance(new Client(3L));
//        System.out.println("Membership balance is : " + clientBalance + " \n " + "Top up your membership!");

        //methods for Client
        //save

        Client client1 = new Client("Nataliya", "Teslenko", new Date(2002 - 1900, 4, 3),
                "+380987345667", "natalya.teslenko@gmail.com", null);
        // DAO<Client, Long> dao = daoFactory.getDAO(Client.class);
//        dao.save(client1);
//        String info = client1.toString();
//        System.out.println(info);

//        Date expirationDate = clientDAOImpl.showExpirationDate(new Client(1L));
//        System.out.println(expirationDate);
        //Remained classes count

        //update
//        client1.setEmail("teslenkonataliya@gmail.com");
//        client1.setId(41L);
//        Client updatedClient = clientDAO.update(client1);
//        String info3 = updatedClient.toString();
//        System.out.println(info3);
        //delete
//        Client deletedClient1 = clientDAO.delete(new Client(55L));
//        String info2 = deletedClient1.toString();
//        System.out.println(info2);
        //findByNameAndSurname
//        List<Client> foundClients = clientDAO.findByNameAndSurname("Inna", "Uvarova") ;
//        String info3 = foundClients.toString();
//        System.out.println(info3);
        //findById
//        Client foundClient = clientDAO.find(1L);
//        String info4 = foundClient.toString();
//        System.out.println(info4);
        //findByPhone
//        Client foundClient = clientDAO.findByPhone("+380998567567");
//        String info5 = foundClient.toString();
//        System.out.println(info5);

        //methods for Trainer
        //save
        //  TrainerDAOImpl trainerDAO = new TrainerDAOImpl();
        Trainer trainer1 = new Trainer("Oleg", "Sydorenko", new Date(1984 - 1900, 3, 7),
                "Master of sports in powerlifting", new Category(4L), "power training", 300, "+380987564332");
//        trainerDAO.save(trainer1);
//        String info1 = trainer1.toString();
//        System.out.println(info1);
        //update
//        trainer1.setDirection("cross-fit");
//        trainer1.setId(37L);
//        Trainer updatedTrainer = trainerDAO.update(trainer1);
//        String info2 = updatedTrainer.toString();
//        System.out.println(info2);
        //delete
//        Trainer deletedTrainer = trainerDAO.delete(new Trainer(37L));
//        String info3 = deletedTrainer.toString();
//        System.out.println(info3);
        //findById
//        Trainer foundTrainer1 = trainerDAO.find(19L);
//        String info4 = foundTrainer1.toString();
//        System.out.println(info4);
//        //findByNameAndSurname
//        List<Trainer> foundTrainer2 = trainerDAO.findByNameAndSurname("Angelina", "Klimova");
//        String info5 = foundTrainer2.toString();
//        System.out.println(info5);
//        //findByCategory
//        List<Trainer> foundTrainer3 = trainerDAO.findByCategory(1L);
//        String info6 = foundTrainer3.toString();
//        System.out.println(info6);
//        //findByDirection
//        List<Trainer> foundTrainer4 = trainerDAO.findByDirection("TRX");
//        String info7 = foundTrainer4.toString();
//        System.out.println(info7);
//        //findAllTrainers
//        List<Trainer> foundTrainers = trainerDAO.findAll();
//        String info8 = foundTrainers.toString();
//        System.out.println(info8);

        //save data into table clients_memberships
//        boolean result1 = clientDAO.addMembership(new Client(36L),new Membership(11L));
//        System.out.println(result1);

        //save data into table clients_trainers
//        boolean result2 = clientDAO.addTrainer(new Client(55L), new Trainer(15L));
//        System.out.println(result2);

        //delete data from table clients_memberships
//        boolean result3 = clientDAO.deleteMembership(new Client(36L), new Membership(11L));
//        System.out.println(result3);
        //delete data from table clients_trainers
//        boolean result4 = clientDAO.deleteTrainer(new Client(36L), new Trainer(15L));
//        System.out.println(result4);

        //find trainer by client id from clients_trainers table
//        long foundTrainer = clientDAO.findTrainer(new Client(3L));
//        System.out.println("Trainer id: " + foundTrainer);

        //find membership by client_id from clients_memberships table
//        long foundMembership = clientDAO.findMembership(new Client(3L));
//        System.out.println("Membership id: " + foundMembership);

        //find clients by trainer_id in clients_trainers table
//        List<Long> foundClients = trainerDAO.findClients(new Trainer(16L));
//        System.out.println(foundClients);

        //save category
        //CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
//        Category category1 = new Category(4L, "Fourth category");
//        Category category = categoryDAO.save(category1);
//        System.out.println(category.toString());
        //delete category
//        Category deletedCategory = categoryDAO.delete(new Category(4L));
//        String info = deletedCategory.toString();
//        System.out.println(info);

        //find visits by client id
//        VisitDAOImpl visitDAO = new VisitDAOImpl();
//        List<Visit> visits = visitDAO.findByClientId(new Visit(new Client(1L)));
//        String info = visits.toString();
//        System.out.println(info);


    }

}
