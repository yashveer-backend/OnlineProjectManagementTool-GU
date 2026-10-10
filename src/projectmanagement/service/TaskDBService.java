package projectmanagement.service;

import projectmanagement.model.Task;
import projectmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskDBService implements TaskOperations {

    // Insert Task
    public void addTask(Task task) {

        String sql = "INSERT INTO tasks " +
                "(task_id, task_name, description, status, project_id, assigned_to) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, task.getTaskId());
            statement.setString(2, task.getTaskName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getStatus());
            statement.setInt(5, task.getProjectId());
            statement.setInt(6, task.getAssignedTo());

            statement.executeUpdate();

            System.out.println("Task inserted into database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Tasks
    public List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Task task = new Task(
                        resultSet.getInt("task_id"),
                        resultSet.getString("task_name"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("assigned_to")
                );

                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Get Tasks For Specific User
    public List<Task> getTasksForUser(int userId) {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks WHERE assigned_to = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Task task = new Task(
                            resultSet.getInt("task_id"),
                            resultSet.getString("task_name"),
                            resultSet.getString("description"),
                            resultSet.getString("status"),
                            resultSet.getInt("project_id"),
                            resultSet.getInt("assigned_to")
                    );

                    tasks.add(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Update Task Status
    @Override
    public void updateTaskStatus(int taskId, String newStatus) {

        String sql = "UPDATE tasks SET status = ? WHERE task_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, newStatus);
            statement.setInt(2, taskId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("Task not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}