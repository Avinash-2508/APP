import javax.swing.*;
import java.sql.*;

public class StudentRegistration {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Registration Form");
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JButton submitBtn = new JButton("Submit");

        nameLabel.setBounds(30, 50, 80, 25);
        nameField.setBounds(120, 50, 200, 25);
        emailLabel.setBounds(30, 90, 80, 25);
        emailField.setBounds(120, 90, 200, 25);
        submitBtn.setBounds(120, 130, 100, 30);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(submitBtn);

        frame.setSize(400, 250);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submitBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
            } else {
                try {
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/studentdb", "root", "password");
                    PreparedStatement pst = con.prepareStatement(
                            "INSERT INTO students(name, email) VALUES(?, ?)");
                    pst.setString(1, name);
                    pst.setString(2, email);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Data Saved Successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });
    }
}