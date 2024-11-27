import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard {
    private JPanel dashboard;
    private JButton studentButton;
    private JButton lecturesButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Dashboard");
        frame.setContentPane(new Dashboard().dashboard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMain() {
        return dashboard;
    }

    public Dashboard() {
        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(dashboard);
                dashboardFrame.dispose();

                JFrame studentFrame = new JFrame("Student");
                studentFrame.setContentPane(new Student().getMain());
                studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                studentFrame.pack();
                studentFrame.setVisible(true);

            }
        });
        lecturesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame dashboardFrame = (JFrame) SwingUtilities.getWindowAncestor(dashboard);
                dashboardFrame.dispose();

                JFrame studentFrame = new JFrame("Lectures");
                studentFrame.setContentPane(new Lectures().getMain());
                studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                studentFrame.pack();
                studentFrame.setVisible(true);

            }
        });
    }
}
