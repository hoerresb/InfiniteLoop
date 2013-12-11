package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;


import edu.uwm.cs361.entities.*;

public class IssueAwardFactory {
	private List<String> errors = new ArrayList<String>();
	
	public boolean issueAward( Student student, Award award) {
		
		if (student == null) {
			errors.add("No student selected.");
		}

		if (award == null) {
			errors.add("Please select an award.");
		}
		if(student != null && award != null){
			for(Award a : student.getAwards()){
				if(a.getAward_id().getId() == award.getAward_id().getId())
					errors.add(student.getFullName() + "already has the award: " + award.getAwardName());
			}
		}
		
		if(hasErrors()) {
			return false;
		} 
		else {
			student.getAwards().add(award);
			return true;
		}
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
