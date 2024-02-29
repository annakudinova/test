package gym.controller.storage.impl.db;

import gym.controller.storage.api.DAO;
import gym.model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DAOFactory {
    private static DAOFactory daoFactory;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DAOFactory.class.getClassLoader().getResourceAsStream("storage.properties"));
            String storageType = properties.getProperty("type");
            if (storageType.equals("database")) {
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                String url = properties.getProperty("url");
                daoFactory = new JdbcDAOFactory(user, url, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DAOFactory() {

    }

    public abstract void close();

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }


    public abstract <E extends Entity<E, K>, K> DAO<E, K> getDAO(Class<?> clazz);

    private static class JdbcDAOFactory extends DAOFactory {
        private Connection connection;

        private JdbcDAOFactory(String user, String url, String password) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public <E extends Entity<E, K>, K> DAO<E, K> getDAO(Class<?> clazz) {
            try {
                Object object = clazz.newInstance();
                if (object instanceof Client) {
                    return (DAO<E, K>) new ClientDAOImpl(connection);
                }
                if (object instanceof Trainer) {
                    return (DAO<E, K>) new TrainerDAOImpl(connection);
                }
                if (object instanceof Membership) {
                    return (DAO<E, K>) new MembershipDAOImpl(connection);
                }
                if (object instanceof Category) {
                    return (DAO<E, K>) new CategoryDAOImpl(connection);
                }
                if (object instanceof Visit) {
                    return (DAO<E, K>) new VisitDAOImpl(connection);
                }

            } catch (IllegalAccessException | InstantiationException e) {

            }
            return null;

        }

        @Override
        public void close() {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }


}
