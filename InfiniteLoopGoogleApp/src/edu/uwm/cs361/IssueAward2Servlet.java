package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.entities.Course;
@SuppressWarnings("serial")
public class IssueAward2Servlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException        {
        
        String courseid = null;

        Cookie[] cookies = req.getCookies();
        
        if(cookies == null){
                resp.sendRedirect("/login.jsp");
        }
        if (cookies != null) {
        	for (Cookie c : cookies) {

                if(c.getName().equals("Coursename")){
                               courseid = c.getValue();
                }
                
                if(c.getName().isEmpty()){
                     resp.sendRedirect("/login.jsp");
                }
                               
            }
        }
        
        Course course = getCourse(courseid);
        
        req.setAttribute("award_students", getStudents());

        req.setAttribute("course_awards", getAwards(course));
        req.getRequestDispatcher("IssueAward2.jsp").forward(req, resp);
    }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		
		PersistenceManager pm = getPersistenceManager();

		try {
			
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
    private List<Student> getStudents() {
            PersistenceManager pm = getPersistenceManager();
            List<Student> students = new ArrayList<Student>();
            try {
                    students = (List<Student>) pm.newQuery(Student.class).execute();
            } finally {
                    pm.close();
            }
            return students;
    }
	
	@SuppressWarnings("unchecked")
    private Course getCourse(String courseid) {
            PersistenceManager pm = getPersistenceManager();
            List<Course> courses = new ArrayList<Course>();
            Course course = null;
            try {
                    courses = (List<Course>) pm.newQuery(Course.class).execute();
                    long courseId = Long.parseLong(courseid);
                    for (Course c : courses) {
                            if (c.getCourse_id().getId() == courseId) {
                                    course = c;
                            }
                    }
            } finally {
                    pm.close();
            }
            return course;
    }
	
	   private Set<Award> getAwards(Course course) {
           PersistenceManager pm = getPersistenceManager();
           Set<Award> awards = new HashSet<Award>();
           try {
                   if (course != null) {
                           if (course.getAwards() != null) {
                                   awards = course.getAwards();
                           }
                   }
           } finally {
                   pm.close();
           }
           return awards;
   }
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
