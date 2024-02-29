package gym.view2.clients;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.util.GymException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class FindClientByName extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JButton findButton;
    private JButton cancelButton;
    private JPanel findByNamePanel;
    private ClientDAOImpl clientDAO;

    public FindClientByName() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                try {
                    List<Client> clients = clientDAO.findByNameAndSurname(name, surname);
                    if (!clients.isEmpty()) {
                        JTextArea textArea = new JTextArea(20, 40);
                        textArea.setEditable(false);
                        StringBuilder message = new StringBuilder("Found clients:\n");
                        for (Client client : clients) {
                            message.append("ID: ").append(client.getId()).append("\n")
                                    .append("Name: ").append(client.getName()).append("\n")
                                    .append("Surname: ").append(client.getSurname()).append("\n")
                                    .append("Date of Birth: ").append(client.getDateOfBirth()).append("\n")
                                    .append("Phone: ").append(client.getPhone()).append("\n")
                                    .append("Email: ").append(client.getEmail()).append("\n");
                        }
                        textArea.setText(message.toString());
                        textArea.setCaretPosition(0);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(null, scrollPane, "Found clients", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Client is not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FindClientByName.this, "Failed to find a client", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public JPanel getFindByNamePanel() {
        return findByNamePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindClientByName();
            }
        });
    }
}
