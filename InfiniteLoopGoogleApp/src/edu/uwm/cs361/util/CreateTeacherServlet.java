package edu.uwm.cs361.util;

import java.io.IOException;
import java.util.List;

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
		
//		String instructor_types = req.getParameter("instructor_types");
//		String[] instructor_types_array = null;
//		if(!req.getParameter("instructor_types").equals("null")) {
//			instructor_types_array = instructor_types.split(",");
//		}
		
		createTeacher(username, password, firstname, lastname, email, phonenumber);
	}

	private void createTeacher(String username, String password,
			String firstname, String lastname, String email, String phonenumber) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Teacher(username,password,firstname,lastname,email,phonenumber,null,null,null));
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
