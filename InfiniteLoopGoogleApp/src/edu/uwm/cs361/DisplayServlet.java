package edu.uwm.cs361;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.User;

@SuppressWarnings("serial")
public class DisplayServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");

		PersistenceManager pm = getPersistenceManager();

		try {
			displayUsers(req, resp, pm);
			displayCourses(req, resp, pm);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void displayCourses(HttpServletRequest req,
			HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Courses</h1>");

		List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();

		if (courses.size() == 0) {
			resp.getWriter().println("<p>There are no courses.</p>");
		} else {
			resp.getWriter().println("<ul>");

			for (Course course : courses) {
				resp.getWriter().println("<li>(" + course.getCourse_id().getId() + "): " + course.getName() +
						 "<br/>Meeting days: " + course.getMeetingDays() + "</li>");
			}

			resp.getWriter().println("</ul>");
		}
		
	}

	@SuppressWarnings("unchecked")
	private void displayUsers(HttpServletRequest req, HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Users</h1>");

		List<User> users = (List<User>) pm.newQuery(User.class).execute();

		if (users.size() == 0) {
			resp.getWriter().println("<p>There are no users.</p>");
		} else {
			resp.getWriter().println("<ul>");

			for (User user : users) {
				String row = "";
				String userType = "";
				switch(user.getUser_type()) {
					case 0: userType = "admin";
							break;
					case 1: userType = "teacher";
							break;
					case 2: userType = "student";
							break;
				}
				row += "<li>(" + user.getUser_id().getId() + "):"+ userType +":"+ user.getFullName() + 
						 "<br/>Courses: "; 
				Set<Course> courses = user.getCourses();
				for(Course c : courses) {
					row += "Name: " + c.getName() + " Days: " + c.getMeetingDays();
				}
				row += "</li>";
				resp.getWriter().println(row);
			}

			resp.getWriter().println("</ul>");
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
