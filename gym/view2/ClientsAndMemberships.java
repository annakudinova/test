package gym.view2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ClientsAndMemberships extends JFrame {
    private JTable table;

    public ClientsAndMemberships() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Clients and Memberships");
        setSize(800, 600);
        setLocationRelativeTo(null);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Client id");
        model.addColumn("Membership id");
        model.addColumn("Cost");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients_memberships");
            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getLong("client_id"),
                        resultSet.getLong("membership_id"), resultSet.getInt("cost")});
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
        JButton deleteButton = new JButton("Delete");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddClientsMemberships addClientsMemberships = new AddClientsMemberships(ClientsAndMemberships.this);
                addClientsMemberships.setVisible(true);
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
                new ClientsAndMemberships().setVisible(true);
            }
        });
    }
}

