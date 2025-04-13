package Impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Model.Task;
import Model.TaskStatus;
import Service.TaskManager;

public class TaskManagerImpl implements TaskManager {

    private final Path JSON_FILE_PATH = Path.of("Tasks.json");
    private List<Task> tasksList;

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public TaskManagerImpl() {
        this.tasksList = loadTask();
    }

    private List<Task> loadTask() {
        // check if does not exists
        if(!Files.exists(JSON_FILE_PATH)){
            try{
                Files.createFile(JSON_FILE_PATH);
            }
            catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                throw new RuntimeException(e);
            }
            return new ArrayList<>();
        }
        List<Task> existing_tasks = new ArrayList<>();
        StringBuilder jsonBuilder = new StringBuilder();

        try(BufferedReader jsonReader = new BufferedReader(new FileReader("Tasks.json"))){
            String line;
            while((line = jsonReader.readLine()) != null){
                jsonBuilder.append(line.trim());
            }

            String json = jsonBuilder.toString();

            // Removing the square bracket from the string
            json = json.substring(1, json.length()-1);  // Remove [ ]

            String[] items = json.split("},\\{");

            for(String item : items){
                item = item.replace("{", "").replace("}", "");

                // item :- "id" : 1, "status" : -----, "description" : ------, "createdAt" : --------, "updatedAt" : ------"
                String[] attributes = item.split(",");
                int id = 0;
                TaskStatus status = null;
                String description = "";
                LocalDateTime createdAt = null, updatedAt = null;

                for(String attribute : attributes){
                    // "id" : 1
                    String[] field_value = attribute.split(":", 2);
                    String key = field_value[0].replace("\"", "").trim();
                    String value = field_value[1].replace("\"", "").trim();

                    switch (key){
                        case "id":
                            id = Integer.parseInt(value);
                            break;
                        case "status":
                            status = TaskStatus.valueOf(value.toUpperCase().replace("-", "_"));
                            break;
                        case "description":
                            description = value;
                            break;
                        case "createdAt":
                            createdAt = LocalDateTime.parse(value, formatter);
                            break;
                        case "updatedAt":
                            updatedAt = LocalDateTime.parse(value, formatter);
                            break;
                    }

                }

                existing_tasks.add(new Task(id, status, description, createdAt, updatedAt));
                
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return existing_tasks;
    }

    @Override
    public int addTask(String taskDescription) {
        Task task = new Task(taskDescription);
        tasksList.add(task);
        return task.getId();
    }

    @Override
    public void updateTask(int taskID, String description) throws Exception {
        Task task = findTask(taskID);
        if(task == null){
            throw new Exception("task ID not found");
        }
        task.updateTask(description);
    }

    @Override
    public void deleteTask(int taskID) throws Exception {
        Task task = findTask(taskID);
        if(task == null){
            throw new Exception("task ID not found");
        }
        tasksList.remove(task);
        // Delete if Task.json is empty
        if(tasksList.isEmpty()){
            Files.deleteIfExists(JSON_FILE_PATH);
        }
    }

    @Override
    public void markInProgress(int taskID) throws Exception {
        Task task = findTask(taskID);
        if(task == null){
            throw new Exception("Task ID not found");
        }
        task.markInProgress();
    }

    @Override
    public void markDone(int taskID) throws Exception {
        Task task = findTask(taskID);
        if(task == null){
            throw new Exception("Task ID not found");
        }
        task.markDone();
    }

    @Override
    public List<Task> listTasks(String status) {
        List<Task> list = new ArrayList<>();

        if(status.equals("All")){
            return tasksList;
        }

        for(Task task : tasksList){
            String taskStatus = task.getStatus().toString().strip();
            if(taskStatus.equals(status)){
                list.add(task);
            }
        }
        return list;
    }

    public Task findTask(int id){
        for (Task task: tasksList){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }

    public void saveTasks(){
        if(!Files.exists(JSON_FILE_PATH)){
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < tasksList.size(); i++) {
            sb.append(tasksList.get(i).toJson());
            if(i < tasksList.size() - 1){
                sb.append(",");
            }
        }

        sb.append("\n]");

        String jsonFormat = sb.toString();
        try{
            Files.writeString(JSON_FILE_PATH, jsonFormat);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
