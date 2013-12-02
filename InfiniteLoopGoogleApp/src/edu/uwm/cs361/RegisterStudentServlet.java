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
		req.setAttribute("course_list", getCourses());
		req.setAttribute("teacher_list", getTeachers());
		req.setAttribute("award_list", getAwards());
		req.setAttribute("charge_list", getCharges());
		req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
		PersistenceManager pm = getPersistenceManager();
		
		String username = req.getParameter("student_username");
		String password = req.getParameter("password");
		String password_repeat = req.getParameter("password_repeat");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String phonenumber = (req.getParameter("phonenumber")).equals("null") ? null : req.getParameter("phonenumber");
		
		Set<Course> courses = getSelectedCourses(req);
		Set<Charge> charges = new HashSet<Charge>();
		Date deadline, currentDate = new Date();
		deadline = new Date(currentDate.getYear(),currentDate.getMonth()+1,1);
		for (Course course : courses) {
			charges.add(new Charge(course.getPayment_amount(),deadline,course.getName()));
		}
		Set<Teacher> teachers = getSelectedTeachers(req);
		Set<Award> awards = getSelectedAwards(req);
		
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		Student student = stud_fact.createStudent(username, password, password_repeat, firstname, lastname, email, courses, teachers, awards, charges);
				
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
				req.setAttribute("course_list", getCourses());
				req.setAttribute("teacher_list", getTeachers());
				req.setAttribute("award_list", getAwards());
				req.setAttribute("charge_list", getCharges());
				req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);
			} else {
				pm.makePersistent(student);
				req.setAttribute("course_list", getCourses());
				req.setAttribute("teacher_list", getTeachers());
				req.setAttribute("award_list", getAwards());
				req.setAttribute("charge_list", getCharges());
				req.setAttribute("success", "Student registered successfully.");
				req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);
			}
		} finally {			
			pm.close();
		}
	}
	
	private Set<Course> getSelectedCourses(HttpServletRequest req) {
		PersistenceManager pm = getPersistenceManager();
		String[] course_ids_str = req.getParameterValues("course_opts");
		if(course_ids_str == null) {
			return null;
		}
		Set<Course> courses  =  new HashSet<Course>();
		for (String s : course_ids_str) {
			courses.add(pm.getObjectById(Course.class, Long.parseLong(s)));
		}
		pm.close();
		return courses;
	}
	
	private Set<Teacher> getSelectedTeachers(HttpServletRequest req) {
		PersistenceManager pm = getPersistenceManager();
		String[] teacher_ids_str = req.getParameterValues("teacher_opts");
		if(teacher_ids_str == null) {
			return null;
		}
		Set<Teacher> teachers  =  new HashSet<Teacher>();
		for (String s : teacher_ids_str) {
			teachers.add(pm.getObjectById(Teacher.class, Long.parseLong(s)));
		}
		pm.close();
		return teachers;
	}
	
	private Set<Award> getSelectedAwards(HttpServletRequest req) {
		PersistenceManager pm = getPersistenceManager();
		String[] award_ids_str = req.getParameterValues("award_opts");
		if(award_ids_str == null) {
			return null;
		}
		Set<Award> awards  =  new HashSet<Award>();
		for (String s : award_ids_str) {
			awards.add(pm.getObjectById(Award.class, Long.parseLong(s)));
		}
		pm.close();
		return awards;
	}
	
	private Set<Charge> getSelectedCharges(HttpServletRequest req) {
		PersistenceManager pm = getPersistenceManager();
		String[] charge_ids_str = req.getParameterValues("charge_opts");
		if(charge_ids_str == null) {
			return null;
		}
		Set<Charge> charges  =  new HashSet<Charge>();
		for (String s : charge_ids_str) {
			charges.add(pm.getObjectById(Charge.class, Long.parseLong(s)));
		}
		pm.close();
		return charges;
	}


	private void createStudent(String username, String password,
			String firstname, String lastname, String email,  
			Set<Course> courses, Set<Teacher> teachers, Set<Award> awards, Set<Charge> charges) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Student(username,password,firstname,lastname,email,courses,teachers,awards,charges));
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Course> getCourses()
	{
		PersistenceManager pm = getPersistenceManager();		
		try {
			return (List<Course>) (pm.newQuery(Course.class)).execute();
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Teacher> getTeachers()
	{
		PersistenceManager pm = getPersistenceManager();		
		try {
			return (List<Teacher>) (pm.newQuery(Teacher.class)).execute();
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Award> getAwards()
	{
		PersistenceManager pm = getPersistenceManager();		
		try {
			return (List<Award>) (pm.newQuery(Award.class)).execute();
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Charge> getCharges()
	{
		PersistenceManager pm = getPersistenceManager();		
		try {
			return (List<Charge>) (pm.newQuery(Charge.class)).execute();
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
