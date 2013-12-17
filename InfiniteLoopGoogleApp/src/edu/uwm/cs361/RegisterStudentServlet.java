package edu.uwm.cs361;

import java.io.IOException;
import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.*;
import edu.uwm.cs361.factories.*;

@SuppressWarnings("serial")
public class RegisterStudentServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		req.setAttribute("course_list", getCourses(pm));
		req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		
		String username = req.getParameter("student_username");
		String password = req.getParameter("password");
		String password_repeat = req.getParameter("password_repeat");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String phonenumber = (req.getParameter("phonenumber")).equals("null") ? null : req.getParameter("phonenumber");
		
		Set<Course> courses = getSelectedCourses(req, pm);
		
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		Student student = stud_fact.createStudent(username, password, password_repeat, firstname, lastname, email, courses);
		
		
		try {
			if (stud_fact.hasErrors()) {				
				req.setAttribute("firstname", firstname);
				req.setAttribute("lastname", lastname);
				req.setAttribute("email", email);
				req.setAttribute("phonenumber", phonenumber);
				req.setAttribute("student_username", username);
				req.setAttribute("password", password);
				req.setAttribute("password_repeat", password_repeat);
				req.setAttribute("errors", stud_fact.getErrors());
				req.setAttribute("course_list", getCourses(pm));
				req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);
			} else {
				for (Course course : student.getCourses()) {
					Charge charge = new Charge(course.getPayment_amount(),new Date(),course.getName());
					pm.makePersistent(charge);
					student.getCharges().add(charge);//adds initial charge for course fee
				}
				req.setAttribute("course_list", getCourses(pm));
				req.setAttribute("success", "Student registered successfully.");
				pm.makePersistent(student);
				req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);
			}
		} finally {			
			pm.close();
		}
	}
	
	private Set<Course> getSelectedCourses(HttpServletRequest req, PersistenceManager pm) {
		String[] course_ids_str = req.getParameterValues("course_opts");
		if(course_ids_str == null) {
			return null;
		}
		Set<Course> courses  =  new HashSet<Course>();
		for (String s : course_ids_str) {
			courses.add(pm.getObjectById(Course.class, Long.parseLong(s)));
		}
		return courses;
	}
	
	@SuppressWarnings("unchecked")
	private List<Course> getCourses(PersistenceManager pm) {
			return (List<Course>) (pm.newQuery(Course.class)).execute();
	}
}
