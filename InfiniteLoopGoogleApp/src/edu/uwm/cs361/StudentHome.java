package edu.uwm.cs361;

import java.io.IOException;
import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.*;
import edu.uwm.cs361.factories.PersistenceFactory;

@SuppressWarnings("serial")
public class StudentHome extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		
		String username = null;

		Cookie[] cookies = req.getCookies();
		
		if(cookies == null){
			resp.sendRedirect("/login.jsp");
		}
		if (cookies != null) {
			for (Cookie c : cookies) {

		 if(c.getName().equals("Studentname")){
				username = c.getValue();
			}
		 if(c.getName().equals("Adminname")){
			Cookie admin = new Cookie("Adminname", null);
			admin.setMaxAge(0);
			resp.addCookie(admin);
			resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().equals("Teachername")){
				Cookie teacher = new Cookie("Teachername", null);
				teacher.setMaxAge(0);
				resp.addCookie(teacher);
				resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().isEmpty()){
					resp.sendRedirect("/login.jsp");
				}
				
			}
		}
		
		req.setAttribute("username", username);
		Student student = getStudent(pm, username);		
		req.setAttribute("balance", getBalance(pm, student));
		req.setAttribute("courses", getCourses(pm, student));
	
		req.getRequestDispatcher("StudentHome.jsp").forward(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	private Student getStudent(PersistenceManager pm, String username) {
		List<Student> students = new ArrayList<Student>();
		Student student = null;
		students = (List<Student>) pm.newQuery(Student.class).execute();
		for (Student s : students) {
			if (s.getUsername().equals(username)) {
				student = s;
				student.getCharges(); //black magic that makes student.getCharges() in getBalance(student) not null
				student.getCourses(); //black magic that makes student.getCourses() in getBalance(student) not null
			}
		}
		return student;
	}
	
	private double getBalance(PersistenceManager pm, Student student) {
		double balance = 0;
		if (student != null) {
			if (student.getCharges() != null) {
				for (Charge charge : student.getCharges()) {
					balance += charge.getAmount();
				}
			}
		} else {
			balance = 0;
		}
		return balance*-1;
	}
	
	private Set<Course> getCourses(PersistenceManager pm, Student student) {
		Set<Course> courses = new HashSet<Course>();
		if (student != null) {
			if (student.getCourses() != null) {
				courses = student.getCourses();
			}
		}
		return courses;
	}
}