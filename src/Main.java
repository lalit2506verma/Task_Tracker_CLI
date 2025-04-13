import java.util.List;

import Impl.TaskManagerImpl;
import Model.Task;
import Model.TaskStatus;
import Service.TaskManager;

public class Main {

    public static void main(String[] args) throws Exception {

        TaskManager taskManager = new TaskManagerImpl();

        if(args.length < 1){
            System.out.println("Usage: Task_Tracker_CLI command <description> [argument]");
            return;
        }

        String command = args[0].toLowerCase();

        switch (command){
            // Example " add "Buy groceries" "
            case "add":
                if(args.length < 2){
                    System.out.println("Usage: Task_Tracker_CLI ADD <description>");
                    return;
                }
                int id = taskManager.addTask(args[1]);
                System.out.println("Task added successfully (ID: " + id + ")");
                break;

            // Example "update 1 "Buy groceries and cook dinner""
            case "update":
                if(args.length < 3){
                    System.out.println("Usage: Task_Tracker_CLI UPDATE [id] <description>");
                    return;
                }
                taskManager.updateTask(Integer.parseInt(args[1]), args[2]);
                System.out.println("Task updated successfully");
                break;

            // Example "delete 1"
            case "delete":
                if(args.length < 2){
                    System.out.println("Usage: Task_Tracker_CLI DELETE <id>");
                    return;
                }
                taskManager.deleteTask(Integer.parseInt(args[1]));
                System.out.println("Task deleted successfully");
                break;

            case "mark-in-progress":
                if(args.length < 2){
                    System.out.println("Usage: Task_Tracker_CLI MARK-IN-PROGRESS <id>");
                    return;
                }
                taskManager.markInProgress(Integer.parseInt(args[1]));
                System.out.println("Task is marked as In-progress");
                break;

            case "mark-done":
                if(args.length < 2){
                    System.out.println("Usage: Task_Tracker_CLI MARK-IN-PROGRESS <id>");
                    return;
                }

                taskManager.markDone(Integer.parseInt(args[1]));
                System.out.println("Task is marked as Done");
                break;

            case "list":
                List<Task> list;
                if(args.length < 2){
                    // All task need to be fetched from Task.json
                    list =  taskManager.listTasks("All");
                }
                else{
                    TaskStatus status;

                    try{
                        status = TaskStatus.valueOf(args[1].toUpperCase().replace("-", "_"));
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Invalid Status");
                        e.printStackTrace();
                        return;
                    }
                    list = taskManager.listTasks(status.toString());
                }

                if(list.isEmpty()){
                    System.out.println("No Task Found");
                    return;
                }

                // print list
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).toString());
                }
                break;

            default:
                System.out.println("Invalid Command!!");
                break;
        }

        taskManager.saveTasks();

    }
}