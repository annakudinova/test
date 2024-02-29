package gym.view2.memberships;
import gym.controller.storage.api.MembershipsDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.model.Membership;
import gym.util.GymException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
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

        membershipPanel = new JPanel(new BorderLayout());
        setContentPane(membershipPanel);
        setMinimumSize(new Dimension(800, 600));
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        find = new JComboBox<>(new String[]{"Search", "by title"});
        buttonPanel.add(find);
        membershipPanel.add(buttonPanel, BorderLayout.NORTH);
        membershipsTable = new JTable(new DefaultTableModel(new Object[]{"Membership id", "Title", "Description", "Number", "Cost", "Duration"}, 0));
        JTableHeader header = membershipsTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        DefaultTableModel model = (DefaultTableModel) membershipsTable.getModel();
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
        membershipPanel.add(new JScrollPane(membershipsTable), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        addButton.addActionListener(e -> openAddMembershipWindow());
        editButton.addActionListener(e -> editMembership());
        deleteButton.addActionListener(e -> deleteMembership());
        find.addActionListener(e -> findAction());
        setVisible(true);
    }
    private void openAddMembershipWindow() {
        SwingUtilities.invokeLater(() -> {
            AddMembership addMembership = new AddMembership(MembershipWindow.this);
            addMembership.setVisible(true);
        });
    }

    private void editMembership() {
        int selectedRow = membershipsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(MembershipWindow.this, "Select a row to edit", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void deleteMembership() {
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
                JOptionPane.showMessageDialog(MembershipWindow.this, "Error deleting a membership", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(MembershipWindow.this, "Unknown Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void findAction() {
        String selectedItem = (String) find.getSelectedItem();
        if ("Search".equals(selectedItem)) {
            return;
        } else if ("by title".equals(selectedItem)) {
            FindMembershipByTitle findMembershipByTitle = new FindMembershipByTitle();
            JFrame frame = new JFrame();
            frame.setTitle("Find Membership By Title");
            frame.setContentPane(findMembershipByTitle.getFindByTitlePanel());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(new Dimension(600, 600));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
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
