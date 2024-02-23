package gym.view2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClient extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField date_of_birth;
    private JTextField phoneField;
    private JTextField EmailField1;
    private JTextField photoField;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel editPanel;

    public EditClient() {

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Enter the system");
        setContentPane(editPanel);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditClient editClient = new EditClient();
                editClient.setVisible(true);
            }
        });

}
}
