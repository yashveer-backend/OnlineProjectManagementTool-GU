package projectmanagement.gui;

import projectmanagement.service.TaskDBService;
import projectmanagement.model.Project;
import projectmanagement.model.Task;
import projectmanagement.model.User;
import projectmanagement.service.ProjectService;
import projectmanagement.service.TaskService;
import projectmanagement.service.UserService;
import projectmanagement.util.Role;
import projectmanagement.service.ProjectDBService;
import projectmanagement.service.UserDBService;
import projectmanagement.service.TaskUpdateThread;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private UserService userService;
    private ProjectService projectService;
    private TaskService taskService;
    private TaskDBService taskDBService = new TaskDBService();

    public DashboardFrame(User user) {

        userService = new UserService();
        projectService = new ProjectService();
        taskService = new TaskService();

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

        userService.addUser(admin);
        userService.addUser(member);

        setTitle("Dashboard - Online Project Management Tool");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + user.getName()
                        + " | Role: " + user.getRole(),
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel =
                new JPanel(new GridLayout(2, 2, 15, 15));

        if (user.getRole() == Role.ADMIN) {

            JButton usersButton =
                    new JButton("User Management");

            JButton projectButton =
                    new JButton("Project Management");
            projectButton.addActionListener(e -> {

                String[] options = {
                        "View Projects",
                        "Add Project",
                        "Cancel"
                };

                String choice =
                        (String) JOptionPane.showInputDialog(
                                this,
                                "Select an option:",
                                "Manage Projects",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                if (choice == null ||
                        choice.equals("Cancel")) {
                    return;
                }

                if (choice.equals("View Projects")) {

                    if (projectService.getProjects().isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "No projects found."
                        );

                    } else {

                        StringBuilder projects =
                                new StringBuilder();

                        for (Project project :
                                projectService.getProjects()) {

                            projects.append(project)
                                    .append("\n\n");
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                projects.toString(),
                                "All Projects",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } else if (choice.equals("Add Project")) {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Project Name:"
                            );

                    if (name == null ||
                            name.trim().isEmpty()) {
                        return;
                    }

                    String description =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Project Description:"
                            );

                    if (description == null ||
                            description.trim().isEmpty()) {
                        return;
                    }

                    String[] statuses = {
                            "ACTIVE",
                            "PENDING",
                            "COMPLETED"
                    };

                    String status =
                            (String) JOptionPane.showInputDialog(
                                    this,
                                    "Select Project Status:",
                                    "Project Status",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    statuses,
                                    statuses[0]
                            );

                    if (status == null) {
                        return;
                    }

                    int projectId =
                            projectService.getProjects().size() + 101;

                    Project project =
                            new Project(
                                    projectId,
                                    name,
                                    description,
                                    status,
                                    user.getUserId()
                            );

                    projectService.addProject(project);

                    ProjectDBService projectDBService =
                            new ProjectDBService();

                    projectDBService.addProject(project);
                    JOptionPane.showMessageDialog(
                            this,
                            "Project created successfully!\n\n"
                                    + "Project ID: "
                                    + project.getProjectId()
                                    + "\nProject Name: "
                                    + project.getProjectName()
                                    + "\nDescription: "
                                    + project.getDescription()
                                    + "\nStatus: "
                                    + project.getStatus()
                    );
                }
            });

            JButton performanceButton =
                    new JButton("Performance");
            performanceButton.addActionListener(e -> {

                JOptionPane.showMessageDialog(
                        this,
                        "===== PERFORMANCE =====\n\n"
                                + "Total Users: "
                                + userService.getUsers().size()
                                + "\nTotal Projects: "
                                + projectService.getProjects().size()
                                + "\n\nSystem is running successfully.",
                        "Performance",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });

            JButton logoutButton =
                    new JButton("Logout");


            usersButton.addActionListener(e -> {

                String[] options = {
                        "View Users",
                        "Add User",
                        "Delete User",
                        "Cancel"
                };

                String choice =
                        (String) JOptionPane.showInputDialog(
                                this,
                                "Select an option:",
                                "User Management",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                if (choice == null ||
                        choice.equals("Cancel")) {
                    return;
                }

                if (choice.equals("View Users")) {

                    if (userService.getUsers().isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "No users found."
                        );

                    } else {

                        StringBuilder users =
                                new StringBuilder();

                        for (User u :
                                userService.getUsers()) {

                            users.append(u)
                                    .append("\n\n");
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                users.toString(),
                                "All Users",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } else if (choice.equals("Add User")) {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter User Name:"
                            );

                    if (name == null ||
                            name.trim().isEmpty()) {
                        return;
                    }

                    String email =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Email:"
                            );

                    if (email == null ||
                            email.trim().isEmpty()) {
                        return;
                    }

                    String password =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Password:"
                            );

                    if (password == null ||
                            password.trim().isEmpty()) {
                        return;
                    }

                    String[] roles = {
                            "ADMIN",
                            "PROJECT_MANAGER",
                            "TEAM_MEMBER"
                    };

                    String selectedRole =
                            (String) JOptionPane.showInputDialog(
                                    this,
                                    "Select Role:",
                                    "User Role",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    roles,
                                    roles[2]
                            );

                    if (selectedRole == null) {
                        return;
                    }

                    Role role =
                            Role.valueOf(selectedRole);

                    int newUserId =
                            userService.getUsers().size() + 1;

                    User newUser =
                            new User(
                                    newUserId,
                                    name,
                                    email,
                                    password,
                                    role
                            );

                    userService.addUser(newUser);

                    UserDBService userDBService = new UserDBService();
                    userDBService.addUser(newUser);

                    JOptionPane.showMessageDialog(
                            this,
                            "User created successfully!\n\n"
                                    + "User ID: "
                                    + newUser.getUserId()
                                    + "\nName: "
                                    + newUser.getName()
                                    + "\nEmail: "
                                    + newUser.getEmail()
                                    + "\nRole: "
                                    + newUser.getRole()
                    );

                } else if (choice.equals("Delete User")) {

                    String idInput =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter User ID to delete:"
                            );

                    if (idInput == null) {
                        return;
                    }

                    try {

                        int userId =
                                Integer.parseInt(idInput.trim());

                        User userToDelete =
                                userService.findUserById(userId);

                        // Admin cannot be deleted
                        if (userToDelete != null &&
                                userToDelete.getRole() == Role.ADMIN) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Admin user cannot be deleted."
                            );

                            return;
                        }

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        this,
                                        "Are you sure you want to delete User ID: "
                                                + userId + "?",
                                        "Confirm Delete",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm == JOptionPane.YES_OPTION) {

                            UserDBService userDBService =
                                    new UserDBService();

                            userDBService.deleteUser(userId);

                            // Remove from in-memory list if present
                            if (userToDelete != null) {
                                userService.deleteUser(userId);
                            }

                            JOptionPane.showMessageDialog(
                                    this,
                                    "User deleted successfully!"
                            );
                        }

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter a valid User ID."
                        );
                    }
                }
            });

            projectButton.addActionListener(e -> {

                String[] options = {
                        "View Projects",
                        "Add Project",
                        "Cancel"
                };

                String choice =
                        (String) JOptionPane.showInputDialog(
                                this,
                                "Select an option:",
                                "Project Management",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                if (choice == null ||
                        choice.equals("Cancel")) {
                    return;
                }

                if (choice.equals("View Projects")) {

                    if (projectService.getProjects().isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "No projects found."
                        );

                    } else {

                        StringBuilder projects =
                                new StringBuilder();

                        for (Project project :
                                projectService.getProjects()) {

                            projects.append(project)
                                    .append("\n\n");
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                projects.toString(),
                                "All Projects",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } else if (choice.equals("Add Project")) {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Project Name:"
                            );

                    if (name == null ||
                            name.trim().isEmpty()) {
                        return;
                    }

                    String description =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Project Description:"
                            );

                    if (description == null ||
                            description.trim().isEmpty()) {
                        return;
                    }

                    String[] statuses = {
                            "ACTIVE",
                            "PENDING",
                            "COMPLETED"
                    };

                    String status =
                            (String) JOptionPane.showInputDialog(
                                    this,
                                    "Select Project Status:",
                                    "Project Status",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    statuses,
                                    statuses[0]
                            );

                    if (status == null) {
                        return;
                    }

                    int projectId =
                            projectService.getProjects().size() + 101;

                    Project project =
                            new Project(
                                    projectId,
                                    name,
                                    description,
                                    status,
                                    user.getUserId()
                            );

                    projectService.addProject(project);

                    JOptionPane.showMessageDialog(
                            this,
                            "Project created successfully!\n\n"
                                    + "Project ID: "
                                    + project.getProjectId()
                                    + "\nProject Name: "
                                    + project.getProjectName()
                                    + "\nDescription: "
                                    + project.getDescription()
                                    + "\nStatus: "
                                    + project.getStatus()
                    );
                }
            });

            logoutButton.addActionListener(e -> {

                int choice =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to logout?",
                                "Logout",
                                JOptionPane.YES_NO_OPTION
                        );

                if (choice ==
                        JOptionPane.YES_OPTION) {

                    dispose();

                    LoginFrame loginFrame =
                            new LoginFrame();

                    loginFrame.setVisible(true);
                }
            });

            menuPanel.add(usersButton);
            menuPanel.add(projectButton);
            menuPanel.add(performanceButton);
            menuPanel.add(logoutButton);

        } else if (user.getRole() ==
                Role.PROJECT_MANAGER) {

            JButton manageProjectsButton =
                    new JButton("Manage Projects");
            manageProjectsButton.addActionListener(e -> {

                String[] options = {
                        "View Projects",
                        "Add Project",
                        "Cancel"
                };

                String choice =
                        (String) JOptionPane.showInputDialog(
                                this,
                                "Select an option:",
                                "Manage Projects",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );

                if (choice == null ||
                        choice.equals("Cancel")) {
                    return;
                }

                if (choice.equals("View Projects")) {

                    if (projectService.getProjects().isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "No projects found."
                        );

                    } else {

                        StringBuilder projects =
                                new StringBuilder();

                        for (Project project :
                                projectService.getProjects()) {

                            projects.append(project)
                                    .append("\n\n");
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                projects.toString(),
                                "All Projects",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } else if (choice.equals("Add Project")) {

                    String name =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Project Name:"
                            );

                    if (name == null ||
                            name.trim().isEmpty()) {
                        return;
                    }

                    String description =
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Project Description:"
                            );

                    if (description == null ||
                            description.trim().isEmpty()) {
                        return;
                    }

                    String[] statuses = {
                            "ACTIVE",
                            "PENDING",
                            "COMPLETED"
                    };

                    String status =
                            (String) JOptionPane.showInputDialog(
                                    this,
                                    "Select Project Status:",
                                    "Project Status",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    statuses,
                                    statuses[0]
                            );

                    if (status == null) {
                        return;
                    }

                    int projectId =
                            projectService.getProjects().size() + 101;

                    Project project =
                            new Project(
                                    projectId,
                                    name,
                                    description,
                                    status,
                                    user.getUserId()
                            );

                    projectService.addProject(project);
                    ProjectDBService projectDBService = new ProjectDBService();
                    projectDBService.addProject(project);


                    JOptionPane.showMessageDialog(
                            this,
                            "Project created successfully!\n\n"
                                    + "Project ID: "
                                    + project.getProjectId()
                                    + "\nProject Name: "
                                    + project.getProjectName()
                                    + "\nDescription: "
                                    + project.getDescription()
                                    + "\nStatus: "
                                    + project.getStatus()
                    );
                }
            });

            JButton taskButton =
                    new JButton("Assign Tasks");
            taskButton.addActionListener(e -> {

                String taskName =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Task Name:"
                        );

                if (taskName == null ||
                        taskName.trim().isEmpty()) {
                    return;
                }

                String description =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Task Description:"
                        );

                if (description == null ||
                        description.trim().isEmpty()) {
                    return;
                }

                String projectIdInput =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Project ID:"
                        );

                if (projectIdInput == null) {
                    return;
                }

                String userIdInput =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Team Member User ID:"
                        );

                if (userIdInput == null) {
                    return;
                }

                try {

                    int projectId =
                            Integer.parseInt(projectIdInput.trim());

                    int assignedTo =
                            Integer.parseInt(userIdInput.trim());

                    int taskId = 1001;

                    Task task =
                            new Task(
                                    taskId,
                                    taskName,
                                    description,
                                    "PENDING",
                                    projectId,
                                    assignedTo
                            );

                    taskService.addTask(task);

                    taskDBService.addTask(task);

                    JOptionPane.showMessageDialog(
                            this,
                            "Task assigned successfully!\n\n"
                                    + "Task ID: "
                                    + task.getTaskId()
                                    + "\nTask Name: "
                                    + task.getTaskName()
                                    + "\nProject ID: "
                                    + task.getProjectId()
                                    + "\nAssigned To User ID: "
                                    + task.getAssignedTo()
                                    + "\nStatus: "
                                    + task.getStatus()
                    );

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid numeric IDs."
                    );
                }
            });

            JButton teamButton =
                    new JButton("Team Members");
            teamButton.addActionListener(e -> {

                if (userService.getUsers().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No team members found."
                    );

                    return;
                }

                StringBuilder members =
                        new StringBuilder();

                boolean found = false;

                for (User u : userService.getUsers()) {

                    if (u.getRole() == Role.TEAM_MEMBER) {

                        members.append(
                                "User ID: "
                                        + u.getUserId()
                                        + "\nName: "
                                        + u.getName()
                                        + "\nEmail: "
                                        + u.getEmail()
                                        + "\nRole: "
                                        + u.getRole()
                                        + "\n\n"
                        );

                        found = true;
                    }
                }
                if (found) {

                    JOptionPane.showMessageDialog(
                            this,
                            members.toString(),
                            "Team Members",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "No team members found."
                    );
                }
            });

            JButton logoutButton =
                    new JButton("Logout");
            logoutButton.addActionListener(e -> {

                int choice =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to logout?",
                                "Logout",
                                JOptionPane.YES_NO_OPTION
                        );

                if (choice == JOptionPane.YES_OPTION) {

                    dispose();

                    LoginFrame loginFrame =
                            new LoginFrame();

                    loginFrame.setVisible(true);
                }
            });

            menuPanel.add(manageProjectsButton);
            menuPanel.add(taskButton);
            menuPanel.add(teamButton);
            menuPanel.add(logoutButton);

        } else if (user.getRole() ==
                Role.TEAM_MEMBER) {

            JButton myTasksButton =
                    new JButton("My Tasks");

            myTasksButton.addActionListener(e -> {

                StringBuilder taskDetails =
                        new StringBuilder();

                for (Task t :
                        taskDBService.getTasksForUser(
                                user.getUserId())) {

                    taskDetails.append(t)
                            .append("\n\n");
                }

                if (taskDetails.length() == 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "No tasks assigned to you."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            taskDetails.toString(),
                            "My Tasks",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            });
            JButton projectDetailsButton =
                    new JButton("Project Details");

            projectDetailsButton.addActionListener(e -> {

                String projectDetails =
                        "Project ID: 101\n"
                                + "Project Name: Online Project Management Tool\n"
                                + "Description: A tool to manage projects and tasks\n"
                                + "Status: ACTIVE\n"
                                + "Manager ID: 1";

                JOptionPane.showMessageDialog(
                        this,
                        projectDetails,
                        "Project Details",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });

            JButton updateTaskButton =
                    new JButton("Update Task");

            updateTaskButton.addActionListener(e -> {

                String taskIdInput =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter Task ID:"
                        );

                if (taskIdInput == null) {
                    return;
                }

                try {

                    int taskId =
                            Integer.parseInt(
                                    taskIdInput.trim()
                            );

                    String[] statuses = {
                            "PENDING",
                            "IN_PROGRESS",
                            "COMPLETED"
                    };

                    String newStatus =
                            (String) JOptionPane.showInputDialog(
                                    this,
                                    "Select New Status:",
                                    "Update Task",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    statuses,
                                    statuses[0]
                            );

                    if (newStatus == null) {
                        return;
                    }

                    TaskUpdateThread updateThread =
                            new TaskUpdateThread(taskId, newStatus);

                    updateThread.start();

                    JOptionPane.showMessageDialog(
                            this,
                            "Task status updated successfully!\n\n"
                                    + "Task ID: "
                                    + taskId
                                    + "\nNew Status: "
                                    + newStatus
                    );

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid Task ID."
                    );
                }
            });

            JButton logoutButton =
                    new JButton("Logout");

            logoutButton.addActionListener(e -> {

                int choice =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to logout?",
                                "Logout",
                                JOptionPane.YES_NO_OPTION
                        );

                if (choice ==
                        JOptionPane.YES_OPTION) {

                    dispose();

                    LoginFrame loginFrame =
                            new LoginFrame();

                    loginFrame.setVisible(true);
                }
            });

            menuPanel.add(myTasksButton);
            menuPanel.add(projectDetailsButton);
            menuPanel.add(updateTaskButton);
            menuPanel.add(logoutButton);
        }

        panel.add(
                menuPanel,
                BorderLayout.CENTER
        );

        add(panel);
    }
}