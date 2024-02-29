package gym.controller.storage.impl.db;

import gym.controller.storage.api.DAO;
import gym.model.Category;
import gym.model.Client;
import gym.util.GymException;

import java.sql.*;

public class CategoryDAOImpl implements DAO<Category, Long> {
    private Connection connection;

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Category save(Category category) throws GymException {
        String sql = "INSERT INTO categories (id, title) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, category.getId());
            preparedStatement.setString(2, category.getTitle());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    category.setId(id);
                    return category;
                }
            }
        } catch (SQLException e) {
            throw new GymException("Failed to save category");
        }
        return null;
    }

    public Category update(Category category) throws GymException {
        return null;
    }

    public Category delete(Category category) throws GymException {
        String sql = "DELETE FROM categories WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, category.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return category;
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("Failed to delete a category");
        }

    }

    public Category find(Long id) throws GymException {
        return null;
    }

}
