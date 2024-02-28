package gym.view2.clients;

import gym.view2.memberships.MembershipWindow;
import gym.view2.trainers.TrainerWindow;
import gym.view2.visits.VisitWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {

    private JButton clientsButton;
    private JButton trainersButton1;
    private JButton visitsButton;
    private JButton membershipsButton;
    private JPanel mainMenuPanel;

    public MainMenu(){

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main");
        setContentPane(mainMenuPanel);

       // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 500));
        setLocationRelativeTo(null);
        setVisible(true);


        visitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVisitWindow();
            }
        });
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientWindow();

            }
        });
        trainersButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTrainerWindow();
            }
        });
        membershipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMembershipWindow();
            }
        });
    }


    private void openTrainerWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TrainerWindow trainerWindow = new TrainerWindow(null);
                trainerWindow.setVisible(true);
            }
        });
    }

    private void openVisitWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VisitWindow visitWindow = new VisitWindow();
                visitWindow.setVisible(true);
            }
        });
    }

    private void openMembershipWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MembershipWindow membershipWindow = new MembershipWindow();
                membershipWindow.setVisible(true);
            }
        });
    }

    private void openClientWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientWindow clientWindow = new ClientWindow();
                clientWindow.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
    }
}


