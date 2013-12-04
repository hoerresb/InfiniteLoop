package edu.uwm.cs361.factories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;

public class SpecificStudentPageFactory {
	public Set<Award> getAwards(PersistenceManager pm, Student student) {
		Set<Award> awards = new HashSet<Award>();
		if (student != null) {
			if (student.getAwards() != null) {
				awards = student.getAwards();
			}
		}
		return awards;
	}

	@SuppressWarnings("unchecked")
	public Student getStudent(PersistenceManager pm, long id) {
		List<Student> students = null;

		students = (List<Student>) pm.newQuery(Student.class).execute(); 
		for(Student student : students) {
			if (student.getUser_id().getId() == id) {
				return student;
			}
		}
		return null; 
	}
	
	public double getBalance(PersistenceManager pm, Student student) {
		double balance = 0;

		if (student != null) {
			if (student.getCharges() != null) {
				for (Charge charge : student.getCharges()) {
					balance += charge.getAmount();
				}
			}
		} else {
			balance = 0;
		}
		return balance*-1;
	}
	
	public Set<Course> getCourses(PersistenceManager pm, Student student) {
		Set<Course> courses = new HashSet<Course>();
		if (student != null) {
			if (student.getCourses() != null) {
				courses = student.getCourses();
			}
		}
		return courses;
	}
	
	public Set<Teacher> getTeachers(PersistenceManager pm, Student student) {
		Set<Teacher> teachers = new HashSet<Teacher>();

		if (student != null) {
			if (student.getTeachers() != null) {
				teachers = student.getTeachers();
			}
		}
		return teachers;
	}
}
