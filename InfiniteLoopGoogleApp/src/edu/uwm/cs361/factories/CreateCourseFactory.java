package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Teacher;

public class CreateCourseFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Course createCourse(String classname, String startDate, String endDate, 
			Set<String> meetingDays, String time, String place, String payment_option, String description, Teacher teacher) {
		
		if (classname.isEmpty()) {
			errors.add("Please enter a class name.");
		}
		if (startDate.isEmpty()) {
			errors.add("Please enter a class start date.");
		}
		if (endDate.isEmpty()) {
			errors.add("Please enter a class end date.");
		}
		if (meetingDays == null) {
			errors.add("Must select a meeting day.");
		}
		if(payment_option.isEmpty()) {
			errors.add("Please enter a payment option.");
		}
		if (time.isEmpty()) {
			errors.add("Please enter a meeting time.");
		}
		if (place.isEmpty()) {
			errors.add("Please enter a class meeting place.");
		}
		if (description.isEmpty()) {
			errors.add("Please enter a class description.");
		}
		
		if(hasErrors()) {
			return null;
		} else {
			Course c = new Course(classname, startDate, endDate, meetingDays, time, place, payment_option, description, teacher);
			teacher.getCourses().add(c);
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
