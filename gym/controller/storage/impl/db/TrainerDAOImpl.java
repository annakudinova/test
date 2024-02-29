package gym.controller.storage.impl.db;

import gym.controller.storage.api.TrainerDAO;
import gym.model.Category;
import gym.model.Client;
import gym.model.Trainer;
import gym.util.GymException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAOImpl implements TrainerDAO {
    private Connection connection;

    public TrainerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Trainer save(Trainer trainer) throws GymException {
        String sql = "INSERT INTO trainers (name, surname, date_of_birth, description, category, direction, cost, phone)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setString(2, trainer.getSurname());
            preparedStatement.setDate(3, new Date(trainer.getDateOfBirth().getTime()));
            preparedStatement.setString(4, trainer.getDescription());
            preparedStatement.setLong(5, trainer.getCategory().getId());
            preparedStatement.setString(6, trainer.getDirection());
            preparedStatement.setInt(7, trainer.getCost());
            preparedStatement.setString(8, trainer.getPhone());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    trainer.setId(id);
                    return trainer;
                }
            }
        } catch (SQLException e) {
            throw new GymException("Failed to save trainer");
        }
        return null;
    }

    public Trainer update(Trainer trainer) throws GymException {
        String sql = "UPDATE trainers SET trainers.name=?, trainers.surname =?, trainers.date_of_birth=?, trainers.description=?, trainers.category=?, trainers.direction=?, trainers.cost=?, trainers.phone=? WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setString(2, trainer.getSurname());
            preparedStatement.setDate(3, trainer.getDateOfBirth());
            preparedStatement.setString(4, trainer.getDescription());
            preparedStatement.setLong(5, trainer.getCategory().getId());
            preparedStatement.setString(6, trainer.getDirection());
            preparedStatement.setInt(7, trainer.getCost());
            preparedStatement.setString(8, trainer.getPhone());
            preparedStatement.setLong(9, trainer.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return trainer;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to update trainer");
        }
        return null;
    }

    public Trainer delete(Trainer trainer) throws GymException {
        String sql = "DELETE FROM trainers WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, trainer.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return trainer;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to delete trainer");
        }
        return null;
    }

    public Trainer find(Long id) throws GymException {
        String sql = "SELECT t.id, t.name, t.surname, t.date_of_birth, t.description, t.category, t.direction, t.cost, t.phone FROM gym.trainers as t WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String description = resultSet.getString(5);
                Long category = resultSet.getLong(6);
                String direction = resultSet.getString(7);
                int cost = resultSet.getInt(8);
                String phone = resultSet.getString(9);
                return new Trainer(id, name, surname, date, description, new Category(category), direction, cost, phone);
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("Failed to find a trainer");
        }
    }

    public List<Trainer> findByNameAndSurname(String name, String surname) throws GymException {
        String sql = "SELECT t.id, t.name, t.surname, t.date_of_birth, t.description, t.category, t.direction, t.cost, t.phone FROM gym.trainers as t WHERE name =? AND surname =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            Trainer trainer = null;
            ArrayList<Trainer> trainers = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String trainerName = resultSet.getString(2);
                String trainerSurname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String description = resultSet.getString(5);
                Long category = resultSet.getLong(6);
                String direction = resultSet.getString(7);
                int cost = resultSet.getInt(8);
                String phone = resultSet.getString(9);
                trainer = new Trainer(id, trainerName, trainerSurname, date, description, new Category(category), direction, cost, phone);
                trainers.add(trainer);
            }
            return trainers;

        } catch (SQLException e) {
            throw new GymException("Failed to find a trainer");
        }

    }

    public List<Trainer> findByCategory(long category) throws GymException {
        String sql = "SELECT t.id, t.name, t.surname, t.date_of_birth, t.description, t.category, t.direction, t.cost, t.phone FROM gym.trainers as t WHERE category=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            Trainer trainer = null;
            ArrayList<Trainer> trainers = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String trainerName = resultSet.getString(2);
                String trainerSurname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String description = resultSet.getString(5);
                Long trainerCategory = resultSet.getLong(6);
                String direction = resultSet.getString(7);
                int cost = resultSet.getInt(8);
                String phone = resultSet.getString(9);
                trainer = new Trainer(id, trainerName, trainerSurname, date, description, new Category(trainerCategory), direction, cost, phone);
                trainers.add(trainer);
            }
            return trainers;

        } catch (SQLException e) {
            throw new GymException("Failed to find a trainer");
        }

    }

    public List<Trainer> findByDirection(String direction) throws GymException {
        String sql = "SELECT t.id, t.name, t.surname, t.date_of_birth, t.description, t.category, t.direction, t.cost, t.phone FROM gym.trainers as t WHERE direction=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, direction);
            ResultSet resultSet = preparedStatement.executeQuery();
            Trainer trainer = null;
            ArrayList<Trainer> trainers = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String trainerName = resultSet.getString(2);
                String trainerSurname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String description = resultSet.getString(5);
                Long trainerCategory = resultSet.getLong(6);
                String trainerDirection = resultSet.getString(7);
                int cost = resultSet.getInt(8);
                String phone = resultSet.getString(9);
                trainer = new Trainer(id, trainerName, trainerSurname, date, description, new Category(trainerCategory), trainerDirection, cost, phone);
                trainers.add(trainer);
            }
            return trainers;

        } catch (SQLException e) {
            throw new GymException("Failed to find a trainer");
        }
    }

    public List<Trainer> findAll() throws GymException{
        String sql = "SELECT t.id, t.name, t.surname, t.date_of_birth, t.description, t.category, t.direction, t.cost, t.phone FROM gym.trainers as t";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Trainer> trainers = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String trainerName = resultSet.getString(2);
                String trainerSurname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String description = resultSet.getString(5);
                Long category = resultSet.getLong(6);
                String direction = resultSet.getString(7);
                int cost = resultSet.getInt(8);
                String phone = resultSet.getString(9);
                Trainer trainer = new Trainer(id, trainerName, trainerSurname, date, description, new Category(category), direction, cost, phone);
                trainers.add(trainer);
            }
            return trainers;

        } catch (SQLException e) {
            throw new GymException("Failed to find all trainers");
        }

    }
    public List<Long>findClients(Trainer trainer)throws GymException{
        String sql = "SELECT client_id FROM clients_trainers WHERE trainer_id=?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, trainer.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Long>clients = new ArrayList<>();
            while (resultSet.next()){
                long clientId = resultSet.getLong(1);
                clients.add(clientId);
            }
            return clients;
        } catch (SQLException e) {
            throw new GymException("Failed to find clients");
        }
    }
}


