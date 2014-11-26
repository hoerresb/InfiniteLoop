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
public class ViewAttendance extends HttpServlet {
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		
		String username = null;

		Cookie[] cookies = req.getCookies();
		
		if (cookies != null) {
			for (Cookie c : cookies) {

		 if(c.getName().equals("Teachername")){
				username = c.getValue();
			}
		 if(c.getName().equals("Adminname")){
			Cookie admin = new Cookie("Adminname", null);
			admin.setMaxAge(0);
			resp.addCookie(admin);
			resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().equals("Studentname")){
				Cookie student = new Cookie("Studentname", null);
				student.setMaxAge(0);
				resp.addCookie(student);
				resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().isEmpty()){
					resp.sendRedirect("/login.jsp");
				}
				
			}
		}
				
		req.setAttribute("username", username);
		
		Teacher teacher = getTeacher(username);		
		
		req.getAttribute("course_id");
		
		req.setAttribute("courses", getCourses(teacher));
		req.setAttribute("students", getStudents(teacher));
		
		
		
		req.getRequestDispatcher("ViewAttendance.jsp").forward(req, resp);
		
	}
	








@SuppressWarnings("unchecked")
private Teacher getTeacher(String username) {
	PersistenceManager pm = getPersistenceManager();
	List<Teacher> teachers = new ArrayList<Teacher>();
	Teacher teacher = null;
	try {
		teachers = (List<Teacher>) pm.newQuery(Teacher.class).execute();
		for (Teacher t : teachers) {
			if (t.getUsername().equals(username)) {
				teacher = t;
			
				teacher.getCourses(); 
			}
		}
	} finally {
		pm.close();
	}
	return teacher;
}


private Set<Course> getCourses(Teacher teacher) {
	PersistenceManager pm = getPersistenceManager();
	Set<Course> courses = new HashSet<Course>();
	try {
		if (teacher != null) {
			if (teacher.getCourses() != null) {
				courses = teacher.getCourses();
			}
		}
	} finally {
		pm.close();
	}
	return courses;
}

private Set<Student> getStudents(Teacher teacher) {
	PersistenceManager pm = getPersistenceManager();
	Set<Student> students = new HashSet<Student>();
	try {
		if (teacher != null) {
			if (teacher.getStudents() != null) {
				students = teacher.getStudents();
			}
		}
	} finally {
		pm.close();
	}
	return students;
}

private int getDiffInDate(Date date1, Date date2){
	
	final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	int diffInDays = (int) ((date2.getTime() - date1.getTime())/ DAY_IN_MILLIS );
	return (int) Math.ceil((double)diffInDays/7);
}
private PersistenceManager getPersistenceManager() {
	return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
}
}