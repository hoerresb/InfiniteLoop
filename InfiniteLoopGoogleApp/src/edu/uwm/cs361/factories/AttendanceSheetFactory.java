package edu.uwm.cs361.factories;

import java.util.*;

import edu.uwm.cs361.entities.*;

public class AttendanceSheetFactory {
	private List<String> errors = new ArrayList<String>();
	
	public StudentAttendance createAttendanceSheet(List<String> daysPresent, int date, String studentName, Set<String> meetDays, Course course) {
		if (daysPresent.isEmpty()) {
			errors.add("Days present is required.");
		}
		if (date != (int)date) {
			errors.add("Date is required.");
		}
		if (studentName.isEmpty()) {
			errors.add("Student's name is required.");
		}
		if(meetDays.isEmpty()){
			errors.add("Meeting Days are required.");
		}
		if(course.getName().isEmpty()){
			errors.add("Course is required.");
		}
		if(!(sameWeekSubmit(date, course, studentName))){
			errors.add("Already Attendance for this week.");
		}
		
		if(hasErrors()) {
			return null;
		}
		else{
			StudentAttendance attendance = new StudentAttendance(daysPresent, date, studentName, meetDays);
			return attendance;
		}
		
		}
		
	
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public boolean sameWeekSubmit(int date, Course course, String name){
		List<StudentAttendance> att = course.getAttendance();
		
		for(int i = 0 ; i < att.size(); i++){
			if(att.get(i).getDate() == date && att.get(i).getStudentName().equals(name)	){
				return false;
			}
		}
		return true;
		
	}
}