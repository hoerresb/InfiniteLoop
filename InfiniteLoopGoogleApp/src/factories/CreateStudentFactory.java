package factories;

import java.util.ArrayList;
import java.util.List;

import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;

public class CreateStudentFactory {
	private List<String> errors = new ArrayList<String>();
	
	public User createStudent(String username, String password, String password_repeat,
			String firstname, String lastname, String email,
			String phonenumber, String[] student_charges) {
		if (username.isEmpty()) {
			errors.add("Username is required.");
		}
		if (password.isEmpty()) {
			errors.add("Password is required.");
		} else if (!password.equals(password_repeat)) {
			errors.add("Passwords do not match.");
		}
		if (phonenumber.isEmpty()) {
			errors.add("Phone Number is required.");
		}
		if (email.isEmpty()) {
			errors.add("Email is required.");
		}
		
		if(hasErrors()) {
			return null;
		} else {
			return new User(UserConstants.STUDENT_NUM,username,password,firstname,lastname,email,phonenumber,student_charges);
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
