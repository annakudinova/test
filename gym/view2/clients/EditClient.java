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

public class EditClient extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField date_of_birth;
    private JTextField phoneField;
    private JTextField EmailField1;
    private JTextField photoField;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel editPanel;
    private Client client;
    private ClientDAOImpl clientDAO;
    private ClientWindow clientWindow;

    public EditClient(Client client, ClientWindow clientWindow) {
        this.client = client;
        this.clientWindow = clientWindow;
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit a client");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(editPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        nameField.setText(client.getName());
        surnameField.setText(client.getSurname());
        date_of_birth.setText(client.getDateOfBirth().toString());
        phoneField.setText(client.getPhone());
        EmailField1.setText(client.getEmail());

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String dateOfBirthString = date_of_birth.getText();
                if (dateOfBirthString.isEmpty()) {
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = null;
                    try {
                        date = sdf.parse(dateOfBirthString);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                String phone = phoneField.getText();
                String email = EmailField1.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = sdf.parse(dateOfBirthString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                Client updatedClient = client;
                updatedClient.setId(client.getId());
                updatedClient.setName(name);
                updatedClient.setSurname(surname);
                updatedClient.setDateOfBirth(new java.sql.Date(date.getTime()));
                updatedClient.setPhone(phone);
                updatedClient.setEmail(email);
                try {
                    updatedClient = clientDAO.update(updatedClient);
                    DefaultTableModel model = (DefaultTableModel) clientWindow.getClientsTable().getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if ((Long) model.getValueAt(i, 0) == client.getId()) {
                            model.setValueAt(updatedClient.getName(), i, 1);
                            model.setValueAt(updatedClient.getSurname(), i, 2);
                            model.setValueAt(updatedClient.getDateOfBirth(), i, 3);
                            model.setValueAt(updatedClient.getPhone(), i, 4);
                            model.setValueAt(updatedClient.getEmail(), i, 5);
                            break;
                        }
                    }
                    dispose();
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditClient.this, "Error editing a client", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditClient editClient = new EditClient(new Client(), null);
                editClient.setVisible(true);
            }
        });

    }
}

