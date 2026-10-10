package projectmanagement.service;

import projectmanagement.model.User;
import projectmanagement.util.DBConnection;
import projectmanagement.util.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDBService {

    // Insert User
    public void addUser(User user) {

        String sql = "INSERT INTO users " +
                "(user_id, name, email, password, role) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().name());

            statement.executeUpdate();

            System.out.println("User inserted into database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Users
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                );

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // Delete User
    public void deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted from database.");
            } else {
                System.out.println("User not found in database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}