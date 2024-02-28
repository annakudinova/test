package gym.view;

import gym.view2.clients.ClientWindow;
import gym.view2.clients.Login;
import gym.view2.memberships.MembershipWindow;
import gym.view2.trainers.TrainerWindow;
import gym.view2.visits.VisitWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {
    public MainWindow() throws IOException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JMenuBar menuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Menu");
        JMenuItem clients = new JMenuItem("Clients");
        JMenuItem trainers = new JMenuItem("Trainers");
        JMenuItem memberships = new JMenuItem("Memberships");
        JMenuItem visits = new JMenuItem("Visits");
        jMenu.add(clients);
        jMenu.add(trainers);
        jMenu.add(memberships);
        jMenu.add(visits);
        menuBar.add(jMenu);

        setJMenuBar(menuBar);
        JToolBar jToolBar = new JToolBar();
        JButton clientsButton = new JButton("Clients");
        JButton trainersButton = new JButton("Trainers");
        JButton membershipsButton = new JButton("Memberships");
        JButton visitsButton = new JButton("Visits");
        jToolBar.add(clientsButton);
        jToolBar.add(Box.createHorizontalStrut(10));
        jToolBar.add(trainersButton);
        jToolBar.add(Box.createHorizontalStrut(10));
        jToolBar.add(membershipsButton);
        jToolBar.add(Box.createHorizontalStrut(10));
        jToolBar.add(visitsButton);
        jToolBar.add(Box.createHorizontalStrut(10));
        add(jToolBar, BorderLayout.NORTH);
        JDesktopPane jdpDesktop = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                ImageIcon icon = new ImageIcon("gym5.jpg");
                grphcs.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (ClientsWindow.getAmount() == 0) {
//                    ClientsWindow clientsWindow = new ClientsWindow();
//                    jdpDesktop.add(clientsWindow);
//                    clientsWindow.setVisible(true);
                ClientWindow clientWindow = new ClientWindow();
                clientWindow.setVisible(true);
            }
        });
     trainersButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             TrainerWindow trainerWindow = new TrainerWindow(null);
             trainerWindow.setVisible(true);
         }
     });
     membershipsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             MembershipWindow membershipWindow = new MembershipWindow();
             membershipWindow.setVisible(true);
         }
     });
     visitsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             VisitWindow visitWindow = new VisitWindow();
             visitWindow.setVisible(true);
         }
     });
        add(jdpDesktop, BorderLayout.CENTER);
        setVisible(true);
        Login login = new Login(null);
    }
    public static void main(String[] args) throws IOException {
        MainWindow mainWindow = new MainWindow();
    }
}
