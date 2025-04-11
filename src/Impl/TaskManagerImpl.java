package Impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

    private final Path jsonFilePath = Path.of("Tasks.json");
    private List<Task> tasksList;

    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public TaskManagerImpl() {
        this.tasksList = loadTask();
    }

    private List<Task> loadTask() {
        // check if does not exists
        if(!Files.exists(jsonFilePath)){
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
    }

    public Task findTask(int id){
        for (Task task: tasksList){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }
}
