package edu.uwm.cs361;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Student;

public class DeadlineEmailObject {
	
	private Student student;
	
	private Charge charge;

	public DeadlineEmailObject(Student student, Charge charge) {
		this.student = student;
		this.charge = charge;
	}
	
	public Charge getCharge() {
		return charge;
	}
	public Student getStudent() {
		return student;
	}
}
