package gym.controller.storage.impl.db;

import gym.controller.storage.api.MembershipsDAO;
import gym.model.Category;
import gym.model.Membership;
import gym.model.Trainer;
import gym.util.GymException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAOImpl implements MembershipsDAO {
    private Connection connection;

    public MembershipDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Membership save(Membership membership) throws GymException {
        String sql = "INSERT INTO memberships (title, description, number, cost, duration)" +
                " VALUES (?, ?, ?, ?, ?)";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, membership.getTitle());
            preparedStatement.setString(2, membership.getDescription());
            preparedStatement.setInt(3, membership.getNumber());
            preparedStatement.setInt(4, membership.getCost());
            preparedStatement.setInt(5, membership.getDuration());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    membership.setId(id);
                    return membership;
                }
            }
        } catch (SQLException e) {
            throw new GymException("Failed to save a membership");
        }
        return null;
    }

    public Membership update(Membership membership) throws GymException {
        String sql = "UPDATE memberships SET memberships.title=?, memberships.description =?, memberships.number=?, memberships.cost=?, memberships.duration=? WHERE id =?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, membership.getTitle());
            preparedStatement.setString(2, membership.getDescription());
            preparedStatement.setInt(3, membership.getNumber());
            preparedStatement.setInt(4, membership.getCost());
            preparedStatement.setInt(5, membership.getDuration());
            if (preparedStatement.executeUpdate() > 0) {
                return membership;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to update membership");
        }
        return null;
    }

    public Membership delete(Membership membership) throws GymException {
        String sql = "DELETE FROM memberships WHERE id=?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, membership.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return membership;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to delete membership");
        }
        return null;
    }

    public List<Membership> findAll() throws GymException {
        String sql = "SELECT memberships.title, memberships.description, memberships.number, memberships.cost, memberships.duration FROM gym.memberships";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Membership> memberships = new ArrayList<>();
            while (resultSet.next()) {
                long membershipId = resultSet.getLong(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int number = resultSet.getInt(4);
                int cost = resultSet.getInt(5);
                int duration = resultSet.getInt(6);
                Membership membership = new Membership(membershipId, title, description, number, cost, duration);
                memberships.add(membership);
            }
            return memberships;

        } catch (SQLException e) {
            throw new GymException("Failed to find all memberships");
        }

    }

    public Membership find(Long id) throws GymException {
        String sql = "SELECT memberships.title, memberships.description, memberships.number, memberships.cost, memberships.duration FROM gym.memberships WHERE id=?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                int number = resultSet.getInt(4);
                int cost = resultSet.getInt(5);
                int duration = resultSet.getInt(6);
                return new Membership(id, title, description, number, cost, duration);
            }
            return null;

        } catch (SQLException e) {
            throw new GymException("Failed to find a membership");
        }
    }

    public List<Membership> findByTitle(String title) throws GymException {
        String sql = "SELECT memberships.title, memberships.description, memberships.number, memberships.cost, memberships.duration FROM gym.memberships WHERE title=?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            Membership membership = null;
            ArrayList<Membership> memberships = new ArrayList<>();
            if (resultSet.next()) {
                long membershipId = resultSet.getLong(1);
                String membershipTitle = resultSet.getString(2);
                String description = resultSet.getString(3);
                int number = resultSet.getInt(4);
                int cost = resultSet.getInt(5);
                int duration = resultSet.getInt(6);
                membership = new Membership(membershipId, membershipTitle, description, number, cost, duration);
                memberships.add(membership);
            }
            return memberships;

        } catch (SQLException e) {
            throw new GymException("Failed to find membership");
        }
    }


}
