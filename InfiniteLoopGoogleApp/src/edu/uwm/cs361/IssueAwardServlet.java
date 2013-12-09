package edu.uwm.cs361;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.factories.IssueAwardFactory;
import edu.uwm.cs361.factories.PersistenceFactory;



@SuppressWarnings("serial")
public class IssueAwardServlet extends HttpServlet {
	private Course course;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		try {
			course = (Course) pm.getObjectId(Long.parseLong((String)req.getAttribute("course_id")));
			System.out.println(course.getName());
			req.setAttribute("student_options", course.getStudents());
			req.setAttribute("award_options", course.getAwards());
		} finally {
			pm.close();
		}
		req.getRequestDispatcher("IssueAward.jsp").forward(req, resp);		
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		try{
			String awardID = req.getParameter("award_options");
			String studentID = req.getParameter("student_options");
			Student student = (Student) pm.getObjectById(Student.class,Long.parseLong(studentID));
			Award award = (Award) pm.getObjectById(Award.class,Long.parseLong(awardID));

			IssueAwardFactory fact = new IssueAwardFactory();
			boolean issued = fact.issueAward(student, award);
			if(issued)
				req.setAttribute("success", "Award given successfully.");
			else
				req.setAttribute("errors", fact.getErrors());
					
			req.setAttribute("student_options", course.getStudents());
			req.setAttribute("award_options", course.getAwards());
			req.getRequestDispatcher("IssueAward.jsp").forward(req, resp);
			resp.sendRedirect("/IssueAward.jsp");
			
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
	}
}

