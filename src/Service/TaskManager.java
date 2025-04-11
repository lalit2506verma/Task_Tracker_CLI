package Service;

public interface TaskManager {

    // Abstract method to add task
    public int addTask(String taskDescription);

    // Method to updateTask
    public void updateTask(int taskID, String description) throws Exception;

    // Method to deleteTask
    public void deleteTask(int taskID) throws Exception;

}
