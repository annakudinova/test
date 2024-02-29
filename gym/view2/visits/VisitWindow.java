package gym.view2.visits;

import gym.controller.storage.api.VisitDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.controller.storage.impl.db.VisitDAOImpl;
import gym.view2.memberships.AddMembership;
import gym.view2.memberships.MembershipWindow;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class VisitWindow extends JFrame {
    private JButton addVisitButton;
    private JTable visitsTable;
    private JPanel visitPanel;
    private VisitDAO visitDAO;

    public VisitWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visit Window");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            visitDAO = new VisitDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setMinimumSize(new Dimension(800, 600));
        JTable visitsTable = new JTable(new DefaultTableModel(new Object[]{"Visit id", "Client id", "Date", "Locker number"}, 0));
        JTableHeader header = visitsTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        DefaultTableModel model = (DefaultTableModel) visitsTable.getModel();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1")) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM visits")) {
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getLong("id"),
                            resultSet.getLong("client_id"),
                            resultSet.getDate("date"),
                            resultSet.getInt("locker_number")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        visitPanel = new JPanel(new BorderLayout());
        visitPanel.add(new JScrollPane(visitsTable), BorderLayout.CENTER);
        addVisitButton = new JButton("Add");
        addVisitButton.setMaximumSize(new Dimension(70, addVisitButton.getMaximumSize().height));
        visitPanel.add(addVisitButton, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addVisitButton);
        visitPanel.add(buttonPanel, BorderLayout.NORTH);
        setContentPane(visitPanel);
        pack();
        setLocationRelativeTo(null);
        addVisitButton.addActionListener(e -> openAddVisitWindow(visitsTable));
        setVisible(true);
    }

    private void openAddVisitWindow(JTable visitsTable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AddVisit addVisit = new AddVisit(VisitWindow.this);
                addVisit.setVisible(true);
            }
        });
    }

    public JTable getVisitsTable() {
        return visitsTable;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VisitWindow();
            }
        });
    }
}

