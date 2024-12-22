package cs320mod4;

import java.util.Objects;
import java.util.Scanner;


public class Task {
	
	private String id; // No longer than 10 chars, non-updateable
	private String Name; // No longer than 20 chars
	private String Desc; // No longer than 50 chars
	
	public Task(String id, String Name, String Desc) {

		this.id = validId(id);
		this.Name = validName(Name);
		this.Desc = validDesc(Desc);
	}
	
	private String validId(String id) {
        Objects.requireNonNull(id, "ID cannot be null");
        if (id.length() > 10) {
            throw new IllegalArgumentException("ID cannot be longer than 10 characters");
        }
        return id;
    }
	
	private String validName(String name) {
        Objects.requireNonNull(name, " cannot be null");
        if (name.length() > 20) {
            throw new IllegalArgumentException("Name cannot be longer than 20 characters");
        }
        return name;
    }
	
	private String validDesc(String Desc) {
        Objects.requireNonNull(Desc," cannot be null");
        if (Desc.length() > 50) {
            throw new IllegalArgumentException("Description cannot be longer than 50 characters");
        }
        return Desc;
    }
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return Name;
	}
	
	public String getDesc() {
		return Desc;
	}
	
	public void setName(String Name) {
		this.Name = validName(Name);
	}
	
	public void setDesc(String Desc) {
		this.Desc = validDesc(Desc);
	}
	
	public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
			Task task = null;

			while (task == null) {
			    try {
			        // Prompt user for input
			        System.out.print("Enter ID (max 10 chars): ");
			        String id = scanner.nextLine();
			        System.out.print("Enter task name (max 20 chars): ");
			        String Name = scanner.nextLine();
			        System.out.print("Enter task description (max 50 chars): ");
			        String Desc = scanner.nextLine();
			       

			        // Create Task object
			        task = new Task(id, Name, Desc);
			    } catch (IllegalArgumentException e) {
			        // Catch validation errors and prompt user again
			        System.out.println("Error: " + e.getMessage());
			        System.out.println("Please try again.");
			    }
			}

			// If successful, display contact details
			System.out.println("Task created successfully!");
			System.out.println("ID: " + task.getId());
			System.out.println("First Name: " + task.getName());
			System.out.println("Last Name: " + task.getDesc());
		}
    }
}
