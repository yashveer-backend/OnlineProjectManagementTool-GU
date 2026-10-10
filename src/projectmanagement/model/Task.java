package projectmanagement.model;

public class Task {

    private int taskId;
    private String taskName;
    private String description;
    private String status;
    private int projectId;
    private int assignedTo;

    public Task(int taskId, String taskName, String description,
                String status, int projectId, int assignedTo) {

        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId +
                ", Task Name: " + taskName +
                ", Description: " + description +
                ", Status: " + status +
                ", Project ID: " + projectId +
                ", Assigned To: " + assignedTo;
    }
}
