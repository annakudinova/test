
package gym.view2.trainers;

import gym.controller.storage.impl.db.TrainerDAOImpl;
import gym.model.Category;
import gym.model.Trainer;
import gym.util.GymException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JScrollBar scrollBar;
    private TrainerDAOImpl trainerDAO;
    private Category category;

    public TrainerWindow(Category category) {

        this.category = category;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trainers Window");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            trainerDAO = new TrainerDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(trainersPanel);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setVisible(true);

        DefaultTableModel model = new DefaultTableModel(0, 9);


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
                            resultSet.getString("phone")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        trainersTable.setModel(model);
        addButton.addActionListener(e -> openAddTrainerWindow(trainersTable));
        pack();
        setVisible(true);


        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"Search", "by name and surname", "by category", "by direction"});
        find.setModel(comboBoxModel);
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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


        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = trainersTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(TrainerWindow.this, "Select a row to edit");
                } else {
                    Long clientId = (Long) trainersTable.getValueAt(selectedRow, 0);
                    try {
                        Trainer trainer = trainerDAO.find(clientId);
                        EditTrainer editTrainer = new EditTrainer(trainer, TrainerWindow.this);
                        editTrainer.setVisible(true);
                    } catch (GymException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(TrainerWindow.this, "Error editing a trainer", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        JOptionPane.showMessageDialog(TrainerWindow.this, "Error deleting a trainer", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(TrainerWindow.this, "Unknown Error", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void openAddTrainerWindow(JTable trainersTable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TrainerWindow.this.dispose();
                AddTrainer addTrainer = new AddTrainer(new TrainerWindow(null));

                addTrainer.setVisible(true);
            }
        });
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
