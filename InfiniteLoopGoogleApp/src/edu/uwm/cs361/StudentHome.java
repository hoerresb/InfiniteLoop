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

@SuppressWarnings("serial")
public class StudentHome extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
		
		String username = null;

		Cookie[] cookies = req.getCookies();

//		if (cookies != null) {
//			for (Cookie c : cookies) {
//				if (c.getName().equals("Studentname")) {
//					username = c.getValue();
//				}
//			}
//		}
		//test until student login works
		username = "student4";
		req.setAttribute("username", username);
		
		Student student = getStudent(username);		
		req.setAttribute("balance", getBalance(student));
		req.setAttribute("courses", getCourses(student));
		req.setAttribute("teachers", getTeachers(student));
	
		req.getRequestDispatcher("StudentHome.jsp").forward(req, resp);
		
	}
	
	@SuppressWarnings("unchecked")
	private Student getStudent(String username) {
		PersistenceManager pm = getPersistenceManager();
		List<Student> students = new ArrayList<Student>();
		Student student = null;
		try {
			students = (List<Student>) pm.newQuery(Student.class).execute();
			for (Student s : students) {
				if (s.getUsername().equals(username)) {
					student = s;
					student.getCharges(); //black magic that makes student.getCharges() in getBalance(student) not null
					student.getCourses(); //black magic that makes student.getCourses() in getBalance(student) not null
				}
			}
		} finally {
			pm.close();
		}
		return student;
	}
	
	private double getBalance(Student student) {
		PersistenceManager pm = getPersistenceManager();
		double balance = 0;
		try {
			if (student != null) {
				if (student.getCharges() != null) {
					for (Charge charge : student.getCharges()) {
						balance += charge.getAmount();
					}
				}
				if (student.getCourses() != null) {
					for (Course course : student.getCourses()) {
						balance += course.getPayment_amount();
					}
				}
			} else {
				balance = 0;
			}
		} finally {
			pm.close();
		}
		return balance*-1;
	}
	
	private Set<Course> getCourses(Student student) {
		PersistenceManager pm = getPersistenceManager();
		Set<Course> courses = new HashSet<Course>();
		try {
			if (student != null) {
				if (student.getCourses() != null) {
					courses = student.getCourses();
				}
			}
		} finally {
			pm.close();
		}
		return courses;
	}
	
	private Set<Teacher> getTeachers(Student student) {
		PersistenceManager pm = getPersistenceManager();
		Set<Teacher> teachers = new HashSet<Teacher>();
		try {
			if (student != null) {
				if (student.getTeachers() != null) {
					teachers = student.getTeachers();
				}
			}
		} finally {
			pm.close();
		}
		return teachers;
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}