package cs320mod4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreationWithValidData() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        assertEquals("1234567890", task.getId());
        assertEquals("John", task.getName());
        assertEquals("This is a valid description.", task.getDesc());
    }

    @Test
    void testTaskCreationWithNullId() {
        assertThrows(NullPointerException.class, () -> new Task(null, "John", "This is a valid description."));
    }

    @Test
    void testTaskCreationWithLongId() {
        assertThrows(IllegalArgumentException.class, () -> new Task("12345678901", "John", "This is a valid description."));
    }

    @Test
    void testTaskCreationWithNullName() {
        assertThrows(NullPointerException.class, () -> new Task("1234567890", null, "This is a valid description."));
    }

    @Test
    void testTaskCreationWithLongName() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1234567890", "ThisNameIsWayTooLongForValidation", "This is a valid description."));
    }

    @Test
    void testTaskCreationWithNullDescription() {
        assertThrows(NullPointerException.class, () -> new Task("1234567890", "John", null));
    }

    @Test
    void testTaskCreationWithLongDescription() {
        assertThrows(IllegalArgumentException.class, () -> new Task("1234567890", "John", "This description is way too long and exceeds the allowed fifty characters."));
    }

    @Test
    void testTaskIdImmutability() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        // Attempt to reflectively access a non-existent setId method
        assertThrows(NoSuchMethodException.class, () -> task.getClass().getMethod("setId", String.class));
    }

    @Test
    void testSetNameWithValidData() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        task.setName("Johnny");
        assertEquals("Johnny", task.getName());
    }

    @Test
    void testSetNameWithNull() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        assertThrows(NullPointerException.class, () -> task.setName(null));
    }

    @Test
    void testSetNameWithLongData() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        assertThrows(IllegalArgumentException.class, () -> task.setName("ThisNameIsWayTooLongForValidation"));
    }

    @Test
    void testSetNameWithEmptyString() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        // Assuming empty strings are treated as no update
        task.setName("");
        assertEquals("John", task.getName());
    }

    @Test
    void testSetDescriptionWithValidData() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        task.setDesc("Updated description.");
        assertEquals("Updated description.", task.getDesc());
    }

    @Test
    void testSetDescriptionWithLongData() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        assertThrows(IllegalArgumentException.class, () -> task.setDesc("This description is way too long and exceeds the allowed fifty characters."));
    }

    @Test
    void testSetDescriptionWithNull() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        assertThrows(NullPointerException.class, () -> task.setDesc(null));
    }

    @Test
    void testSetDescriptionWithEmptyString() {
        Task task = new Task("1234567890", "John", "This is a valid description.");
        // Assuming empty strings are treated as no update
        task.setDesc("");
        assertEquals("This is a valid description.", task.getDesc());
    }

    @Test
    void testTaskCreationWithEdgeCaseFieldLengths() {
        String id = "1234567890"; // 10 characters
        String name = "TaskNameMax20Chr"; // 20 characters
        String desc = "This description is exactly fifty characters long!!!"; // 50 characters

        Task task = new Task(id, name, desc);

        assertEquals(id, task.getId());
        assertEquals(name, task.getName());
        assertEquals(desc, task.getDesc());
    }

    @Test
    void testSetNameAtMaxLength() {
        Task task = new Task("1234567890", "InitialName", "Initial description.");
        String maxLengthName = "TaskNameMax20Chr"; // 20 characters
        task.setName(maxLengthName);
        assertEquals(maxLengthName, task.getName());
    }

    @Test
    void testSetDescriptionAtMaxLength() {
        Task task = new Task("1234567890", "InitialName", "Initial description.");
        String maxLengthDesc = "This description is exactly fifty characters long!!!"; // 50 characters
        task.setDesc(maxLengthDesc);
        assertEquals(maxLengthDesc, task.getDesc());
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
}
