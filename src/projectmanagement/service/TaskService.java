package projectmanagement.service;

import projectmanagement.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    public void showAllTasks() {

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void showTasksForUser(int userId) {

        boolean found = false;

        for (Task task : tasks) {

            if (task.getAssignedTo() == userId) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks assigned to this user.");
        }
    }


    public void updateTaskStatus(int taskId, String newStatus) {

        for (Task task : tasks) {

            if (task.getTaskId() == taskId) {

                task.setStatus(newStatus);

                System.out.println("Task status updated successfully.");
                return;
            }
        }

        System.out.println("Task not found.");
    }
    public List<Task> getTasksForUser(int userId) {

        List<Task> userTasks = new ArrayList<>();

        for (Task task : tasks) {

            if (task.getAssignedTo() == userId) {
                userTasks.add(task);
            }
        }

        return userTasks;
    }

}