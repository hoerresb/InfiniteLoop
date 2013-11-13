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
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;
import factories.CourseFactory;
import factories.InstructorFactory;


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
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(req.getParameterValues("meeting_times"))); 
		String time = req.getParameter("time");
		String place = req.getParameter("place");
		String description = req.getParameter("class_description");
		//TODO: payment options
		
		
		//@SuppressWarnings("unused")
		//User teacher = (User) pm.getObjectById(req.getParameter("instr_options")); //update teacher

		CourseFactory course_fact = new CourseFactory();
		//Course course = course_fact.createCourse(classname, startDate, endDate, meetingDays, time, place, payment_options, description);
		Course course = course_fact.createCourse(classname, startDate, endDate, meetingDays, time, place, null, description);
		try {
			if (course_fact.hasErrors()) {
				req.setAttribute("classname", classname);
				req.setAttribute("classstart", startDate);
				req.setAttribute("classend", endDate);
				req.setAttribute("time", time);
				req.setAttribute("place", place);
				req.setAttribute("meeting_times", meetingDays);
				req.setAttribute("class_description", description);
				//req.setAttribute("meeting_times", payment_options);
				//req.setAttribute("meeting_times", teacher); can do?
				req.setAttribute("errors", course_fact.getErrors());
				req.getRequestDispatcher("/createCourse.jsp").forward(req, resp);
			} else {
				pm.makePersistent(course);
				resp.sendRedirect("/displayCourses");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<User> getTeachers()
	{
		PersistenceManager pm = getPersistenceManager();
		List<User> teachers = new ArrayList<User>();
		
		try {
			Query query = pm.newQuery(User.class);
			query.setFilter("user_type == " + UserConstants.TEACHER_NUM);
			teachers = (List<User>) query.execute();
			return teachers;
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}