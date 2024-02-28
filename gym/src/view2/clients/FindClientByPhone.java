package gym.view2.clients;

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
import java.util.List;

public class FindClientByPhone extends JFrame {
    private JTextField phoneField;
    private JPanel findByPhonePanel;
    private JButton OKButton;
    private JButton cancelButton;
    private ClientDAOImpl clientDAO;

    public FindClientByPhone() {
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle("Find by phone");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        setContentPane(findByPhonePanel);
//        setMinimumSize(new Dimension(500, 500));
//        setLocationRelativeTo(null);
//
//        setVisible(true);


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                try {
                    Client client = clientDAO.findByPhone(phone);
                    if (client != null) {
                        JTextArea textArea = new JTextArea(20, 40);
                        textArea.setEditable(false);
                        StringBuilder message = new StringBuilder("Found clients:\n");
                        message.append("ID: ").append(client.getId()).append("\n")
                                .append("Name: ").append(client.getName()).append("\n")
                                .append("Surname: ").append(client.getSurname()).append("\n")
                                .append("Date of Birth: ").append(client.getDateOfBirth()).append("\n")
                                .append("Phone: ").append(client.getPhone()).append("\n")
                                .append("Email: ").append(client.getEmail()).append("\n");
                        textArea.setText(message.toString());
                        textArea.setCaretPosition(0);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(null, scrollPane, "Found clients", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Client is not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FindClientByPhone.this, "Failed to find a client", "Error", JOptionPane.ERROR_MESSAGE);
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

    public JPanel getFindByPhonePanel() {
        return findByPhonePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindClientByPhone();
            }
        });
    }
}
