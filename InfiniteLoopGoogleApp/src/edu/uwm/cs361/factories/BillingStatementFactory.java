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

public class BillingStatementFactory {

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
