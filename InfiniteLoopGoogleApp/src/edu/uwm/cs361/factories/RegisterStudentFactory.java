package edu.uwm.cs361.factories;

import java.util.*;

import edu.uwm.cs361.UsernameValidator;
import edu.uwm.cs361.entities.*;

public class RegisterStudentFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Student createStudent(String username, String password, String password_repeat,
			String firstname, String lastname, String email, Set<Course> courses) {
		if (username.isEmpty()) {
			errors.add("Username is required.");
		} else if(UsernameValidator.usernameExists(username)) {
			errors.add("Username is already taken.");
		}
		if (password.isEmpty()) {
			errors.add("Password is required.");
		} else if (!password.equals(password_repeat)) {
			errors.add("Passwords do not match.");
		}
		if (email.isEmpty()) {
			errors.add("Email is required.");
		}
		
		if(hasErrors()) {
			return null;
		} else {
			Student student = new Student(username,password,firstname,lastname,email);
			for(Course c : courses) {
				c.getStudents().add(student);
				student.getCourses().add(c);
				student.getCharges().add(new Charge(c.getPayment_amount(),new Date(),c.getName()));//adds initial charge for course fee
				Teacher teacher = c.getTeacher();
				student.addTeacher(teacher);
				teacher.getStudents().add(student);
			}
			return student;
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
