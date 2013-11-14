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
import factories.CreateCourseFactory;
import factories.CreateInstructorFactory;


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

		String payment_options = req.getParameter("payment_options");
		String[] payment_options_array = null;
		if(!req.getParameter("payment_options").equals("null")) {
			payment_options_array = payment_options.split(",");
		}
		
		User teacher = (User) pm.getObjectById(User.class,Long.parseLong(req.getParameter("instr_options"))); //update teacher

		CreateCourseFactory course_fact = new CreateCourseFactory();
		Course course = course_fact.createCourse(classname, startDate, endDate, meetingDays, time, place, new HashSet<String>(Arrays.asList(payment_options_array)), description);
		try {
			if (course_fact.hasErrors()) {
				req.setAttribute("classname", classname);
				req.setAttribute("classstart", startDate);
				req.setAttribute("classend", endDate);
				req.setAttribute("time", time);
				req.setAttribute("place", place);
				req.setAttribute("meeting_times", meetingDays);
				req.setAttribute("class_description", description);
				req.setAttribute("payment_options", payment_options);
				//req.setAttribute("teacher", teacher); can do?
				req.setAttribute("errors", course_fact.getErrors());
				req.setAttribute("teachers", getTeachers());
				req.getRequestDispatcher("createClass.jsp").forward(req, resp);
			} else {
				pm.makePersistent(course);
				teacher.getCourses().add(course);
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