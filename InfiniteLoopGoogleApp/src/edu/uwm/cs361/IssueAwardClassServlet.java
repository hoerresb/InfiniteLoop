package edu.uwm.cs361;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.PersistenceFactory;

@SuppressWarnings("serial")
public class IssueAwardClassServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		String username = getUsernameFromCookies(req, resp);
		Teacher teacher;
		try {
			teacher = getTeacher(pm, username);
			req.setAttribute("courses", getCourses(pm, teacher));
		} finally {
			pm.close();
		}
		req.getRequestDispatcher("issueAwardClass.jsp").forward(req, resp);
	}
	
//	@Override
//	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
//		Long course_id = Long.parseLong(req.getParameter("course_options"));
//		req.setAttribute("course_id", course_id);
//		req.getRequestDispatcher("/issueAward").forward(req, resp);
//	}


	@SuppressWarnings("unchecked")
	private Teacher getTeacher(PersistenceManager pm, String username) {
		Query q = pm.newQuery(Teacher.class);
    	q.setFilter("username == nameParameter");
		q.declareParameters("String nameParameter");
		List<Teacher> teacher = (List<Teacher>) q.execute(username);
		return teacher.get(0);
	}

	private String getUsernameFromCookies(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = null;
		Cookie[] cookies = req.getCookies();
		if(cookies == null){
			resp.sendRedirect("/login.jsp");
		} else {
			for (Cookie c : cookies) {
				if(c.getName().equals("Teachername")){
					username = c.getValue();
				}
				if(c.getName().isEmpty()){
					resp.sendRedirect("/login.jsp");
				}
			}
		}
		return username;
	}

	@SuppressWarnings("unchecked")
	private List<Course> getCourses(PersistenceManager pm, Teacher teacher) {
		List<Course> courses = (List<Course>) (pm.newQuery(Course.class)).execute();
		for(Course c : courses) {
			if(c.getTeacher().getUsername() != teacher.getUsername()) {
				courses.remove(c);
			}
		}
		return courses;
	}
}
