package edu.uwm.cs361;
import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.factories.PersistenceFactory;


@SuppressWarnings("serial")
public class IssueAwardServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		Course course = (Course) pm.getObjectId(Long.parseLong((String)req.getAttribute("course_id")));
		System.out.println(course.getName());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		//resp.sendRedirect("/login.jsp");
	}
}
