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
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.factories.IssueAwardFactory;
import edu.uwm.cs361.factories.PersistenceFactory;


@SuppressWarnings("serial")
public class IssueAwardServlet extends HttpServlet {
	private Set<Course> courses;
	private Set<Student> students;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		String username = getUsernameFromCookies(req, resp);
		Teacher teacher;
		
		try {
			teacher = getTeacher(pm, username);
			courses = getCourses(teacher);
			students = getStudents(teacher);
			List<Award> awards = (List<Award>) (pm.newQuery(Award.class)).execute();
			req.setAttribute("courses", courses);
			req.setAttribute("students", students);
			req.setAttribute("awards", awards);
		} finally {
			pm.close();
		}
		req.getRequestDispatcher("issueAward.jsp").forward(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		String awardID = req.getParameter("award_options");
		String studentID = req.getParameter("student_options");
		String courseID = req.getParameter("course_options");
		Course course = (Course) pm.getObjectById(Course.class,Long.parseLong(courseID));
		Student student = (Student) pm.getObjectById(Student.class,Long.parseLong(studentID));
		Award award = (Award) pm.getObjectById(Award.class,Long.parseLong(awardID));

		IssueAwardFactory fact = new IssueAwardFactory();
		boolean issued = fact.issueAward(student, award);
		try {
			
				boolean validStudent = false;
				for(Student s : course.getStudents()){
					if(s.getUser_id().getId() == student.getUser_id().getId()) 
						validStudent = true;
				}
				boolean validAward = false;
				for(Award a : course.getAwards()){
					if(a.getAward_id().getId() == award.getAward_id().getId() ) 
						validAward = true;
				}
				
				if(validStudent && validAward)
					req.setAttribute("success", "Award given successfully.");
				else
					req.setAttribute("success",  "Failed to give award.  Try again.");
				
					req.setAttribute("course_options", courses);
					req.setAttribute("student_options", students);
					req.setAttribute("award_options", (List<Award>)pm.newQuery(Award.class).execute());
					req.getRequestDispatcher("IssueAward.jsp").forward(req, resp);
				
			
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
	}
	

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

	private Set<Course> getCourses(Teacher teacher) {
		Set<Course> courses = null;
			if (teacher != null) {
				if (teacher.getCourses() != null) {
					courses = teacher.getCourses();
				}
			}
		return courses;
	}
	

	private Set<Student> getStudents(Teacher teacher) {
		Set<Student> students = null;
			if (teacher != null) {
				if (teacher.getStudents() != null) {
					students = teacher.getStudents();
				}
			}
		return students;
	}
}
