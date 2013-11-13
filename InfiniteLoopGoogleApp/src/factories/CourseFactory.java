package factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.util.UserConstants;

public class CourseFactory {
	private List<String> errors = new ArrayList<String>();
	
	public Course createCourse(String classname, String startDate, String endDate, 
			Set<String> meetingDays, String time, String place, Set<String> payment_options, String description) {
		
		if (classname.isEmpty()) {
			errors.add("Please enter a class name.");
		}
		if (startDate.isEmpty()) {
			errors.add("Please enter a class start date.");
		}
		if (endDate.isEmpty()) {
			errors.add("Please enter a class end Time.");
		}
		if (meetingDays.isEmpty()) {
			errors.add("Please select meeting days.");
		}
		if (time.isEmpty()) {
			errors.add("Please enter a meeting time.");
		}
		if (place.isEmpty()) {
			errors.add("Please enter a class meeting place.");
		}
		if (description.isEmpty()) {
			errors.add("Please enter a class description..");
		}
		
		if(hasErrors()) {
			return null;
		} else {
			return new Course(classname, startDate, endDate, meetingDays, time, place, payment_options, description);
		}
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
