package cs320mod4;

import java.util.HashMap;
import java.util.Map;

public class TaskService {
    private final Map<String, Task> tasks;
    private static int idCounter = 1;

    public TaskService() {
        this.tasks = new HashMap<>();
    }

    // Generate a unique ID with zero padding
    private String generateUniqueId() {
        if (idCounter > 999999999) {
            throw new IllegalStateException("ID limit exceeded");
        }
        return String.format("%010d", idCounter++);
    }

    // Add a task with a unique ID
    public void addTask(String name, String description) {
        String id = generateUniqueId();
        Task task = new Task(id, name, description);
        tasks.put(id, task);
    }

    // Delete a task by ID
    public void deleteTask(String id) {
        if (!tasks.containsKey(id)) {
            throw new IllegalArgumentException("No task found with ID " + id);
        }
        tasks.remove(id);
    }

    // Update task fields by ID
    public void updateTask(String id, String name, String description) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new IllegalArgumentException("No task found with ID " + id);
        }

        // Update only fields that are not null
        if (name != null && !name.isEmpty()) {
            task.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            task.setDesc(description);
        }
    }

    // Retrieve a task by ID
    public Task getTask(String id) {
        return tasks.get(id);
    }

    // Reset ID counter for testing purposes
    public static void resetIdCounter() {
        idCounter = 1; // Reset the counter
    }
}
