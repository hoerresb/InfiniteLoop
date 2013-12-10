package edu.uwm.cs361;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course; 
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateCourseFactory;
import edu.uwm.cs361.factories.PersistenceFactory;


@SuppressWarnings("serial")
public class CreateClassServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setAttribute("teachers", getTeachers());
		req.getRequestDispatcher("createClass.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		String[] daysOfWeek = new String[] {"M","T","W","Th","F","S","Su"};
		
		String classname = req.getParameter("classname");
		String startDate = req.getParameter("classstart");
		String endDate = req.getParameter("classend");
		String[] meeting_time_days = req.getParameterValues("meeting_times");
		Set<String> meetingDays = null;
		if(meeting_time_days != null) {
			meetingDays = new HashSet<String>(Arrays.asList(meeting_time_days)); 
		}
		
		Map<String, Boolean> days = populateSelectedDays(meetingDays, daysOfWeek);
		
		String time = req.getParameter("time");
		String place = req.getParameter("place");
		String description = req.getParameter("class_description");

		String payment_value = req.getParameter("payment_value");
		String payment_duration = req.getParameter("payment_duration");
		String payment_option = payment_value + " per " + payment_duration;
		String teacher = req.getParameter("instr_options");

		CreateCourseFactory course_fact = new CreateCourseFactory();
		try {
			
			Course course = course_fact.createCourse(pm, classname, startDate, endDate, meetingDays, time, place, payment_option, description,teacher);
			
			if (course_fact.hasErrors()) {
				req.setAttribute("classname", classname);
				req.setAttribute("classstart", startDate);
				req.setAttribute("classend", endDate);
				req.setAttribute("time", time);
				req.setAttribute("place", place);
				req.setAttribute("meeting_times", meetingDays);
				req.setAttribute("class_description", description);
				req.setAttribute("payment_value", payment_value);
				req.setAttribute("payment_duration", payment_duration);
				req.setAttribute("errors", course_fact.getErrors());
				req.setAttribute("teachers", getTeachers());
				req.setAttribute("days", days);
				req.getRequestDispatcher("createClass.jsp").forward(req, resp);
			} else {
				pm.makePersistent(course);
				req.setAttribute("success", "Class created successfully.");
				req.setAttribute("teachers", getTeachers());
				req.getRequestDispatcher("createClass.jsp").forward(req, resp);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	private Map<String, Boolean> populateSelectedDays(Set<String> meetingDays, String[] daysOfWeek) {
		Map<String, Boolean> days = new HashMap<String, Boolean>();
		for(String day : daysOfWeek) {
			if(wasSelected(day,meetingDays)) {
				days.put(day, true);
			} else {
				days.put(day, false);
			}
		}
		return days;
	}

	private boolean wasSelected(String day, Set<String> meetingDays) {
		for(String selectedDay : meetingDays) {
			if(selectedDay.equals(day)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private List<Teacher> getTeachers() {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		try {
			return (List<Teacher>) pm.newQuery(Teacher.class).execute();
		} finally {
			pm.close();
		}
	}
}