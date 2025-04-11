import Impl.TaskManagerImpl;
import Service.TaskManager;

public class Main {

    public static void main(String[] args) throws Exception {

        TaskManagerImpl taskManager = new TaskManagerImpl();

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

        }

    }
}