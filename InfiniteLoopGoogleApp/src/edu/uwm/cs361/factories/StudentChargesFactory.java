package edu.uwm.cs361.factories;

import java.util.*;

import edu.uwm.cs361.entities.*;

public class StudentChargesFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Charge createCharge(Student student, String s_amount, Date deadline, String reason) {
		Double amount = 0.0;
		if (s_amount != null && s_amount != "") {
			amount = Double.parseDouble(s_amount);
		} else {
			errors.add("Please enter an amount.");
		}
		if (deadline == null) {
			errors.add("Please enter a date.");
		}
		if (reason.isEmpty() || reason == "") {
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
