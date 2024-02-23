package gym.view2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    private JButton clientsButton;
    private JButton statisticsButton;
    private JButton trainersButton1;
    private JButton visitsButton;
    private JButton membershipsButton;
    private JPanel mainMenuPanel;

    public MainMenu(){
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Main");
    setContentPane(mainMenuPanel);
    setMinimumSize(new Dimension(700, 700));
    setLocationRelativeTo(null);
    setVisible(true);


        clientsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openClientWindow();
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


