package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;

public class StudentBillingStatementFactory {

	@SuppressWarnings("unchecked")
	public Student getStudent(PersistenceManager pm, String username) {
		List<Student> students = new ArrayList<Student>();
		Student student = null;
		students = (List<Student>) pm.newQuery(Student.class).execute();
		for (Student s : students) {
			if (s.getUsername().equals(username)) {
				student = s;
				student.getCharges(); //black magic that makes student.getCharges() in getBalance(student) not null
				student.getCourses(); //black magic that makes student.getCourses() in getBalance(student) not null
			}
		}
		return student;
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
	
	public Set<Charge> getCharges(PersistenceManager pm, Student student) {
		Set<Charge> charges = new HashSet<Charge>();
		if (student != null) {
			if (student.getCharges() != null) {
				charges = student.getCharges();
			}
		}
		return charges;
	}
}
