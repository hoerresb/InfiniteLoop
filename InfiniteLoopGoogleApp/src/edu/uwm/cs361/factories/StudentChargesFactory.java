package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;

import edu.uwm.cs361.entities.old_User;

public class StudentChargesFactory {
	private List<String> errors = new ArrayList<String>();
	private List<old_User> students = new ArrayList<old_User>();
	
	public List<old_User> getStudents() {
		return new ArrayList<old_User>(students);
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
