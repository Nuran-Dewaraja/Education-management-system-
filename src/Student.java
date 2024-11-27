import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.*;

public class Student extends JOptionPane {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtMobileNumber;
    private JTextField txtGender;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtID;
    private JTextField txtFee;
    private JButton backBtn;

    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student");
        frame.setContentPane(new Student().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public JPanel getMain() {
        return Main;
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
            pst = con.prepareStatement("SELECT * FROM dbz.studentz");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Student() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, age, mobile, gender, fee;

                name = txtName.getText();
                age = txtAge.getText();
                mobile = txtMobileNumber.getText();
                gender = txtGender.getText();
                fee = txtFee.getText();

                try {
                    pst = con.prepareStatement("insert into studentz(name,age,mobile,gender,fee)values(?,?,?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,age);
                    pst.setString(3,mobile);
                    pst.setString(4,gender);
                    pst.setString(5,fee);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"record added");

                    table_load();

                    txtName.setText("");
                    txtAge.setText("");
                    txtMobileNumber.setText("");
                    txtGender.setText("");
                    txtFee.setText("");
                    txtName.requestFocus();


                } catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    String id = txtID.getText();

                    pst = con.prepareStatement("select name,age,mobile,gender,fee from dbz.studentz where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true){
                        String name = rs.getString(1);
                        String age = rs.getString(2);
                        String mobile = rs.getString(3);
                        String gender = rs.getString(4);
                        String fee = rs.getString(5);

                        txtName.setText(name);
                        txtAge.setText(age);
                        txtMobileNumber.setText(mobile);
                        txtGender.setText(gender);
                        txtFee.setText(fee);

                    }
                    else {
                        txtName.setText("");
                        txtAge.setText("");
                        txtMobileNumber.setText("");
                        txtGender.setText("");
                        txtFee.setText("");

                        JOptionPane.showMessageDialog(null,"Invalid Student ID");
                    }

                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, age, mobile, gender, fee, id;

                name = txtName.getText();
                age = txtAge.getText();
                mobile = txtMobileNumber.getText();
                gender = txtGender.getText();
                fee = txtFee.getText();
                id = txtID.getText();


                try{

                    pst = con.prepareStatement("update dbz.studentz set name = ?, age = ?, mobile = ?, gender = ?, fee = ? where id = ?");
                    pst.setString(1,name);
                    pst.setString(2,age);
                    pst.setString(3,mobile);
                    pst.setString(4,gender);
                    pst.setString(5,fee);
                    pst.setString(6,id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated");
                    table_load();

                    txtName.setText("");
                    txtAge.setText("");
                    txtMobileNumber.setText("");
                    txtGender.setText("");
                    txtFee.setText("");

                    txtName.requestFocus();

                }catch (SQLException e1){
                    e1.printStackTrace();
                }


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;

                id = txtID.getText();

                try{

                    pst = con.prepareStatement("delete from dbz.studentz where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();

                    txtName.setText("");
                    txtAge.setText("");
                    txtMobileNumber.setText("");
                    txtGender.setText("");
                    txtFee.setText("");

                    txtName.requestFocus();

                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame studentFrame = (JFrame) SwingUtilities.getWindowAncestor(Main);
                studentFrame.dispose();

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