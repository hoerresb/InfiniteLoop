package factories;

import java.util.ArrayList;
import java.util.List;

import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;

public class StudentChargesFactory {
	private List<String> errors = new ArrayList<String>();
	private List<User> students = new ArrayList<User>();
	private User testStudent = new User(,UserConstants.STUDENT_NUM, "student", "student",
				"Student_fn", "Student_ln", "student@student.com",
				"111-111-1111");
	
	public List<User> getStudents() {
		return new ArrayList<User>(students);
	}
	
	public boolean hasStudents() {
		return !students.isEmpty();
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
