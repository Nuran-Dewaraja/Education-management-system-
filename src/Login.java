import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel login;
    private JButton loginBtn;
    private JTextField emailTxt;
    private JPasswordField password;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Login() {
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredEmail = emailTxt.getText();
                char[] enteredPassword = password.getPassword();

                // Here you can add the logic to validate the entered credentials
                if (isValidLogin(enteredEmail, enteredPassword)) {
                   // JOptionPane.showMessageDialog(null, "Login successful!");

                    JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(login);
                    loginFrame.dispose();

                    // Open the Student window
                    JFrame dashboardFrame = new JFrame("Dashboard");
                    dashboardFrame.setContentPane(new Dashboard().getMain());
                    dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    dashboardFrame.pack();
                    dashboardFrame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials. Please try again.");
                }

                // Clear the text fields after the login attempt
                emailTxt.setText("");
                password.setText("");
            }
        });
    }

    private boolean isValidLogin(String email, char[] password) {
        // Add your validation logic here, e.g., checking against a stored username and password
        // For demonstration purposes, let's consider a simple example
        return email.equals("123") && new String(password).equals("123");
    }

}
