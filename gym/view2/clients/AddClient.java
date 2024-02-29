package gym.view2.clients;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.util.GymException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddClient extends JFrame {
    private ClientDAOImpl clientDAO;
    private JTextField nameField;
    private JLabel name;
    private JTextField surnameField;
    private JTextField dateField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField photoField;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel addClient;
    private ClientWindow client;

    public AddClient(ClientWindow client) {
        this.client = client;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Client");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(addClient);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String dateOfBirthString = dateField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = sdf.parse(dateOfBirthString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                Client newClient = new Client();
                newClient.setName(name);
                newClient.setSurname(surname);
                newClient.setDateOfBirth(new java.sql.Date(date.getTime()));
                newClient.setPhone(phone);
                newClient.setEmail(email);
                try {
                    newClient = clientDAO.save(newClient);
                    DefaultTableModel model = (DefaultTableModel) client.getClientsTable().getModel();
                    model.addRow(new Object[]{newClient.getId(), newClient.getName(), newClient.getSurname(),
                            newClient.getDateOfBirth(), newClient.getPhone(), newClient.getEmail()});
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddClient.this, "Error adding client", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
