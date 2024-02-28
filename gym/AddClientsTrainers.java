package gym.view2;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.model.Membership;
import gym.model.Trainer;
import gym.util.GymException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddClientsTrainers extends JFrame {
    private JTextField clientIdField;
    private JTextField trainerIdField;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel addClientsTrainersPanel;
    private ClientDAOImpl clientDAO;
    private ClientsAndTrainers clientsAndTrainers;

    public AddClientsTrainers(ClientsAndTrainers clientsAndTrainers) {
        this.clientsAndTrainers = clientsAndTrainers;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add client and trainer");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(addClientsTrainersPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Long clientId = Long.parseLong(clientIdField.getText());
                    Long trainerId = Long.parseLong(trainerIdField.getText());


                    Client client = new Client(clientId);
                    Trainer trainer = new Trainer(trainerId);

                    boolean success = clientDAO.addTrainer(client, trainer);

                    if (success) {
                        DefaultTableModel model = (DefaultTableModel) clientsAndTrainers.getTable().getModel();
                        model.addRow(new Object[]{clientId, trainerId});
                        JOptionPane.showMessageDialog(AddClientsTrainers.this, "Record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddClientsTrainers.this, "Failed to add record", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddClientsTrainers.this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GymException ex) {
                    JOptionPane.showMessageDialog(AddClientsTrainers.this, "Error adding a new record", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

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
