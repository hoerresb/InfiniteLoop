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

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;

@SuppressWarnings("serial")
public class CreateTeacherServlet  extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setAttribute("student_list", getStudents());
		req.setAttribute("course_list", getCourses());
		req.getRequestDispatcher("/util/createTeacher.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String phonenumber = (req.getParameter("phonenumber")).equals("null") ? null : req.getParameter("phonenumber");
		
		String instructor_types = req.getParameter("instructor_types");
		String[] instructor_types_array = null;
		if(!req.getParameter("instructor_types").equals("null")) {
			instructor_types_array = instructor_types.split(",");
		}
		
		Set<Course> courses = getSelectedCourses(req);
		Set<Student> students = getSelectedStudents(req);
		
		createTeacher(username, password, firstname, lastname, email, phonenumber, instructor_types_array, courses, students);
		resp.sendRedirect("/display");
	}

	private Set<Course> getSelectedCourses(HttpServletRequest req) {
		PersistenceManager pm = getPersistenceManager();
		String[] course_ids_str = req.getParameterValues("course_opts");
		Set<Course> courses  =  new HashSet<Course>();
		for (String s : course_ids_str) {
			courses.add(pm.getObjectById(Course.class, Long.parseLong(s)));
		}
		return courses;
	}
	
	private Set<Student> getSelectedStudents(HttpServletRequest req) {
		PersistenceManager pm = getPersistenceManager();
		String[] student_ids_str = req.getParameterValues("student_opts");
		Set<Student> students  =  new HashSet<Student>();
		for (String s : student_ids_str) {
			students.add(pm.getObjectById(Student.class, Long.parseLong(s)));
		}
		return students;
	}

	private void createTeacher(String username, String password,
			String firstname, String lastname, String email, 
			String phonenumber, String[] instructor_types, 
			Set<Course> courses, Set<Student> students) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Teacher(username,password,firstname,lastname,email,phonenumber,instructor_types,courses,students));
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Student> getStudents()
	{
		PersistenceManager pm = getPersistenceManager();	
		try {
			return (List<Student>) (pm.newQuery(Student.class)).execute();
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
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
