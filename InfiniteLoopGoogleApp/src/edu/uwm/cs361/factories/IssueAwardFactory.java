package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.uwm.cs361.entities.*;

public class IssueAwardFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Award createAward(Course course, String name, String description) {
		
		if (name.isEmpty()) {
			errors.add("Please enter an award name.");
		}
		if (description != null) {
			if (description.isEmpty()) {
				errors.add("Please enter an award description.");
			}
		} else {
			errors.add("Please enter an award description.");
		}
		
		if(hasErrors()) {
			return null;
		} else {
			Award a = new Award(name, description);
			course.getAwards().add(a);
			return a;
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
