package projectmanagement.model;

public class Project {

    private int projectId;
    private String projectName;
    private String description;
    private String status;
    private int managerId;

    public Project(int projectId, String projectName, String description,
                   String status, int managerId) {

        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.status = status;
        this.managerId = managerId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project ID: " + projectId +
                ", Project Name: " + projectName +
                ", Description: " + description +
                ", Status: " + status +
                ", Manager ID: " + managerId;
    }
}
