package Service;

public interface TaskManager {

    // Abstract method to add task
    public void addTask(String taskDescription);

    // Method to updateTask
    public void updateTask(int taskID, String description);

    // Method to deleteTask
    public void deleteTask(int taskID);

}
