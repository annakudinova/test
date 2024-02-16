package gym.controller.storage.impl.db;

import gym.controller.storage.api.ClientDAO;
import gym.model.Client;
import gym.model.Membership;
import gym.model.Trainer;
import gym.util.GymException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Client save(Client client) throws GymException {
        String sql = "INSERT INTO clients (name, surname, date_of_birth, phone, email, photo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setDate(3, new Date(client.getDateOfBirth().getTime()));
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setBytes(6, client.getPhoto());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    client.setId(id);
                    return client;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public Client update(Client client) throws GymException {
        String sql = "UPDATE clients SET clients.name=?, clients.surname =?, clients.date_of_birth=?, clients.phone=?, clients.email=?, clients.photo=? WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setDate(3, client.getDateOfBirth());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setBytes(6, client.getPhoto());
            preparedStatement.setLong(7, client.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return client;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to update a client");
        }
        return null;
    }

    public Client delete(Client client) throws GymException {
        String sql = "DELETE FROM clients WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return client;
            }
        } catch (SQLException e) {
            throw new GymException("Failed to delete a client");
        }
        return null;
    }

    public Client find(Long id) throws GymException {
        String sql = "SELECT c.id, c.name, c.surname, c.date_of_birth, c.phone, c.email, c.photo FROM clients as c WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String phone = resultSet.getString(5);
                String email = resultSet.getString(6);
                byte[] photo = resultSet.getBytes(7);
                return new Client(id, name, surname, date, phone, email, photo);
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("Failed to find client");
        }

    }

    public List<Client> findByNameAndSurname(String name, String surname) throws GymException {
        String sql = "SELECT c.id, c.name, c.surname, c.date_of_birth, c.phone, c.email, c.photo FROM clients as c WHERE name =? AND surname =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            ResultSet resultSet = preparedStatement.executeQuery();
            Client client = null;
            ArrayList<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String clientName = resultSet.getString(2);
                String clientSurname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String phone = resultSet.getString(5);
                String email = resultSet.getString(6);
                byte[] photo = resultSet.getBytes(7);
                client = new Client(id, clientName, clientSurname, date, phone, email, photo);
                clients.add(client);
            }
            return clients;

        } catch (SQLException e) {
            throw new GymException("Failed to find a client!");
        }
    }

    public Client findByPhone(String phone) throws GymException {
        String sql = "SELECT c.id, c.name, c.surname, c.date_of_birth, c.phone, c.email, c.photo FROM clients as c WHERE phone=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                String phoneNumber = resultSet.getString(5);
                String email = resultSet.getString(6);
                byte[] photo = resultSet.getBytes(7);
                return new Client(id, name, surname, date, phoneNumber, email, photo);
            }
            return null;

        } catch (SQLException e) {
            throw new GymException("Failed to find a client");
        }
    }

    public boolean addTrainer(Client client, Trainer trainer) throws GymException {
        String sql = "INSERT INTO clients_trainers (client_id, trainer_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, trainer.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new GymException("Failed to save client and his trainer");
        }
    }

    public boolean addMembership(Client client, Membership membership, int cost) throws GymException {
        String sql = "INSERT INTO clients_memberships (client_id, membership_id, cost) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, membership.getId());
            preparedStatement.setInt(3, cost);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new GymException("Failed to save client and his membership");
        }
    }

    public boolean deleteTrainer(Client client, Trainer trainer) throws GymException {
        String sql = "DELETE FROM clients_trainers WHERE client_id=? AND trainer_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, trainer.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new GymException("Failed to delete client and his trainer");
        }

    }

    public boolean deleteMembership(Client client, Membership membership) throws GymException {
        String sql = "DELETE FROM clients_memberships WHERE client_id=? AND membership_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, membership.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new GymException("Failed to delete client and his membership");
        }
    }

    public Long findTrainer(Client client) throws GymException {
        String sql = "SELECT trainer_id FROM clients_trainers WHERE client_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long trainerId = resultSet.getLong(1);
                return trainerId;
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("Failed to find a trainer");
        }
    }

    public Long findMembership(Client client) throws GymException {
        String sql = "SELECT membership_id FROM clients_memberships WHERE client_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long membershipId = resultSet.getLong(1);
                return membershipId;
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("Failed to find a membership");
        }
    }

    public Integer findRemainedClasses(Client client) throws GymException {
        String sql = " SELECT ROUND(((SELECT SUM(gym.payments.sum) AS total_sum\n" +
                "FROM gym.payments\n" +
                "INNER JOIN gym.clients ON gym.payments.client_id = gym.clients.id\n" +
                "INNER JOIN gym.memberships ON gym.payments.membership_id = gym.memberships.id\n" +
                "WHERE gym.clients.id = ?\n" +
                "AND DATEDIFF(CURRENT_DATE(), gym.payments.date) <= gym.memberships.duration) - \n" +
                "(SELECT COUNT(gym.visits.id) as visits_number\n" +
                "FROM  gym.visits INNER JOIN gym.clients ON gym.visits.client_id = gym.clients.id\n" +
                "WHERE gym.clients.id = ?  AND gym.visits.date >= (SELECT MAX(gym.payments.date) FROM gym.payments \n" +
                "INNER JOIN gym.clients ON gym.payments.client_id = gym.clients.id WHERE gym.clients.id = ?)\n" +
                "AND gym.visits.date <= (SELECT DATE_ADD(MAX(gym.payments.date), INTERVAL gym.memberships.duration DAY)\n" +
                "FROM gym.payments  INNER JOIN gym.clients ON gym.payments.client_id = gym.clients.id\n" +
                "INNER JOIN gym.memberships ON gym.payments.membership_id = gym.memberships.id WHERE gym.clients.id = ?))  *  \n" +
                "(SELECT ROUND(gym.memberships.cost / gym.memberships.number, 0) as cost_per_one_class FROM gym.memberships \n" +
                "INNER JOIN gym.clients_memberships ON gym.memberships.id = gym.clients_memberships.membership_id INNER JOIN gym.clients ON gym.clients.id = gym.clients_memberships.client_id \n" +
                "WHERE gym.clients.id = ?)) / (SELECT ROUND(gym.memberships.cost / gym.memberships.number, 0) as cost_per_one_class FROM gym.memberships \n" +
                "INNER JOIN gym.clients_memberships ON gym.memberships.id = gym.clients_memberships.membership_id INNER JOIN gym.clients ON gym.clients.id = gym.clients_memberships.client_id \n" +
                "WHERE gym.clients.id = ?), 0) as remaining_classes";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, client.getId());
            preparedStatement.setLong(3, client.getId());
            preparedStatement.setLong(4, client.getId());
            preparedStatement.setLong(5, client.getId());
            preparedStatement.setLong(6, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("remaining_classes");
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("The number of remained classes is 0.");
        }
    }

    public Integer showMembershipBalance(Client client) throws GymException {
        String sql = "SELECT  ((SELECT SUM(gym.payments.sum) AS total_sum\n" +
                "FROM gym.payments\n" +
                "INNER JOIN gym.clients ON gym.payments.client_id = gym.clients.id\n" +
                "INNER JOIN gym.memberships ON gym.payments.membership_id = gym.memberships.id\n" +
                "WHERE gym.clients.id = ?\n" +
                "AND DATEDIFF(CURRENT_DATE(), gym.payments.date) <= gym.memberships.duration) - (SELECT COUNT(gym.visits.id) as visits_number\n" +
                "FROM  gym.visits INNER JOIN gym.clients ON gym.visits.client_id = gym.clients.id\n" +
                "WHERE gym.clients.id = ?  AND gym.visits.date >= (SELECT MAX(gym.payments.date) FROM gym.payments \n" +
                "INNER JOIN gym.clients ON gym.payments.client_id = gym.clients.id WHERE gym.clients.id = ?)\n" +
                "AND gym.visits.date <= (SELECT DATE_ADD(MAX(gym.payments.date), INTERVAL gym.memberships.duration DAY)\n" +
                "FROM gym.payments  INNER JOIN gym.clients ON gym.payments.client_id = gym.clients.id\n" +
                "INNER JOIN gym.memberships ON gym.payments.membership_id = gym.memberships.id WHERE gym.clients.id = ?))  *  (SELECT ROUND(gym.memberships.cost / gym.memberships.number, 0) as cost_per_one_class FROM gym.memberships \n" +
                "INNER JOIN gym.clients_memberships ON gym.memberships.id = gym.clients_memberships.membership_id INNER JOIN gym.clients ON gym.clients.id = gym.clients_memberships.client_id \n" +
                " WHERE gym.clients.id = ?)) as balance;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setLong(2, client.getId());
            preparedStatement.setLong(3, client.getId());
            preparedStatement.setLong(4, client.getId());
            preparedStatement.setLong(5, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("balance");
            }
            return null;
        } catch (SQLException e) {
            throw new GymException("The membership balance is 0.");
        }
    }

    public List<Client> showClientWithHighestNumberOfVisits() throws GymException {
        String sql = "SELECT gym.clients.id, gym.clients.name, gym.clients.surname, gym.clients.date_of_birth, gym.clients.phone, gym.clients.email, gym.clients.photo,COUNT(gym.visits.client_id) AS visits_count\n" +
                "FROM gym.visits INNER JOIN gym.clients ON gym.clients.id = gym.visits.client_id\n" +
                "WHERE MONTH(gym.visits.date) = MONTH(CURRENT_DATE()) AND YEAR(gym.visits.date) = YEAR(CURRENT_DATE())\n" +
                "GROUP BY gym.visits.client_id\n" +
                "HAVING COUNT(gym.visits.client_id) = (\n" +
                "  SELECT COUNT(gym.visits.client_id) AS visits_count\n" +
                "    FROM gym.visits\n" +
                "    WHERE MONTH(gym.visits.date) = MONTH(CURRENT_DATE()) AND YEAR(gym.visits.date) = YEAR(CURRENT_DATE())\n" +
                "    GROUP BY gym.visits.client_id\n" +
                "    ORDER BY visits_count DESC\n" +
                "    LIMIT 1\n" +
                ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                Long clientId = resultSet.getLong("id");
                String clientName = resultSet.getString("name");
                String clientSurname = resultSet.getString("surname");
                Date date = resultSet.getDate("date_of_birth");
                String phoneNumber = resultSet.getString("phone");
                String email = resultSet.getString("email");
                byte[] photo = resultSet.getBytes("photo");
                Client client = new Client(clientId, clientName, clientSurname, date, phoneNumber, email, photo);
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new GymException("Failed to show a client!");
        }
    }


    public boolean changeMembership(long clientId, long oldMembershipId, long newMembershipId, int cost) throws GymException {
        String deleteSql = "DELETE FROM gym.clients_memberships WHERE gym.clients_memberships.client_id = ? AND gym.clients_memberships.membership_id = ?";
        String insertSql = "INSERT INTO gym.clients_memberships (client_id, membership_id, cost) VALUES (?, ?, ?)" +
                " ON DUPLICATE KEY UPDATE gym.clients_memberships.client_id = VALUES(client_id), " +
                "gym.clients_memberships.membership_id = VALUES(membership_id), gym.clients_memberships.cost = VALUES(cost)";
        try {

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setLong(1, clientId);
                deleteStatement.setLong(2, oldMembershipId);
                deleteStatement.executeUpdate();
            }
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                insertStatement.setLong(1, clientId);
                insertStatement.setLong(2, newMembershipId);
                insertStatement.setInt(3, cost);
                insertStatement.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new GymException("Failed to change membership" );
        }
    }

}




