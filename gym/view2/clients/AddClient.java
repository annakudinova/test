package gym.view2.clients;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.util.GymException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root",
                    "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(addClient);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        nameField.setBorder(new LineBorder(Color.BLACK, 1));
        surnameField.setBorder(new LineBorder(Color.BLACK, 1));
        dateField.setBorder(new LineBorder(Color.BLACK, 1));
        phoneField.setBorder(new LineBorder(Color.BLACK, 1));
        emailField.setBorder(new LineBorder(Color.BLACK, 1));
        nameField.setToolTipText("Name must start with a capital letter");
        surnameField.setToolTipText("Surname must start with a capital letter");
        dateField.setToolTipText("Date format must be yyyy-MM-dd");
        phoneField.setToolTipText("Phone number format must be +380XXXXXXXXX");
        emailField.setToolTipText("Email format must be user@example.com");
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        surnameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateSurname();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateSurname();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        dateField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateDate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        phoneField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validatePhone();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    private void addClient() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String dateOfBirthString = dateField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || surname.isEmpty() || dateOfBirthString.isEmpty()
                || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(AddClient.this,
                    "All fields must be filled in", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validateName() || !validateSurname() || !validateDate() 
            || !validatePhone() || !validateEmail()) {
            JOptionPane.showMessageDialog(AddClient.this,
                    "Please fill in all fields correctly",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client newClient = new Client();
        newClient.setName(name);
        newClient.setSurname(surname);
        newClient.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthString));
        newClient.setPhone(phone);
        newClient.setEmail(email);
        try {
            newClient = clientDAO.save(newClient);
            DefaultTableModel model = (DefaultTableModel) client.getClientsTable().getModel();
            model.addRow(new Object[]{newClient.getId(), newClient.getName(), newClient.getSurname(),
                    newClient.getDateOfBirth(), newClient.getPhone(), newClient.getEmail()});
        } catch (GymException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(AddClient.this, "Error adding a client",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateName() {
        String name = nameField.getText();
        boolean isValid = name.matches("[A-Z][a-z]*");
        if (!isValid) {
            nameField.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            nameField.setBorder(new LineBorder(Color.BLACK, 1));
        }
        return isValid;
    }

    private boolean validateSurname() {
        String surname = surnameField.getText();
        boolean isValid = surname.matches("[A-Z][a-z]*");
        if (!isValid) {
            surnameField.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            surnameField.setBorder(new LineBorder(Color.BLACK, 1));
        }
        return isValid;
    }

    private boolean validateDate() {
        String dateString = dateField.getText();
        boolean isValid = dateString.matches("\\d{4}-\\d{2}-\\d{2}");
        if (!isValid) {
            dateField.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            dateField.setBorder(new LineBorder(Color.BLACK, 1));
        }
        return isValid;
    }

    private boolean validatePhone() {
        String phone = phoneField.getText();
        boolean isValid = phone.matches("\\+380\\d{9}");
        if (!isValid) {
            phoneField.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            phoneField.setBorder(new LineBorder(Color.BLACK, 1));
        }
        return isValid;
    }

    private boolean validateEmail() {
        String email = emailField.getText();
        boolean isValid = email.matches("^[a-zA-Z0-9_.+-]+@(gmail\\.com)$");
        if (!isValid) {
            emailField.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            emailField.setBorder(new LineBorder(Color.BLACK, 1));
        }
        return isValid;
    }
}



