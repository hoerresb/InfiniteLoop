package edu.uwm.cs361.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Student extends User
{
	@Persistent
	@Unowned
	private Set<Teacher> teachers = new HashSet<Teacher>();
	
	@Persistent
	@Unowned
	private Set<Course> courses = new HashSet<Course>();
	
	@Persistent
	@Unowned
	private Set<Award> awards = new HashSet<Award>();
	
	@Persistent
	@Unowned
	private Set<Charge> charges = new HashSet<Charge>();

	public Student(String username, String password, String firstname, String lastname, String email) {
		super(username, password, firstname, lastname, email);
	}

	public Student (String username, String password, String firstname, String lastname, 
					String email, Set<Course> courses, Set<Teacher> teachers, Set<Award> awards, Set<Charge> charges) {
		super(username, password, firstname, lastname, email);	
		this.courses = courses;
		this.teachers = teachers;
		this.awards = awards;
		this.charges = charges;
	}

	public Student(String username, String password, String firstname,
			String lastname, String email, Set<Course> student_courses) {
		super(username, password, firstname, lastname, email);	
		this.courses = student_courses;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public Set<Award> getAwards() {
		return awards;
	}

	public Set<Key> getTeacherKeys() {
		Set<Key> teacher_keys = new HashSet<Key>();
		for(Teacher teacher : teachers) {
			teacher_keys.add(teacher.getUser_id());
		}
		return teacher_keys;
	}

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}
	
	
	public Set<Charge> getCharges() {
		return charges;
	}
	
	public Double getBalance() {
		Double balance = 0.0;
		for(Charge c : charges) {
			balance += c.getAmount();
		}
		return balance;
	}
}
