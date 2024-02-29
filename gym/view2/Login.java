package gym.view2;

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
}

