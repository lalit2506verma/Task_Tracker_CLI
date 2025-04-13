package Service;

import java.util.List;

import Model.Task;

public interface TaskManager {

    // Abstract method to add task
    public int addTask(String taskDescription);

    // Method to updateTask
    public void updateTask(int taskID, String description) throws Exception;

    // Method to deleteTask
    public void deleteTask(int taskID) throws Exception;

    // Method to mark Task In-Progress
    public void markInProgress(int taskID) throws Exception;

    // Method to mark Task Done
    public void markDone(int taskID) throws Exception;

    // Method to list task based on its status
    public List<Task> listTasks(String status);

    // Method to save all the task from list to Task.json
    public void saveTasks();
}
