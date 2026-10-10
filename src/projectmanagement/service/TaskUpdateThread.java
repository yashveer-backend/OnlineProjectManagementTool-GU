package projectmanagement.service;

public class TaskUpdateThread extends Thread {

    private int taskId;
    private String newStatus;

    public TaskUpdateThread(int taskId, String newStatus) {
        this.taskId = taskId;
        this.newStatus = newStatus;
    }

    @Override
    public void run() {

        synchronized (TaskUpdateThread.class) {

            System.out.println(
                    "Thread " + Thread.currentThread().getName()
                            + " is updating task..."
            );

            TaskDBService taskDBService =
                    new TaskDBService();

            taskDBService.updateTaskStatus(
                    taskId,
                    newStatus
            );

            System.out.println(
                    "Task update completed by "
                            + Thread.currentThread().getName()
            );
        }
    }
}