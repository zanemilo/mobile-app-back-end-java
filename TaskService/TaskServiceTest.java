package cs320mod4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @BeforeEach
    void resetIdCounter() {  // Resets ID increment in TaskService to ensure consistent IDs in tests
        TaskService.resetIdCounter();
    }

    @Test
    void testAddTask() {
        TaskService service = new TaskService();
        service.addTask("TaskName", "This is a valid task description.");

        Task task = service.getTask("0000000001");
        assertNotNull(task);
        assertEquals("TaskName", task.getName());
        assertEquals("This is a valid task description.", task.getDesc());
    }

    @Test
    void testAddMultipleTasks() {
        TaskService service = new TaskService();
        service.addTask("Task One", "Description for task one.");
        service.addTask("Task Two", "Description for task two.");
        service.addTask("Task Three", "Description for task three.");

        Task task1 = service.getTask("0000000001");
        Task task2 = service.getTask("0000000002");
        Task task3 = service.getTask("0000000003");

        assertNotNull(task1);
        assertNotNull(task2);
        assertNotNull(task3);

        assertEquals("Task One", task1.getName());
        assertEquals("Description for task one.", task1.getDesc());

        assertEquals("Task Two", task2.getName());
        assertEquals("Description for task two.", task2.getDesc());

        assertEquals("Task Three", task3.getName());
        assertEquals("Description for task three.", task3.getDesc());
    }

    @Test
    void testAddTaskUniqueId() {
        TaskService service = new TaskService();
        service.addTask("TaskOne", "Description for task one.");
        service.addTask("TaskTwo", "Description for task two.");

        Task task1 = service.getTask("0000000001");
        Task task2 = service.getTask("0000000002");

        assertNotNull(task1);
        assertNotNull(task2);
        assertNotEquals(task1.getId(), task2.getId());
    }

    @Test
    void testAddTaskEnsuresUniqueIds() {
        TaskService service = new TaskService();
        service.addTask("Task One", "Description for task one.");
        service.addTask("Task Two", "Description for task two.");

        Task task1 = service.getTask("0000000001");
        Task task2 = service.getTask("0000000002");

        assertNotNull(task1);
        assertNotNull(task2);
        assertNotEquals(task1.getId(), task2.getId());
    }

    @Test
    void testDeleteTask() {
        TaskService service = new TaskService();
        service.addTask("TaskToDelete", "Description for task to delete.");
        service.deleteTask("0000000001");

        assertNull(service.getTask("0000000001"));
    }

    @Test
    void testDeleteOneOfMultipleTasks() {
        TaskService service = new TaskService();
        service.addTask("Task One", "Description for task one.");
        service.addTask("Task Two", "Description for task two.");
        service.addTask("Task Three", "Description for task three.");

        service.deleteTask("0000000002");

        assertNull(service.getTask("0000000002"));
        assertNotNull(service.getTask("0000000001"));
        assertNotNull(service.getTask("0000000003"));
    }

    @Test
    void testDeleteTaskNotFound() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> service.deleteTask("0000000001"));
    }

    @Test
    void testUpdateTaskNameAndDescription() {
        TaskService service = new TaskService();
        service.addTask("OriginalTaskName", "Original task description.");

        service.updateTask("0000000001", "UpdatedTaskName", "Updated task description.");
        Task updatedTask = service.getTask("0000000001");

        assertEquals("UpdatedTaskName", updatedTask.getName());
        assertEquals("Updated task description.", updatedTask.getDesc());
    }

    @Test
    void testUpdateTaskNameOnly() {
        TaskService service = new TaskService();
        service.addTask("OriginalTaskName", "Original task description.");

        service.updateTask("0000000001", "UpdatedTaskName", null);
        Task updatedTask = service.getTask("0000000001");

        assertEquals("UpdatedTaskName", updatedTask.getName());
        assertEquals("Original task description.", updatedTask.getDesc());
    }

    @Test
    void testUpdateTaskDescriptionOnly() {
        TaskService service = new TaskService();
        service.addTask("OriginalTaskName", "Original task description.");

        service.updateTask("0000000001", null, "Updated task description.");
        Task updatedTask = service.getTask("0000000001");

        assertEquals("OriginalTaskName", updatedTask.getName());
        assertEquals("Updated task description.", updatedTask.getDesc());
    }

    @Test
    void testUpdateTaskWithInvalidName() {
        TaskService service = new TaskService();
        service.addTask("TaskOne", "Description for task one.");

        assertThrows(IllegalArgumentException.class, () -> 
            service.updateTask("0000000001", "ThisNameIsWayTooLongForValidation", null)
        );
    }

    @Test
    void testUpdateTaskWithInvalidDescription() {
        TaskService service = new TaskService();
        service.addTask("TaskOne", "Description for task one.");

        String longDescription = "This description is way too long and exceeds the allowed fifty characters.";
        assertThrows(IllegalArgumentException.class, () -> 
            service.updateTask("0000000001", null, longDescription)
        );
    }

    @Test
    void testUpdateTaskWithEmptyName() {
        TaskService service = new TaskService();
        service.addTask("TaskOne", "Description for task one.");

        service.updateTask("0000000001", "", null);
        Task updatedTask = service.getTask("0000000001");

        assertEquals("TaskOne", updatedTask.getName());
        assertEquals("Description for task one.", updatedTask.getDesc());
    }

    @Test
    void testUpdateTaskWithEmptyDescription() {
        TaskService service = new TaskService();
        service.addTask("TaskOne", "Description for task one.");

        service.updateTask("0000000001", null, "");
        Task updatedTask = service.getTask("0000000001");

        assertEquals("TaskOne", updatedTask.getName());
        assertEquals("Description for task one.", updatedTask.getDesc());
    }

    @Test
    void testUpdateTaskNotFound() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> 
            service.updateTask("0000000001", "UpdatedTaskName", "Updated task description.")
        );
    }

    @Test
    void testGetTaskNotFound() {
        TaskService service = new TaskService();
        assertNull(service.getTask("0000000001"));
    }

    @Test
    void testResetIdCounter() {
        TaskService service = new TaskService();
        service.addTask("TaskOne", "Description for task one.");
        assertEquals("0000000001", service.getTask("0000000001").getId());

        TaskService.resetIdCounter();
        service.addTask("TaskTwo", "Description for task two.");
        assertEquals("0000000001", service.getTask("0000000001").getId()); // After reset, first ID is reused
    }
}
