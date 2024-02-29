
package gym.view2.clients;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.util.GymException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class ClientWindow extends JFrame {
    private JPanel clientPanel;
    private JButton findButton;
    private JButton deleteButton;
    private JButton editButton1;
    private JButton addButton;
    private JTable clientsTable;
    private JButton balanceButton;
    private JButton classesNumberButton;
    private JButton expirationDateButton;
    private JButton bonusButton;
    private JComboBox find;
    private JButton statisticsButton;
    private ClientDAOImpl clientDAO;

//    public ClientWindow() {
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle("Clients Window");
//        //setSize(1000, 900);
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
//            clientDAO = new ClientDAOImpl(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        add(clientPanel);
//        //  setContentPane(clientPanel);
//        //setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//        setMinimumSize(new Dimension(950, 600));
//        setLocationRelativeTo(null);
//
//        setVisible(true);
//
//        DefaultTableModel model = new DefaultTableModel(0, 6);
//        model.setColumnIdentifiers(new Object[]{"id", "name", "surname", "date of birth", "phone", "email"});
//
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1")) {
//            try (Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery("SELECT * FROM clients")) {
//                while (resultSet.next()) {
//                    model.addRow(new Object[]{
//                            resultSet.getLong("id"),
//                            resultSet.getString("name"),
//                            resultSet.getString("surname"),
//                            resultSet.getDate("date_of_birth"),
//                            resultSet.getString("phone"),
//                            resultSet.getString("email"),
//                          //  resultSet.getBytes("photo")
//                    });
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        clientsTable.setModel(model);
//        addButton.addActionListener(e -> openAddClientWindow(clientsTable));
//        pack();
//        setVisible(true);
//
//        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"Search", "by name and surname", "by phone"});
//        find.setModel(comboBoxModel);
//
//        find.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame frame = new JFrame();
//                String selectedItem = (String) find.getSelectedItem();
//                if ("Search".equals(selectedItem)) {
//                    return;
//                } else if ("by name and surname".equals(selectedItem)) {
//                   FindClientByName findClientByName = new FindClientByName();
//                    frame.setTitle("Find Client By Name and Surname");
//                    frame.setContentPane(findClientByName.getFindByNamePanel());
//                } else if ("by phone".equals(selectedItem)) {
//                    FindClientByPhone findClientByPhone = new FindClientByPhone();
//                    frame.setTitle("Find Client By Phone");
//                    frame.setContentPane(findClientByPhone.getFindByPhonePanel());
//                }
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame.setSize(new Dimension(600, 600));
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//            }
//        });
//statisticsButton.addActionListener(new ActionListener() {
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        try {
//            List<Client> clients = clientDAO.showClientWithHighestNumberOfVisits();
//            StringBuilder message = new StringBuilder("Clients with the highest number of visits this month:\n");
//            for (Client client : clients) {
//                message.append(client.getName()).append(" ").append(client.getSurname()).append("\n");
//            }
//            JOptionPane.showMessageDialog(ClientWindow.this, message.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
//        } catch (GymException ex) {
//            JOptionPane.showMessageDialog(ClientWindow.this, "Error showing client statistics", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//
//    }
//});
//        balanceButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = clientsTable.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(null, "Select a client to look through a balance");
//                    return;
//                }
//                long clientId = (long) clientsTable.getValueAt(selectedRow, 0);
//                try {
//                    Client client = clientDAO.find(clientId);
//                    Integer balance = clientDAO.showMembershipBalance(client);
//                    JOptionPane.showMessageDialog(ClientWindow.this, "Membership balance: " + balance, "Balance", JOptionPane.INFORMATION_MESSAGE);
//                } catch (GymException ex) {
//                    JOptionPane.showMessageDialog(ClientWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//
//        });
//        classesNumberButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = clientsTable.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(null, "Select a client to look through a number of remaining classes");
//                    return;
//                }
//                long clientId = (long) clientsTable.getValueAt(selectedRow, 0);
//                try {
//                    Client client = clientDAO.find(clientId);
//                    Integer remainingClasses = clientDAO.findRemainedClasses(client);
//                    JOptionPane.showMessageDialog(ClientWindow.this, "Number of remaining classes: " + remainingClasses, "Remaining classes", JOptionPane.INFORMATION_MESSAGE);
//
//                } catch (GymException ex) {
//                    JOptionPane.showMessageDialog(ClientWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//
//                }
//            }
//
//        });
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = clientsTable.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(ClientWindow.this, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
//                    long id = (long) model.getValueAt(selectedRow, 0);
//                    try {
//                        Client clientToDelete = new Client();
//                        clientToDelete.setId(id);
//                        clientDAO.delete(clientToDelete);
//                        model.removeRow(selectedRow);
//                    } catch (GymException ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(ClientWindow.this, "Ошибка при удалении клиента", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(ClientWindow.this, "Произошла неизвестная ошибка", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        });
//        expirationDateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = clientsTable.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(null, "Select a client to know an expiration date");
//                    return;
//                }
//                long clientId = (long) clientsTable.getValueAt(selectedRow, 0);
//                try {
//                    Date date = clientDAO.showExpirationDate(new Client(clientId));
//                    JOptionPane.showMessageDialog(ClientWindow.this, "Expiration date: " + date, "Expiration date", JOptionPane.INFORMATION_MESSAGE);
//
//                } catch (GymException ex) {
//                    JOptionPane.showMessageDialog(ClientWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//
//                }
//            }
//
//        });
//        bonusButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    List<Client> birthdayPersons = clientDAO.findBirthdayPerson();
//                    if (birthdayPersons.isEmpty()) {
//                        JOptionPane.showMessageDialog(ClientWindow.this, "No clients have a birthday today.", "Bonus", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        StringBuilder message = new StringBuilder("Today is the birthday of the following clients:\n");
//                        for (Client client : birthdayPersons) {
//                            message.append(client.getName()).append(" ").append(client.getSurname()).append("\n");
//                        }
//                        message.append("They are entitled to one bonus class.");
//                        JOptionPane.showMessageDialog(ClientWindow.this, message.toString(), "Bonus", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } catch (GymException ex) {
//                    JOptionPane.showMessageDialog(ClientWindow.this, "Failed to retrieve birthday information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//
//        });
//        editButton1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = clientsTable.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(ClientWindow.this, "Select a row to edit");
//                } else {
//                    Long clientId = (Long) clientsTable.getValueAt(selectedRow, 0);
//                    try {
//                        Client client = clientDAO.find(clientId);
//                        EditClient editClient = new EditClient(client, ClientWindow.this);
//                        editClient.setVisible(true);
//                    } catch (GymException ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(ClientWindow.this, "Error editing a trainer", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                }           }
//
//        });
//    }
//
//    private void openAddClientWindow(JTable clientsTable) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                AddClient addClient = new AddClient(ClientWindow.this);
//                addClient.setVisible(true);
//            }
//        });
//    }
//
//    public JTable getClientsTable() {
//        return clientsTable;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new ClientWindow();
//
//            }
//        });
//    }

    public ClientWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clients Window");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        clientPanel = new JPanel(new BorderLayout());
        setContentPane(clientPanel);
        addButton = new JButton("Add");
        editButton1 = new JButton("Edit");
        deleteButton = new JButton("Delete");
        balanceButton = new JButton("Balance");
        classesNumberButton = new JButton("Remaining classes");
        expirationDateButton = new JButton("Expiration date");
        bonusButton = new JButton("Bonus");
        statisticsButton = new JButton("Statistics");
        find = new JComboBox<>(new String[]{"Search", "by name and surname", "by phone"});
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton1);
        buttonPanel.add(deleteButton);
        buttonPanel.add(find);
        buttonPanel.add(balanceButton);
        buttonPanel.add(classesNumberButton);
        buttonPanel.add(expirationDateButton);
        buttonPanel.add(bonusButton);
        buttonPanel.add(statisticsButton);
        clientPanel.add(buttonPanel, BorderLayout.NORTH);
        clientsTable = new JTable(new DefaultTableModel(new Object[]{"Client id", "Name", "Surname", "Date of birth", "Phone", "Email"}, 0));
        JTableHeader header = clientsTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1")) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM clients")) {
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getDate("date_of_birth"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clientPanel.add(new JScrollPane(clientsTable), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        addButton.addActionListener(e -> openAddClientWindow());
        editButton1.addActionListener(e -> editClient());
        deleteButton.addActionListener(e -> deleteClient());
        find.addActionListener(e -> findAction());
        balanceButton.addActionListener(e -> showBalance());
        classesNumberButton.addActionListener(e -> showRemainingClasses());
        expirationDateButton.addActionListener(e -> showExpirationDate());
        bonusButton.addActionListener(e -> showBonus());
        statisticsButton.addActionListener(e -> showStatistics());
        setVisible(true);
    }

    private void openAddClientWindow() {
        SwingUtilities.invokeLater(() -> {
            AddClient addClient = new AddClient(ClientWindow.this);
            addClient.setVisible(true);
        });
    }

    private void editClient() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(ClientWindow.this, "Select a row to edit", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Long clientId = (Long) clientsTable.getValueAt(selectedRow, 0);
            try {
                Client client = clientDAO.find(clientId);
                EditClient editClient = new EditClient(client, ClientWindow.this);
                editClient.setVisible(true);
            } catch (GymException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ClientWindow.this, "Error editing a client", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteClient() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(ClientWindow.this, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
            long id = (long) model.getValueAt(selectedRow, 0);
            try {
                Client clientToDelete = new Client();
                clientToDelete.setId(id);
                clientDAO.delete(clientToDelete);
                model.removeRow(selectedRow);
            } catch (GymException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ClientWindow.this, "Error deleting a client", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ClientWindow.this, "Unknown Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void findAction() {
        JFrame frame = new JFrame();
        String selectedItem = (String) find.getSelectedItem();
        if ("Search".equals(selectedItem)) {
            return;
        } else if ("by name and surname".equals(selectedItem)) {
            FindClientByName findClientByName = new FindClientByName();
            frame.setTitle("Find Client By Name and Surname");
            frame.setContentPane(findClientByName.getFindByNamePanel());
        } else if ("by phone".equals(selectedItem)) {
            FindClientByPhone findClientByPhone = new FindClientByPhone();
            frame.setTitle("Find Client By Phone");
            frame.setContentPane(findClientByPhone.getFindByPhonePanel());
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(600, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showBalance() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a client to look through a balance");
            return;
        }
        long clientId = (long) clientsTable.getValueAt(selectedRow, 0);
        try {
            Client client = clientDAO.find(clientId);
            Integer balance = clientDAO.showMembershipBalance(client);
            JOptionPane.showMessageDialog(ClientWindow.this, "Membership balance: " + balance, "Balance", JOptionPane.INFORMATION_MESSAGE);
        } catch (GymException ex) {
            JOptionPane.showMessageDialog(ClientWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showRemainingClasses() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a client to look through a number of remaining classes");
            return;
        }
        long clientId = (long) clientsTable.getValueAt(selectedRow, 0);
        try {
            Client client = clientDAO.find(clientId);
            Integer remainingClasses = clientDAO.findRemainedClasses(client);
            JOptionPane.showMessageDialog(ClientWindow.this, "Number of remaining classes: " + remainingClasses, "Remaining classes", JOptionPane.INFORMATION_MESSAGE);

        } catch (GymException ex) {
            JOptionPane.showMessageDialog(ClientWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void showBonus() {
        try {
            List<Client> birthdayPersons = clientDAO.findBirthdayPerson();
            if (birthdayPersons.isEmpty()) {
                JOptionPane.showMessageDialog(ClientWindow.this, "No clients have a birthday today.", "Bonus", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder message = new StringBuilder("Today is the birthday of the following clients:\n");
                for (Client client : birthdayPersons) {
                    message.append(client.getName()).append(" ").append(client.getSurname()).append("\n");
                }
                message.append("They are entitled to one bonus class.");
                JOptionPane.showMessageDialog(ClientWindow.this, message.toString(), "Bonus", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (GymException ex) {
            JOptionPane.showMessageDialog(ClientWindow.this, "Failed to retrieve birthday information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showStatistics() {
        try {
            List<Client> clients = clientDAO.showClientWithHighestNumberOfVisits();
            StringBuilder message = new StringBuilder("Clients with the highest number of visits this month:\n");
            for (Client client : clients) {
                message.append(client.getName()).append(" ").append(client.getSurname()).append("\n");
            }
            JOptionPane.showMessageDialog(ClientWindow.this, message.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
        } catch (GymException ex) {
            JOptionPane.showMessageDialog(ClientWindow.this, "Error showing client statistics", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showExpirationDate() {
        int selectedRow = clientsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Select a client to know an expiration date");
            return;
        }
        long clientId = (long) clientsTable.getValueAt(selectedRow, 0);
        try {
            Date date = clientDAO.showExpirationDate(new Client(clientId));
            JOptionPane.showMessageDialog(ClientWindow.this, "Expiration date: " + date, "Expiration date", JOptionPane.INFORMATION_MESSAGE);

        } catch (GymException ex) {
            JOptionPane.showMessageDialog(ClientWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTable getClientsTable() {
        return clientsTable;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

}








