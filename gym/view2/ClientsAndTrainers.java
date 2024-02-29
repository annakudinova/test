package gym.view2;

import gym.controller.storage.api.ClientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ClientsAndTrainers extends JFrame {
    private JTable table;
    private ClientDAO clientDAO;

    public ClientsAndTrainers() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Clients and Trainers");
        setSize(800, 600);
        setLocationRelativeTo(null);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Client id");
        model.addColumn("Trainer id");
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
        JTableHeader header = table.getTableHeader();
        Font headerFont = header.getFont();
        header.setFont(headerFont.deriveFont(Font.BOLD));
        JScrollPane scrollPane = new JScrollPane(table);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddClientsTrainers addClientsTrainers = new AddClientsTrainers(ClientsAndTrainers.this);
                addClientsTrainers.setVisible(true);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    public JTable getTable() {
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
