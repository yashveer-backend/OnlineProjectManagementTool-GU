package projectmanagement.gui;

import projectmanagement.model.User;
import projectmanagement.service.AuthService;
import projectmanagement.service.UserService;
import projectmanagement.util.Role;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Online Project Management Tool");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Online Project Management Tool");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {

            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            UserService userService = new UserService();

            User admin = new User(
                    1,
                    "Admin",
                    "admin@gmail.com",
                    "admin123",
                    Role.ADMIN
            );

            User member = new User(
                    2,
                    "Rahul",
                    "rahul@gmail.com",
                    "rahul123",
                    Role.TEAM_MEMBER
            );
            User manager = new User(
                    3,
                    "Manager",
                    "manager@gmail.com",
                    "manager123",
                    Role.PROJECT_MANAGER
            );


            userService.addUser(admin);
            userService.addUser(member);
            userService.addUser(manager);

            AuthService authService = new AuthService();

            User loggedInUser = authService.login(
                    email,
                    password,
                    userService.getUsers()
            );

            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!\nWelcome, "
                                + loggedInUser.getName()
                                + "\nRole: "
                                + loggedInUser.getRole()
                );

                DashboardFrame dashboardFrame =
                        new DashboardFrame(loggedInUser);

                dashboardFrame.setVisible(true);

                dispose();


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password!"
                );
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(loginButton, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
