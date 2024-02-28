package gym.view2.trainers;

import gym.controller.storage.impl.db.TrainerDAOImpl;
import gym.model.Category;
import gym.model.Trainer;
import gym.util.GymException;
import gym.view2.clients.EditClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditTrainer extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField date_of_birthField;
    private JTextField descriptionField;
    private JTextField categoryField;
    private JTextField directionField;
    private JTextField costField;
    private JTextField phoneField;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel trainerPanel;
    private TrainerDAOImpl trainerDAO;
    private Trainer trainer;
    private TrainerWindow trainerWindow;

    public EditTrainer(Trainer trainer, TrainerWindow trainerWindow) {
        this.trainer = trainer;
        this.trainerWindow = trainerWindow;
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit a trainer");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            trainerDAO = new TrainerDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(trainerPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        nameField.setText(trainer.getName());
        surnameField.setText(trainer.getSurname());
        date_of_birthField.setText(trainer.getDateOfBirth().toString());
        descriptionField.setText(trainer.getDescription());
        categoryField.setText(String.valueOf(trainer.getCategory().getId()));
        directionField.setText(trainer.getDirection());
        costField.setText(Integer.toString(trainer.getCost()));
        phoneField.setText(trainer.getPhone());

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String dateOfBirthString = date_of_birthField.getText();
                if (dateOfBirthString.isEmpty()) {
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = null;
                    try {
                        date = sdf.parse(dateOfBirthString);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                String description = descriptionField.getText();
//                String categoryId = categoryField.getText();
                Long categoryId = Long.parseLong(categoryField.getText());

                String direction = directionField.getText();
                String cost = costField.getText();
                String phone = phoneField.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = sdf.parse(dateOfBirthString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                Trainer updatedTrainer = new Trainer();
                updatedTrainer.setId(trainer.getId());
                updatedTrainer.setName(name);
                updatedTrainer.setSurname(surname);
                updatedTrainer.setDateOfBirth(new java.sql.Date(date.getTime()));
                updatedTrainer.setDescription(description);
                Category category = new Category(categoryId);
                updatedTrainer.setCategory(category);
                updatedTrainer.setDirection(direction);
                updatedTrainer.setCost(Integer.parseInt(cost));
                updatedTrainer.setPhone(phone);

                try {
                    updatedTrainer = trainerDAO.update(updatedTrainer);
                    DefaultTableModel model = (DefaultTableModel) trainerWindow.getTrainersTable().getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if ((Long) model.getValueAt(i, 0) == trainer.getId()) {
                            model.setValueAt(updatedTrainer.getName(), i, 1);
                            model.setValueAt(updatedTrainer.getSurname(), i, 2);
                            model.setValueAt(updatedTrainer.getPhone(), i, 3);
                            model.setValueAt(updatedTrainer.getDescription(), i, 4);
                            model.setValueAt(updatedTrainer.getCategory(), i, 5);
                            model.setValueAt(updatedTrainer.getDirection(), i, 6);
                            model.setValueAt(updatedTrainer.getCost(), i, 7);
                            model.setValueAt(updatedTrainer.getPhone(), i, 8);
                            break;
                        }
                    }
                    dispose();
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditTrainer.this, "Error editing a trainer", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }


        public static void main (String[]args){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    EditTrainer editTrainer = new EditTrainer(new Trainer(), null);
                    editTrainer.setVisible(true);
                }
            });
        }
    }
