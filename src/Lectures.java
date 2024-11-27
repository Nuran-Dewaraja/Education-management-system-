import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Lectures {
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtMobileNumber;
    private JButton saveButton;
    private JTextField txtGender;
    private JButton updateButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JTextField txtID;
    private JTextField txtFee;
    private JTextField nameTxt;
    private JTextField ageTxt;
    private JTextField mobileTxt;
    private JTextField genderTxt;
    private JTextField salaryTxt;
    private JTable table1;
    private JTextField searchTxt;
    private JPanel Lecture;
    private JButton backButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lectures");
        frame.setContentPane(new Lectures().Lecture);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    Connection con;
    PreparedStatement pst;

    public JPanel getMain() {
        return Lecture;
    }

    public void connect(){

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbz","root","123");
            System.out.println("success");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    void table_load(){
        try{
            pst = con.prepareStatement("SELECT * FROM dbz.lectures");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Lectures() {
        connect();
        table_load();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String name, age, mobile, gender, salary;

                name = nameTxt.getText();
                age = ageTxt.getText();
                mobile = mobileTxt.getText();
                gender = genderTxt.getText();
                salary = salaryTxt.getText();

                try {
                    pst = con.prepareStatement("insert into lectures(name,age,mobile,gender,salary)values(?,?,?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,age);
                    pst.setString(3,mobile);
                    pst.setString(4,gender);
                    pst.setString(5,salary);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"record added");

                    table_load();

                    nameTxt.setText("");
                    ageTxt.setText("");
                    mobileTxt.setText("");
                    genderTxt.setText("");
                    salaryTxt.setText("");
                    nameTxt.requestFocus();


                } catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    String id = searchTxt.getText();

                    pst = con.prepareStatement("select name,age,mobile,gender,salary from dbz.lectures where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true){
                        String name = rs.getString(1);
                        String age = rs.getString(2);
                        String mobile = rs.getString(3);
                        String gender = rs.getString(4);
                        String salary = rs.getString(5);

                        nameTxt.setText(name);
                        ageTxt.setText(age);
                        mobileTxt.setText(mobile);
                        genderTxt.setText(gender);
                        salaryTxt.setText(salary);

                    }
                    else {
                        nameTxt.setText("");
                        ageTxt.setText("");
                        mobileTxt.setText("");
                        genderTxt.setText("");
                        salaryTxt.setText("");

                        JOptionPane.showMessageDialog(null,"Invalid Lecture ID");
                    }

                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, age, mobile, gender, salary, id;

                name = nameTxt.getText();
                age = ageTxt.getText();
                mobile = mobileTxt.getText();
                gender = genderTxt.getText();
                salary = salaryTxt.getText();
                id = searchTxt.getText();


                try{

                    pst = con.prepareStatement("update dbz.lectures set name = ?, age = ?, mobile = ?, gender = ?, salary = ? where id = ?");
                    pst.setString(1,name);
                    pst.setString(2,age);
                    pst.setString(3,mobile);
                    pst.setString(4,gender);
                    pst.setString(5,salary);
                    pst.setString(6,id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated");
                    table_load();

                    nameTxt.setText("");
                    ageTxt.setText("");
                    mobileTxt.setText("");
                    genderTxt.setText("");
                    salaryTxt.setText("");

                    nameTxt.requestFocus();

                }catch (SQLException e1){
                    e1.printStackTrace();
                }


            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String id;

                id = searchTxt.getText();

                try{

                    pst = con.prepareStatement("delete from dbz.lectures where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();

                    nameTxt.setText("");
                    ageTxt.setText("");
                    mobileTxt.setText("");
                    genderTxt.setText("");
                    salaryTxt.setText("");

                    nameTxt.requestFocus();

                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame lectureFrame = (JFrame) SwingUtilities.getWindowAncestor(Lecture);
                lectureFrame.dispose();

                // Open the Student window
                JFrame dashboardFrame = new JFrame("Dashboard");
                dashboardFrame.setContentPane(new Dashboard().getMain());
                dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboardFrame.pack();
                dashboardFrame.setVisible(true);

            }
        });
    }
}
