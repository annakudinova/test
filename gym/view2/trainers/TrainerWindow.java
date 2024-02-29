
package gym.view2.trainers;

import gym.controller.storage.api.MembershipsDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.controller.storage.impl.db.TrainerDAOImpl;
import gym.model.Category;
import gym.model.Membership;
import gym.model.Trainer;
import gym.util.GymException;
import gym.view2.memberships.AddMembership;
import gym.view2.memberships.EditMembership;
import gym.view2.memberships.FindMembershipByTitle;
import gym.view2.memberships.MembershipWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TrainerWindow extends JFrame {
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JComboBox<String> find;
    private JTable trainersTable;
    private JPanel trainersPanel;
    private TrainerDAOImpl trainerDAO;
    private Category category;

    public TrainerWindow(Category category) {
        this.category = category;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Memberships Window");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            trainerDAO = new TrainerDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        trainersPanel = new JPanel(new BorderLayout());
        setContentPane(trainersPanel);
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        find = new JComboBox<>(new String[]{"Search", "by name and surname", "by category", "by direction"});
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(find);
        trainersPanel.add(buttonPanel, BorderLayout.NORTH);
        trainersTable = new JTable(new DefaultTableModel(new Object[]{"Trainer id", "Name", "Surname", "Date of birth", "Description", "Category", "Direction", "Cost", "Phone"}, 0));
        JTableHeader header = trainersTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        DefaultTableModel model = (DefaultTableModel) trainersTable.getModel();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1")) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM trainers")) {
                while (resultSet.next()) {
                    model.addRow(new Object[]{
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getDate("date_of_birth"),
                            resultSet.getString("description"),
                            resultSet.getLong("category"),
                            // category = new Category(resultSet.getLong("category")),
                            resultSet.getString("direction"),
                            resultSet.getInt("cost"),
                            resultSet.getString("phone"),
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        trainersPanel.add(new JScrollPane(trainersTable), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        addButton.addActionListener(e -> openAddTrainerWindow());
        editButton.addActionListener(e -> editTrainer());
        deleteButton.addActionListener(e -> deleteTrainer());
        find.addActionListener(e -> findAction());
        setVisible(true);
    }

    private void openAddTrainerWindow() {
        SwingUtilities.invokeLater(() -> {
            AddTrainer addTrainer = new AddTrainer(TrainerWindow.this);
            addTrainer.setVisible(true);
        });
    }

    private void editTrainer() {
        int selectedRow = trainersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(TrainerWindow.this, "Select a row to edit", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Long memberShipId = (Long) trainersTable.getValueAt(selectedRow, 0);
            try {
                Trainer trainer = trainerDAO.find(memberShipId);
                EditTrainer editTrainer = new EditTrainer(trainer, TrainerWindow.this);
                editTrainer.setVisible(true);
            } catch (GymException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(TrainerWindow.this, "Error editing a trainer", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteTrainer() {
        int selectedRow = trainersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(TrainerWindow.this, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            DefaultTableModel model = (DefaultTableModel) trainersTable.getModel();
            long id = (long) model.getValueAt(selectedRow, 0);
            try {
                Trainer trainerToDelete = new Trainer();
                trainerToDelete.setId(id);
                trainerDAO.delete(trainerToDelete);
                model.removeRow(selectedRow);
            } catch (GymException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(TrainerWindow.this, "Error deleting a trainer", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(TrainerWindow.this, "Unknown Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void findAction() {
        JFrame frame = new JFrame();
        String selectedItem = (String) find.getSelectedItem();
        if ("Search".equals(selectedItem)) {
            return;
        } else if ("by name and surname".equals(selectedItem)) {
            FindTrainerByName findByName = new FindTrainerByName();
            frame.setTitle("Find Trainer By Name and Surname");
            frame.setContentPane(findByName.getFindByNamePanel());
        } else if ("by category".equals(selectedItem)) {
            FindTrainerByCategory findByCategory = new FindTrainerByCategory();
            frame.setTitle("Find Trainer By Category");
            frame.setContentPane(findByCategory.getFindByCategoryPanel());
        } else if ("by direction".equals(selectedItem)) {
            FindTrainerByDirection findByDirection = new FindTrainerByDirection();
            frame.setTitle("Find Trainer By Direction");
            frame.setContentPane(findByDirection.getFindByDirectionPanel());
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(600, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JTable getTrainersTable() {
        return trainersTable;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrainerWindow(null);
            }
        });
    }

}



