package gym.view2.memberships;

import gym.controller.storage.api.MembershipsDAO;
import gym.controller.storage.impl.db.MembershipDAOImpl;
import gym.model.Membership;
import gym.util.GymException;
import gym.view2.clients.FindClientByName;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class FindMembershipByTitle extends JFrame {
    private JTextField titleField;
    private JButton findButton;
    private JButton cancelButton;
    private JPanel findByTitlePanel;
    private MembershipsDAO membershipsDAO;

    public FindMembershipByTitle() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            membershipsDAO = new MembershipDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        setContentPane(findByNamePanel);
//        setMinimumSize(new Dimension(600, 600));
//        setLocationRelativeTo(null);
//        setVisible(true);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                try {
                    List<Membership> memberships = membershipsDAO.findByTitle(title);
                    if (!memberships.isEmpty()) {
                        JTextArea textArea = new JTextArea(20, 40);
                        textArea.setEditable(false);
                        StringBuilder message = new StringBuilder("Found memberships:\n");
                        for (Membership membership : memberships) {
                            message.append("ID: ").append(membership.getId()).append("\n")
                                    .append("Title: ").append(membership.getTitle()).append("\n")
                                    .append("Description: ").append(membership.getDescription()).append("\n")
                                    .append("Number: ").append(membership.getNumber()).append("\n")
                                    .append("Cost: ").append(membership.getCost()).append("\n")
                                    .append("Duration: ").append(membership.getDuration()).append("\n");
                        }
                        textArea.setText(message.toString());
                        textArea.setCaretPosition(0);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(null, scrollPane, "Found memberships", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Membership is not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FindMembershipByTitle.this, "Failed to find a membership", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public JPanel getFindByTitlePanel() {
        return findByTitlePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindClientByName();
            }
        });
    }
}
