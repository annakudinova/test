package gym.view2.memberships;

import gym.controller.storage.api.MembershipsDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.controller.storage.impl.db.TrainerDAOImpl;
import gym.model.Category;
import gym.model.Membership;
import gym.model.Trainer;
import gym.util.GymException;
import gym.view2.trainers.AddTrainer;
import gym.view2.trainers.TrainerWindow;

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

public class AddMembership extends JFrame {
    private JTextField titleField;
    private JPanel addPanel;
    private JTextField descriptionField;
    private JTextField numberField;
    private JTextField costField;
    private JTextField durationField;
    private JButton saveButton;
    private JButton cancelButton;
    private MembershipWindow membershipWindow;
    private MembershipsDAO membershipsDAO;

    public AddMembership(MembershipWindow membershipWindow) {
        this.membershipWindow = membershipWindow;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Membership");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            membershipsDAO = new MembershipDAOImpl(connection);
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
                String title = titleField.getText();
                String description = descriptionField.getText();
                String number = numberField.getText();
                String cost = descriptionField.getText();
                String duration = durationField.getText();

                Membership newMembership = new Membership();
                newMembership.setTitle(title);
                newMembership.setDescription(description);
                newMembership.setNumber(Integer.parseInt(numberField.getText()));
                newMembership.setCost(Integer.parseInt(costField.getText()));
                newMembership.setCost(Integer.parseInt(durationField.getText()));


                try {
                    newMembership = membershipsDAO.save(newMembership);

                    DefaultTableModel model = (DefaultTableModel) membershipWindow.getMembershipsTable().getModel();
                    model.addRow(new Object[]{newMembership.getTitle(), newMembership.getDescription(),
                            newMembership.getNumber(), newMembership.getCost(), newMembership.getDuration()});
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddMembership.this, "Error adding a membership", "Error", JOptionPane.ERROR_MESSAGE);
                }

                dispose();
            }

        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMembership addMembership = new AddMembership(null);
            }
        });
    }
}
