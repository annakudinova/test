package gym.view2;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.util.GymException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FindWindow extends JFrame {
    private JTextField phoneField;
    private JPanel findPanel;
    private JButton OKButton;
    private JButton cancelButton;
    private ClientDAOImpl clientDAO;

    public FindWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find by phone");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(findPanel);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        setVisible(true);


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                try {
                    Client client = clientDAO.findByPhone(phone);
                    if (client != null) {
                        JOptionPane.showMessageDialog(FindWindow.this, "Found client:\n" +
                                "ID: " + client.getId() + "\n" +
                                "Name: " + client.getName() + "\n" +
                                "Surname: " + client.getSurname() + "\n" +
                                "Date of Birth: " + client.getDateOfBirth() + "\n" +
                                "Phone: " + client.getPhone() + "\n" +
                                "Email: " + client.getEmail());
                    } else {
                        JOptionPane.showMessageDialog(FindWindow.this, "Client not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FindWindow.this, "Failed to find a client", "Error", JOptionPane.ERROR_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindWindow();
            }
        });
    }
}
