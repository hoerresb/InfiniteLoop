package edu.uwm.cs361.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;

@SuppressWarnings("serial")
public class CreateStudentServlet  extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setAttribute("course_list", getCourses());
		req.setAttribute("teacher_list", getTeachers());
		req.setAttribute("award_list", getAwards());
		req.setAttribute("charge_list", getCharges());
		req.getRequestDispatcher("/util/createStudent.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");

		Set<Course> courses = getSelectedCourses(req);
		Set<Teacher> teachers = getSelectedTeachers(req);
		Set<Award> awards = getSelectedAwards(req);
		Set<Charge> charges = getSelectedCharges(req);
		
		createStudent(username, password, firstname, lastname, email, courses, teachers, awards, charges);
		resp.sendRedirect("/display");
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
