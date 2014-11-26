package edu.uwm.cs361;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.factories.PersistenceFactory;
import edu.uwm.cs361.factories.SpecificCoursePageFactory;

@SuppressWarnings("serial")
public class SpecificCoursePageServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		long id = Long.parseLong(req.getParameter("course_id"));
		try {
			SpecificCoursePageFactory specFact = new SpecificCoursePageFactory();
			Course course = specFact.getCourse(pm, id);
			
			req.setAttribute("course", course);
			req.getRequestDispatcher("specificCourse.jsp").forward(req, resp);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pm.flush();
			pm.close();
		}
	}
}
