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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class EditMembership extends JFrame {
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField numberField;
    private JTextField costField;
    private JTextField durationField;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel editPanel;
    private MembershipsDAO membershipsDAO;
    private Membership membership;
    private MembershipWindow membershipWindow;

    public EditMembership(Membership membership, MembershipWindow membershipWindow) {
        this.membership = membership;
        this.membershipWindow = membershipWindow;
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit a membership");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            membershipsDAO = new MembershipDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(editPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        titleField.setText(membership.getTitle());
        descriptionField.setText(membership.getDescription());
//        int numberString = Integer.parseInt(numberField.getText());
//        int costString = Integer.parseInt(costField.getText());
//        int durationString = Integer.parseInt(durationField.getText());
        numberField.setText(String.valueOf(membership.getNumber())); // Преобразование числового значения в строку
        costField.setText(String.valueOf(membership.getCost())); // Преобразование числового значения в строку
        durationField.setText(String.valueOf(membership.getDuration()));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = durationField.getText();
                int number = Integer.parseInt(numberField.getText()); // Преобразование текста в целое число
                int cost = Integer.parseInt(costField.getText()); // Преобразование текста в число с плавающей точкой
                int duration = Integer.parseInt(durationField.getText());


               // Membership updatedMembership = new Membership();
                Membership updatedMembership = membership;
                updatedMembership.setTitle(title);
                updatedMembership.setDescription(description);
                updatedMembership.setNumber(number);
                updatedMembership.setCost(cost);
                updatedMembership.setDuration(duration);
                try {
                    updatedMembership = membershipsDAO.update(updatedMembership);
                    DefaultTableModel model = (DefaultTableModel) membershipWindow.getMembershipsTable().getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if ((Long) model.getValueAt(i, 0) == membership.getId()) {
                            model.setValueAt(updatedMembership.getTitle(), i, 1);
                            model.setValueAt(updatedMembership.getDescription(), i, 2);
                            model.setValueAt(updatedMembership.getNumber(), i, 3);
                            model.setValueAt(updatedMembership.getCost(), i, 4);
                            model.setValueAt(updatedMembership.getDuration(), i, 5);

                            break;
                        }
                    }
                    dispose();
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditMembership.this, "Error editing a membership", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditMembership editMembership = new EditMembership(new Membership(), null);
                editMembership.setVisible(true);
            }
        });
    }

}

