package edu.uwm.cs361.factories;

import java.util.*;

import edu.uwm.cs361.entities.*;

public class StudentChargesFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Charge createCharge(Student student, double amount, Date deadline, String reason) {
		if (amount == 0) {
			errors.add("Please enter an amount.");
		}
		if (deadline == null) {
			errors.add("Please enter a date.");
		}
		if (reason.isEmpty()) {
			errors.add("Please enter a reason.");
		}
		if(hasErrors()) {
			return null;
		} else {
			Charge c = new Charge(amount, deadline, reason);
			return c;
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
