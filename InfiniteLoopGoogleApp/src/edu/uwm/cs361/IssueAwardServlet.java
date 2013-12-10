package edu.uwm.cs361;
import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
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
			course = (Course) pm.getObjectById(Course.class, Long.parseLong((String)req.getParameter("course_id")));
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
			//get student id's, parse id's, add students to array list
			String[] studentIDs = req.getParameterValues("student_options");
			ArrayList<Student> students = new ArrayList<Student>();
			for(String s : studentIDs){
				students.add((Student) pm.getObjectById(Student.class,Long.parseLong(s)));
			}
			//get award id's, parse id's, add awards to array list
			String[] awardIDs = req.getParameterValues("award_options");
			ArrayList<Award> awards = new ArrayList<Award>();
			for(String s : awardIDs){
				awards.add((Award) pm.getObjectById(Award.class,Long.parseLong(s)));
			}

			IssueAwardFactory fact;
			boolean success = true;//overall success
			boolean issued;        //success of individual attempts to give an award 
			for(Student s : students){//loop through students and awards, giving each award to each student
				for(Award a : awards){
					fact = new IssueAwardFactory();
					issued = fact.issueAward(s, a);
					if(!issued){
						success = false;
						req.setAttribute("errors",  fact.getErrors());
					}
				}
			}
			
			
			if(success)
				req.setAttribute("success", "Award(s) given successfully.");
			else
				req.setAttribute("success",  "Sorry, one or more attempts to give an award failed");
					
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
