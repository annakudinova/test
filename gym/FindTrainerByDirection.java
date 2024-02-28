package gym.view2.trainers;

import gym.controller.storage.impl.db.TrainerDAOImpl;
import gym.model.Trainer;
import gym.util.GymException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class FindTrainerByDirection extends JFrame {
    private JTextField directionField;
    private JPanel findByDirectionPanel;
    private JButton findButton;
    private JButton cancelButton;
    private TrainerDAOImpl trainerDAO;

    public FindTrainerByDirection() {
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle("Find by direction");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            trainerDAO = new TrainerDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        setContentPane(findByDirectionPanel);
//        setMinimumSize(new Dimension(600, 600));
//        setLocationRelativeTo(null);
//        setVisible(true);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String direction = directionField.getText();
                try {

                    List<Trainer> trainers = trainerDAO.findByDirection(direction);
                    if (!trainers.isEmpty()) {
                        JTextArea textArea = new JTextArea(20, 40);
                        textArea.setEditable(false);
                        StringBuilder message = new StringBuilder("Found trainers:\n");
                        for (Trainer trainer : trainers) {
                            message.append("ID: ").append(trainer.getId()).append("\n")
                                    .append("Name: ").append(trainer.getName()).append("\n")
                                    .append("Surname: ").append(trainer.getSurname()).append("\n")
                                    .append("Date of Birth: ").append(trainer.getDateOfBirth()).append("\n")
                                    .append("Description: ").append(trainer.getDescription()).append("\n")
                                    .append("Category: ").append(trainer.getCategory().getId()).append("\n")
                                    .append("Direction: ").append(trainer.getDirection()).append("\n")
                                    .append("Cost: ").append(trainer.getCost()).append("\n")
                                    .append("Phone: ").append(trainer.getPhone()).append("\n\n");
                        }
                        textArea.setText(message.toString());
                        textArea.setCaretPosition(0);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(null, scrollPane, "Found trainers", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Trainer is not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FindTrainerByDirection.this, "Failed to find a trainer", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }


        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
    public JPanel getFindByDirectionPanel(){
        return findByDirectionPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindTrainerByDirection();
            }
        });

    }
}
