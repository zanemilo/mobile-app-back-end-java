package cs320mod3;

import java.util.Objects;
import java.util.Scanner;

public class Contact {
	
	private String id; // No longer than 10 chars, non-updateable
	private String firstName; // No longer than 10 chars
	private String lastName; // No longer than 10 chars
	private String Phone; // No more or less than 10 chars, must be exact
	private String Address; // No more than 30 chars
	
	public Contact(String id, String firstName, String lastName, String Phone, 	String Address) {
		
		this.id = validId(id);
		this.firstName = validName(firstName, "First name");
		this.lastName = validName(lastName, "Last name");
		this.Phone = validPhone(Phone);
		this.Address = validAddress(Address);
	}
	
	
	private String validId(String id) {
        Objects.requireNonNull(id, "ID cannot be null");
        if (id.length() > 10) {
            throw new IllegalArgumentException("ID cannot be longer than 10 characters");
        }
        return id;
    }
	
	private String validName(String name, String fieldName) {
        Objects.requireNonNull(name, fieldName + " cannot be null");
        if (name.length() > 10) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than 10 characters");
        }
        return name;
    }
	
	private String validAddress(String address) {
        Objects.requireNonNull(address, "Address cannot be null");
        if (address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be longer than 30 characters");
        }
        return address;
    }
	
	private String validPhone(String phone) {
        Objects.requireNonNull(phone, "Phone cannot be null");
        if (phone.length() != 10 || !phone.matches("\\d+")) {  // regex, checking if phone is all numbers
            throw new IllegalArgumentException("Phone must be exactly 10 digits");
        }
        return phone;
    }
	
	public String getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
        this.firstName = validName(firstName, "First Name");
    }
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
        this.lastName = validName(lastName, "Last Name");
    }
	
	public String getPhone() {
		return Phone;
	}
	
	public void setPhone(String Phone) {
        this.Phone = validPhone(Phone);
    }
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String Address) {
        this.Address = validAddress(Address);
    }
	
	public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
			Contact contact = null;

			while (contact == null) {
			    try {
			        // Prompt user for input
			        System.out.print("Enter ID (max 10 chars): ");
			        String id = scanner.nextLine();
			        System.out.print("Enter First Name (max 10 chars): ");
			        String firstName = scanner.nextLine();
			        System.out.print("Enter Last Name (max 10 chars): ");
			        String lastName = scanner.nextLine();
			        System.out.print("Enter Phone (exactly 10 digits): ");
			        String phone = scanner.nextLine();
			        System.out.print("Enter Address (max 30 chars): ");
			        String address = scanner.nextLine();

			        // Create Contact object
			        contact = new Contact(id, firstName, lastName, phone, address);
			    } catch (IllegalArgumentException e) {
			        // Catch validation errors and prompt user again
			        System.out.println("Error: " + e.getMessage());
			        System.out.println("Please try again.");
			    }
			}

			// If successful, display contact details
			System.out.println("Contact created successfully!");
			System.out.println("ID: " + contact.getId());
			System.out.println("First Name: " + contact.getFirstName());
			System.out.println("Last Name: " + contact.getLastName());
			System.out.println("Phone: " + contact.getPhone());
			System.out.println("Address: " + contact.getAddress());
		}
    }

}


