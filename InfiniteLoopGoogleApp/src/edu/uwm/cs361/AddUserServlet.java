package edu.uwm.cs361;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;

@SuppressWarnings("serial")
public class AddUserServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");

		resp.getWriter().println("<form action='/addUser' method='POST'>");
		
			resp.getWriter().println("<select id='customer' name='userType' />");
				resp.getWriter().println("<option value='"+ UserConstants.ADMIN_NUM +"'>Admin</option>");
				resp.getWriter().println("<option value='"+ UserConstants.TEACHER_NUM +"'>Teacher</option>");
				resp.getWriter().println("<option value='"+ UserConstants.STUDENT_NUM +"'>Student</option>");
			resp.getWriter().println("</select><br/><br/>");
			
			resp.getWriter().println("<label for='username'>Username: </label>");
			resp.getWriter().println("<input type='text' id='username' name='username' /><br/><br/>");
			
			resp.getWriter().println("<label for='password'>Password: </label>");
			resp.getWriter().println("<input type='text' id='password' name='password' /><br/><br/>");
			
			resp.getWriter().println("<label for='firstname'>First Name: </label>");
			resp.getWriter().println("<input type='text' id='firstname' name='firstname' /><br/><br/>");
			
			resp.getWriter().println("<label for='laststname'>Last Name: </label>");
			resp.getWriter().println("<input type='text' id='lastname' name='lastname' /><br/><br/>");
			
			resp.getWriter().println("<label for='email'>Email: </label>");
			resp.getWriter().println("<input type='text' id='email' name='email' /><br/><br/>");
			
			resp.getWriter().println("<label for='phonenumber'>Phone Number: </label>");
			resp.getWriter().println("<input type='text' id='phonenumber' name='phonenumber' /><br/><br/>");
			
			resp.getWriter().println("<label for='instructor_types'>Instructor Types: </label>");
			resp.getWriter().println("<input type='text' id='instructor_types' name='instructor_types' placeholder='Ex: Dog Trainer, Tutor ...'/><br/><br/>");
			
			resp.getWriter().println("<label for='student_courses'>Student Courses: </label>");
			resp.getWriter().println("<input type='text' id='student_courses' name='student_courses' placeholder='Ex: Cooking for Dummies ...'/><br/><br/>");
			
			resp.getWriter().println("<input type='submit' value='Create User' />");
		resp.getWriter().println("</form>");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		int userType = Integer.parseInt(req.getParameter("userType"));
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
		
		String student_courses = req.getParameter("student_courses");
		String[] student_courses_str_array = null;
		Course[] student_courses_array;
		if(!req.getParameter("student_courses").equals("null")) {
			student_courses_str_array = student_courses.split(",");
		}
		
		student_courses_array = new Course[student_courses_str_array.length];
		
		PersistenceManager pm = getPersistenceManager();
		
		//List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
		
		Course[] courses = {new Course("Cookings for Dummies", null, null, null, "", "", null, "")};

		try {
		// loops through already made courses and checks if the entered courses exist
		for (int i=0; i<student_courses_str_array.length; i++) {
			for (Course course: courses) {
				if (course.getName().equals(student_courses_str_array[i])) {
					student_courses_array[i] = course;
					System.out.println("Course exists");
				}
			}
		}
		
			if (instructor_types_array.length > 0) {
				pm.makePersistent(new User(userType,username,password,firstname,lastname,email,phonenumber,instructor_types_array));
			} else {
				pm.makePersistent(new User(student_courses_array,userType,username,password,firstname,lastname,email,phonenumber));
			}
			resp.sendRedirect("/display");
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}