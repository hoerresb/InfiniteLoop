package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;

import edu.uwm.cs361.UsernameValidator;
import edu.uwm.cs361.entities.Teacher;

public class CreateInstructorFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Teacher createInstructor(String username, String password, String password_repeat,
			String firstname, String lastname, String email,
			String phonenumber, String[] instructor_types) {
		if (username == null || username.isEmpty()) {
			errors.add("Username is required.");
		} else if(UsernameValidator.usernameExists(username)) {
			errors.add("Username is already taken.");
		}
		if (password == null || password.isEmpty()) {
			errors.add("Password is required.");
		} else if (!password.equals(password_repeat)) {
			errors.add("Passwords do not match.");
		}
		if (phonenumber == null || phonenumber.isEmpty()) {
			errors.add("Phone Number is required.");
		}
		if (email == null || email.isEmpty()) {
			errors.add("Email is required.");
		}
		
		if(hasErrors()) {
			return null;
		} else {
			return new Teacher(username,password,firstname,lastname,email,phonenumber,instructor_types);
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
