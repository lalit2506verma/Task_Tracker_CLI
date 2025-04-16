# Task Tracker CLI

A command-line interface (CLI) application for managing tasks.

## Installation

1. Clone the repository:
```
git clone https://github.com/your-username/task-tracker-cli.git
```
2. Compile the Java files:
```
javac Main.java
```

## Usage

To use the Task Tracker CLI, run the following command:

```
java Main [command] [arguments]
```

The available commands are:

- `add <description>`: Add a new task with the given description.
- `update [id] <description>`: Update the description of the task with the given ID.
- `delete <id>`: Delete the task with the given ID.
- `mark-in-progress <id>`: Mark the task with the given ID as "In Progress".
- `mark-done <id>`: Mark the task with the given ID as "Done".
- `list [status]`: List all tasks, or tasks with the specified status (e.g., "Todo", "In-Progress", "Done").

## API

The `TaskManager` interface defines the following methods:

```java
public int addTask(String taskDescription);
public void updateTask(int taskID, String description) throws Exception;
public void deleteTask(int taskID) throws Exception;
public void markInProgress(int taskID) throws Exception;
public void markDone(int taskID) throws Exception;
public List<Task> listTasks(String status);
public void saveTasks();
```

The `Task` class represents a task and has the following properties:

- `id`: The unique identifier of the task.
- `description`: The description of the task.
- `status`: The status of the task (Todo, In-Progress, Done).
- `createdAt`: The date and time when the task was created.
- `updatedAt`: The date and time when the task was last updated.

## Contributing

If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.


## Testing

To run the tests, execute the following command:

```
java -cp . Main
```

This will run the main class and execute the various commands, which can be used to test the functionality of the application.

Solution for the [Task Tracker](https://roadmap.sh/projects/task-tracker) challenge from [roadmap.sh](https://roadmap.sh).