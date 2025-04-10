package Impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Model.Task;
import Service.TaskManager;

public class TaskManagerImpl implements TaskManager {

    private final Path jsonFilePath = Path.of("Tasks.json");
    private List<Task> tasksList;

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
                
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return existing_tasks;
    }

    @Override
    public void addTask(String taskDescription) {

    }

    @Override
    public void updateTask(int taskID, String description) {

    }

    @Override
    public void deleteTask(int taskID) {

    }
}
