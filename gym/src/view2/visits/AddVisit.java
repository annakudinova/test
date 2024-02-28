




package gym.view2.visits;

        import gym.controller.storage.api.VisitDAO;
        import gym.controller.storage.impl.db.VisitDAOImpl;
        import gym.model.Client;
        import gym.model.Visit;
        import gym.util.GymException;
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

public class AddVisit extends JFrame {
    private JTextField idField;
    private JTextField dateField;
    private JTextField lockerNumberField;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel addVisitPanel;

    private VisitDAO visitDAO;
    private VisitWindow visitWindow;

    public AddVisit(VisitWindow visitWindow) {
        this.visitWindow = visitWindow;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Visit");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "Apassword@sql1");
            visitDAO = new VisitDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentPane(addVisitPanel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String dateVisit = dateField.getText();
                String lockerNumber = lockerNumberField.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = sdf.parse(dateVisit);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                Visit newVisit = new Visit();
                long clientId2 = Long.parseLong(id);
                Client client = new Client(clientId2);
                newVisit.setClient(client);
                newVisit.setDate(new java.sql.Date(date.getTime()));
                int lockerNumberInt = Integer.parseInt(lockerNumber);
                newVisit.setLockerNumber(lockerNumberInt);

                try {
                    newVisit = visitDAO.save(newVisit);

                    DefaultTableModel model = (DefaultTableModel) visitWindow.getVisitsTable().getModel();
                    model.addRow(new Object[]{newVisit.getClient(), newVisit.getDate(), newVisit.getLockerNumber()});
                } catch (GymException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddVisit.this, "Error adding a visit", "Error", JOptionPane.ERROR_MESSAGE);
                }

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        AddVisit addVisit = new AddVisit(null);
    }
}
