package projectmanagement.service;

import projectmanagement.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added successfully.");
    }

    public void showAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    public User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }

        return null;
    }

    public void updateUserName(int userId, String newName) {
        User user = findUserById(userId);

        if (user != null) {
            user.setName(newName);
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void deleteUser(int userId) {
        User user = findUserById(userId);

        if (user != null) {
            users.remove(user);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public List<User> getUsers() {
        return users;
    }
}