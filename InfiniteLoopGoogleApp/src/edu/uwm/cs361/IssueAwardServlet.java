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
public class IssueAwardServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException        {
        
        String username = null;

        Cookie[] cookies = req.getCookies();
        
        if(cookies == null){
                resp.sendRedirect("/login.jsp");
        }
        if (cookies != null) {
        	for (Cookie c : cookies) {

                if(c.getName().equals("Teachername")){
                               username = c.getValue();
                }
                
                if(c.getName().isEmpty()){
                     resp.sendRedirect("/login.jsp");
                }
                               
            }
        }
        
        Teacher teacher = getTeacher(username);                
        
        req.setAttribute("award_courses", getCourses(teacher));
        req.getRequestDispatcher("IssueAward.jsp").forward(req, resp);
    }
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		
		PersistenceManager pm = getPersistenceManager();

		try {
				Cookie c = new Cookie("Coursename", req.getParameter("award_courses"));
                resp.addCookie(c);

			
			resp.sendRedirect("IssueAward2.jsp");
			
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
    private Teacher getTeacher(String username) {
            PersistenceManager pm = getPersistenceManager();
            List<Teacher> teachers = new ArrayList<Teacher>();
            Teacher teacher = null;
            try {
                    teachers = (List<Teacher>) pm.newQuery(Teacher.class).execute();
                    for (Teacher t : teachers) {
                            if (t.getUsername().equals(username)) {
                                    teacher = t;
                            }
                    }
            } finally {
                    pm.close();
            }
            return teacher;
    }
    

    private Set<Course> getCourses(Teacher teacher) {
            PersistenceManager pm = getPersistenceManager();
            Set<Course> courses = new HashSet<Course>();
            try {
                    if (teacher != null) {
                            if (teacher.getCourses() != null) {
                                    courses = teacher.getCourses();
                            }
                    }
            } finally {
                    pm.close();
            }
            return courses;
    }

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
