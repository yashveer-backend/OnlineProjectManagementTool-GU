package projectmanagement.service;

import projectmanagement.model.Project;
import projectmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDBService {

    // Insert Project
    public void addProject(Project project) {

        String sql = "INSERT INTO projects " +
                "(project_id, project_name, description, status, manager_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, project.getProjectId());
            statement.setString(2, project.getProjectName());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getStatus());
            statement.setInt(5, project.getManagerId());

            statement.executeUpdate();

            System.out.println("Project inserted into database.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Projects
    public List<Project> getAllProjects() {

        List<Project> projects = new ArrayList<>();

        String sql = "SELECT * FROM projects";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Project project = new Project(
                        resultSet.getInt("project_id"),
                        resultSet.getString("project_name"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getInt("manager_id")
                );

                projects.add(project);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }
}