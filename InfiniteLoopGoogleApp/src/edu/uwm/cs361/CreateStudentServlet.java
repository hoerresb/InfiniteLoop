package edu.uwm.cs361;

import java.io.IOException;
import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.*;
import factories.CreateStudentFactory;

@SuppressWarnings("serial")
public class CreateStudentServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = getPersistenceManager();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String password_repeat = req.getParameter("password_repeat");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String phonenumber = (req.getParameter("phonenumber")).equals("null") ? null : req.getParameter("phonenumber");
		
		String student_courses = req.getParameter("student_courses");
		String[] student_courses_array = null;
		if(!req.getParameter("student_courses").equals("null")) {
			student_courses_array = student_courses.split(",");
		}
		Set<Course> courses = new HashSet<Course>();
		for (int i=0; i<student_courses_array.length; i++) {
			for (Course existing_course : getExistingCourses()) {
				if (existing_course.getName().equals(student_courses_array[i])) {
					courses.add(existing_course);
				}
			}
		}
		
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		User student = stud_fact.createStudent(username, password, password_repeat, firstname, lastname, email, phonenumber, courses);

		try {
			if (stud_fact.hasErrors()) {
				req.setAttribute("firstname", firstname);
				req.setAttribute("lastname", lastname);
				req.setAttribute("email", email);
				req.setAttribute("phonenumber", phonenumber);
				req.setAttribute("username", username);
				req.setAttribute("password", password);
				req.setAttribute("password_repeat", password_repeat);
				req.setAttribute("student_courses", student_courses);
				req.setAttribute("errors", stud_fact.getErrors());
				req.getRequestDispatcher("/createStudent.jsp").forward(req, resp);
			} else {
				pm.makePersistent(student);
				resp.sendRedirect("/display");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}
	
	private Set<Course> getExistingCourses() {
		PersistenceManager pm = getPersistenceManager();
		Set<Course> existing_courses = new HashSet<Course>();
		
		try {
			Query query = pm.newQuery(Course.class);
			existing_courses = (Set<Course>) query.execute();
			return existing_courses;
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
