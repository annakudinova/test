package gym.view2;

import gym.controller.storage.impl.db.ClientDAOImpl;
import gym.model.Client;
import gym.model.Membership;
import gym.util.GymException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddClientsMemberships extends JFrame {
    private JTextField clientIdField;
    private JTextField membershipIdField;
    private JTextField costField;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel clientsMembershipsPanel;
    private ClientDAOImpl clientDAO;
    private ClientsAndMemberships clientsAndMemberships;

    public AddClientsMemberships(ClientsAndMemberships clientsAndMemberships) {
        this.clientsAndMemberships = clientsAndMemberships;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add client and membership");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            clientDAO = new ClientDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(clientsMembershipsPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Long clientId = Long.parseLong(clientIdField.getText());
                    Long membershipId = Long.parseLong(membershipIdField.getText());
                    int cost = Integer.parseInt(costField.getText());
                    Client client = new Client(clientId);
                    Membership membership = new Membership(membershipId);
                    boolean success = clientDAO.addMembership(client, membership, cost);
                    if (success) {
                        DefaultTableModel model = (DefaultTableModel) clientsAndMemberships.getTable().getModel();
                        model.addRow(new Object[]{clientId, membershipId, cost});
                        JOptionPane.showMessageDialog(AddClientsMemberships.this, "Record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddClientsMemberships.this, "Failed to add record", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddClientsMemberships.this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (GymException ex) {
                    JOptionPane.showMessageDialog(AddClientsMemberships.this, "Error adding a new record", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
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
