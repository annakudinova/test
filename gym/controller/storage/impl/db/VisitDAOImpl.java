package gym.controller.storage.impl.db;

import gym.controller.storage.api.DAO;
import gym.controller.storage.api.VisitDAO;
import gym.model.Category;
import gym.model.Client;
import gym.model.Visit;
import gym.util.GymException;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VisitDAOImpl implements VisitDAO {
    private Connection connection;

    public VisitDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Visit save(Visit visit) throws GymException {
        String sql = "INSERT INTO visits (client_id, date, locker_number) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, visit.getClient().getId());
            preparedStatement.setDate(2, visit.getDate());
            preparedStatement.setInt(3, visit.getLockerNumber());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    visit.setId(id);
                    return visit;
                }
            }
        } catch (SQLException e) {
            throw new GymException("Failed to save visit");
        }
        return null;
    }

    public Visit update(Visit visit) throws GymException {
        String sql = "UPDATE visits SET visits.date=?, visits.locker_number =? WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, visit.getDate());
            preparedStatement.setInt(2, visit.getLockerNumber());
            if (preparedStatement.executeUpdate() > 0) {
                return visit;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to update visit");
        }
        return null;
    }

    public Visit delete(Visit visit) throws GymException {
        return null;

    }

    public Visit find(Long id) throws GymException {
        return null;
    }


}
