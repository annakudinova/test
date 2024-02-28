package gym.view2.clients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog {
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel loginForm;
    private JPasswordField passwordField1;
    private String password = "gym";

    public Login(JFrame parent) {
        super(parent);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Enter the system");
        setContentPane(loginForm);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(null);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputtedPassword = new String(passwordField1.getPassword());
                if (inputtedPassword.equals(password)) {
                   // openMainMenu();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "Invalid password. Try again!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setVisible(true);
    }

//    private void openMainMenu() {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                //MainMenu mainMenu = new MainMenu();
//               // mainMenu.setVisible(true);
//            }
//        });
//    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                Login loginForm = new Login(null);
//            }
//        });
//    }
}

