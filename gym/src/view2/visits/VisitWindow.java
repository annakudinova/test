package gym.view2.visits;

import gym.controller.storage.api.VisitDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.controller.storage.impl.db.VisitDAOImpl;
import gym.view2.memberships.AddMembership;
import gym.view2.memberships.MembershipWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

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
        setContentPane(visitPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        //setLocationRelativeTo(null);

        setVisible(true);

        DefaultTableModel model = new DefaultTableModel(0, 4);
        //  model.setColumnIdentifiers(new Object[]{"id", "title", "description", "number", "cost", "duration"});

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
        visitsTable.setModel(model);
        addVisitButton.addActionListener(e -> openAddVisitWindow(visitsTable));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

        private void openAddVisitWindow(JTable visitsTable) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
//                    VisitWindow.this.dispose();
//                   AddVisit addVisit = new AddVisit(VisitWindow.this);
//                   visitPanel.setVisible(true);
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

