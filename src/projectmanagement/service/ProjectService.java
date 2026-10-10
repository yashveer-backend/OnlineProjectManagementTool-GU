package projectmanagement.service;

import projectmanagement.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    private List<Project> projects = new ArrayList<>();

    public void addProject(Project project) {
        projects.add(project);
        System.out.println("Project added successfully.");
    }

    public void showAllProjects() {
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }

        for (Project project : projects) {
            System.out.println(project);
        }
    }

    public List<Project> getProjects() {
        return projects;
    }
}