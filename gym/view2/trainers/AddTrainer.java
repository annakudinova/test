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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddTrainer extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField dateOfBirthField;
    private JTextField descriptionField;
    private JTextField categoryField;
    private JTextField directionField;
    private JTextField costField;
    private JTextField phoneField;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel addPanel;
    private TrainerDAOImpl trainerDAO;
    private TrainerWindow trainer;


    public AddTrainer(TrainerWindow trainer) {
        this.trainer = trainer;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Trainer");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            trainerDAO = new TrainerDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(addPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String dateOfBirthString = dateOfBirthField.getText();
                String description = descriptionField.getText();
                String categoryIdString = categoryField.getText();
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
                Trainer newTrainer = new Trainer();
                newTrainer.setName(name);
                newTrainer.setSurname(surname);
                newTrainer.setDateOfBirth(new java.sql.Date(date.getTime()));
                newTrainer.setDescription(description);
                Long categoryId = null;
                try {
                    categoryId = Long.parseLong(categoryIdString);
                } catch (NumberFormatException exception) {

                }
                newTrainer.setCategory(new Category(categoryId));
                newTrainer.setDirection(direction);
                newTrainer.setCost(Integer.parseInt(cost));
                newTrainer.setPhone(phone);
                try {
                    newTrainer = trainerDAO.save(newTrainer);

                    DefaultTableModel model = (DefaultTableModel) trainer.getTrainersTable().getModel();
                    model.addRow(new Object[]{newTrainer.getId(), newTrainer.getName(), newTrainer.getSurname(),
                            newTrainer.getDateOfBirth(), newTrainer.getDescription(), newTrainer.getCategory().getId(),
                            newTrainer.getDirection(), newTrainer.getCost(), newTrainer.getPhone()});
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddTrainer.this, "Error adding a trainer", "Error", JOptionPane.ERROR_MESSAGE);
                }

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {

        AddTrainer addTrainer = new AddTrainer(null);
    }


}
