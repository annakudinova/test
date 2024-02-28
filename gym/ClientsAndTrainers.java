package gym.view2;

import gym.controller.storage.api.ClientDAO;
import gym.model.Client;
import gym.model.Trainer;
import gym.util.GymException;
import gym.view2.clients.ClientsAndMemberships;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ClientsAndTrainers extends JFrame {
    private JTable table;
    private ClientDAO clientDAO;
    public ClientsAndTrainers(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Clients and Trainers");
        setSize(800, 600);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("client");
        model.addColumn("trainer");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients_trainers");
            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getLong("client_id"),
                        resultSet.getLong("trainer_id")});
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = table.getSelectedRow();
//                if (selectedRow == -1) {
//                    JOptionPane.showMessageDialog(ClientsAndTrainers.this, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//                long clientId = (long) model.getValueAt(selectedRow, 0);
//                long trainerId = (long) model.getValueAt(selectedRow, 1);
//
//                try {
//                    boolean success = clientDAO.deleteTrainer(new Client(clientId), new Trainer(trainerId));
//                    if (success) {
//                        model.removeRow(selectedRow);
//                        JOptionPane.showMessageDialog(ClientsAndTrainers.this, "Record deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//                    } else {
//                        JOptionPane.showMessageDialog(ClientsAndTrainers.this, "Failed to delete record", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } catch (GymException ex) {
//                    JOptionPane.showMessageDialog(ClientsAndTrainers.this, "Error deleting record", "Error", JOptionPane.ERROR_MESSAGE);
//                    ex.printStackTrace();
//                }
//            }
//        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               AddClientsTrainers addClientsTrainers = new AddClientsTrainers(ClientsAndTrainers.this);
                addClientsTrainers.setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
       // buttonPanel.add(deleteButton);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(panel);
    }
    public JTable getTable(){
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientsAndTrainers().setVisible(true);
            }
        });
    }
}
