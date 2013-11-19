package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;

import edu.uwm.cs361.entities.Student;

public class StudentChargesFactory {
	private List<String> errors = new ArrayList<String>();
	private List<Student> students = new ArrayList<Student>();
	
	public List<Student> getStudents() {
		return new ArrayList<Student>(students);
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
