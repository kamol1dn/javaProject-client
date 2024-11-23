package models;

public class Task {

    private String taskName;
    private User user;
    private String taskDeadline;

    public Task(String taskName, User user, String taskDeadline) {
        this.taskName = taskName;
        this.user = user;
        this.taskDeadline = taskDeadline;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getTaskDeadline() {
        return taskDeadline;
    }
    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }
    public String getTaskDeadlineString() {
        return taskDeadline;
    }

    @Override
    public String toString() {
        return "User " + user.getName() + ", Task " + taskName + ", Deadline " + taskDeadline;
    }
}
