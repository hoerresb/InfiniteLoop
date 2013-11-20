package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course; 
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateCourseFactory;


@SuppressWarnings("serial")
public class CreateClassServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setAttribute("teachers", getTeachers());
		req.getRequestDispatcher("createClass.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = getPersistenceManager();
		
		String classname = req.getParameter("classname");
		String startDate = req.getParameter("classstart");
		String endDate = req.getParameter("classend");
		String[] meeting_time_days = req.getParameterValues("meeting_times");
		Set<String> meetingDays = null;
		if(meeting_time_days != null) {
			meetingDays = new HashSet<String>(Arrays.asList(meeting_time_days)); 
		}
		String time = req.getParameter("time");
		String place = req.getParameter("place");
		String description = req.getParameter("class_description");

		String payment_value = req.getParameter("payment_value");
		String payment_duration = req.getParameter("payment_duration");
		String payment_option = payment_value + " per " + payment_duration;

		
		Teacher teacher = (Teacher) pm.getObjectById(Teacher.class,Long.parseLong(req.getParameter("instr_options"))); //update teacher

		CreateCourseFactory course_fact = new CreateCourseFactory();
		Course course = course_fact.createCourse(classname, startDate, endDate, meetingDays, time, place, payment_option, description,teacher);
		try {
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
				req.getRequestDispatcher("createClass.jsp").forward(req, resp);
			} else {
				pm.makePersistent(course);
//				teacher.getCourses().add(course);
//				pm.makePersistent(teacher);
				resp.sendRedirect("/display");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Teacher> getTeachers() {
		PersistenceManager pm = getPersistenceManager();
		try {
			return (List<Teacher>) pm.newQuery(Teacher.class).execute();
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}