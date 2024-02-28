package gym.view2.memberships;

import gym.controller.storage.api.MembershipsDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.model.Membership;

import gym.util.GymException;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MembershipWindow extends JFrame {
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JPanel membershipPanel;
    private JTable membershipsTable;
    private JComboBox find;
    private MembershipsDAO membershipsDAO;


    public MembershipWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Memberships Window");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            membershipsDAO = new MembershipDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(membershipPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);


    DefaultTableModel model = new DefaultTableModel(0, 6);
    model.setColumnIdentifiers(new Object[]{"id", "title", "description", "number", "cost", "duration"});

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1")) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM memberships")) {
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getLong("id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getInt("number"),
                            resultSet.getInt("cost"),
                            resultSet.getInt("duration"),

                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        membershipsTable.setModel(model);
        addButton.addActionListener(e -> openAddMembershipWindow(membershipsTable));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"Search", "by title"});
        find.setModel(comboBoxModel);

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                String selectedItem = (String) find.getSelectedItem();
                if ("Search".equals(selectedItem)) {
                    return;
                } else if ("by title".equals(selectedItem)) {
                    FindMembershipByTitle findMembershipByTitle = new FindMembershipByTitle();
                    frame.setTitle("Find Membership By Title");
                    frame.setContentPane(findMembershipByTitle.getFindByTitlePanel());
                }
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(new Dimension(600, 600));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = membershipsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MembershipWindow.this, "Select a row to edit");
                } else {
                    Long memberShipId = (Long) membershipsTable.getValueAt(selectedRow, 0);
                    try {
                        Membership membership = membershipsDAO.find(memberShipId);
                        EditMembership editMembership = new EditMembership(membership, MembershipWindow.this);
                        editMembership.setVisible(true);
                    } catch (GymException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(MembershipWindow.this, "Error editing a membership", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = membershipsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(MembershipWindow.this, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    DefaultTableModel model = (DefaultTableModel) membershipsTable.getModel();
                    long id = (long) model.getValueAt(selectedRow, 0);
                    try {
                        Membership membershipToDelete = new Membership();
                        membershipToDelete.setId(id);
                        membershipsDAO.delete(membershipToDelete);
                        model.removeRow(selectedRow);
                    } catch (GymException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(MembershipWindow.this, "Error deleting a membership", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(MembershipWindow.this, "Unknown Error", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void openAddMembershipWindow(JTable membershipsTable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               MembershipWindow.this.dispose();
                AddMembership addMembership = new AddMembership(MembershipWindow.this);

               membershipPanel.setVisible(true);
            }
        });
    }


    public JTable getMembershipsTable() {
        return membershipsTable;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MembershipWindow();
            }
        });
    }

}
