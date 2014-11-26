package edu.uwm.cs361.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Teacher extends User {
	@Persistent
	private String phoneNumber;
	
	@Persistent
	private Set<String> instructor_types;
	
	@Persistent
	@Unowned
	private Set<Course> courses = new HashSet<Course>();
	
	@Persistent
	@Unowned
	private Set<Student> students = new HashSet<Student>();

	public Teacher(String username, String password, String firstname, String lastname, String email) {
		super(username, password, firstname, lastname, email);	
	}
	
	@SuppressWarnings("unchecked")
	public Teacher(String username, String password, String firstname, String lastname, String email, 
					String phonenumber, String[] instructor_types, Set<Course> courses, Set<Student> students) {
		super(username, password, firstname, lastname, email);	
		this.phoneNumber = phonenumber;
		this.instructor_types = (Set<String>) Arrays.asList(instructor_types);
		this.courses = courses;
		this.students = students;
	}

	@SuppressWarnings("unchecked")
	public Teacher(String username, String password, String firstname,
			String lastname, String email, String phonenumber,
			String[] instructor_types) {
		super(username, password, firstname, lastname, email);
		this.phoneNumber = phonenumber;
		this.instructor_types = new HashSet<String>(Arrays.asList(instructor_types));
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public Set<String> getInstructor_types() {
		return instructor_types;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public Set<Key> getStudentKeys() {
		Set<Key> student_keys = new HashSet<Key>();
		for(Student student : students) {
			student_keys.add(student.getUser_id());
		}
		return student_keys;
	}

	public void addStudent(Student student) {
		students.add(student);
	}
}