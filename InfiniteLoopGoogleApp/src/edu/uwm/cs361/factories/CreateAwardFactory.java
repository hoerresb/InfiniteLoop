package edu.uwm.cs361.factories;

import java.util.*;

import edu.uwm.cs361.entities.*;

public class CreateAwardFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Award createAward(Course course, String name, String description) {
		
		if (name.isEmpty()) {
			errors.add("Please enter an award name.");
		}
		if(description.isEmpty()){
			errors.add("Please enter an award description.");
		}
		if (course == null) {
				errors.add("Please specify a course.");
		} 
		if(sameCourseName(course,name) ==false){
			errors.add("Award with that name already exists");
		}
		
		
		if(hasErrors()) {
			return null;
		} else {
			Award a = new Award(name, description);
			if (course.getAwards() != null) {
				course.getAwards().add(a);
			}
			return a;
		}
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public boolean sameCourseName(Course course, String name){
		
		Set<Award> a = new HashSet<Award>();
		
		if (course != null) {
			a = course.getAwards();
		}
		if (a != null) {
			for(Award aw: a){
				if (aw != null) {
					if(aw.getAwardName().equals(name)){
						return false;
					}
				}
			}
		}
		return true;
	}
}
