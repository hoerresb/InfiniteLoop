package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateAwardFactory;
import edu.uwm.cs361.factories.PersistenceFactory;

@SuppressWarnings("serial")
public class CreateAwardServlet extends HttpServlet {
	private Teacher teacher;
		@Override
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
			String username = null;

			Cookie[] cookies = req.getCookies();

			if (cookies == null) {
				resp.sendRedirect("/login.jsp");
			}
			if (cookies != null) {
				for (Cookie c : cookies) {

					if (c.getName().equals("Teachername")) {
						username = c.getValue();
					}

					if (c.getName().isEmpty()) {
						resp.sendRedirect("/login.jsp");
					}

				}
			}

			teacher = getTeacher(username);
			req.setAttribute("courses", getCourses(teacher));
			req.getRequestDispatcher("CreateAward.jsp").forward(req, resp);
		}
		@Override
		public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
			PersistenceManager pm = PersistenceFactory.getPersistenceManager();
			String awardName = req.getParameter("award_name");
			String awardDescription = req.getParameter("award_description");
			Course course = (Course) pm.getObjectById(Course.class,Long.parseLong(req.getParameter("courses")));
			CreateAwardFactory fact = new CreateAwardFactory();
			Award award = fact.createAward(course, awardName, awardDescription);
			try {
				if (fact.hasErrors()) {
					req.setAttribute("award_name", awardName);
					req.setAttribute("award_description", awardDescription);
					req.setAttribute("errors", fact.getErrors());
					req.getRequestDispatcher("CreateAward.jsp").forward(req, resp);
				} 
				else {
					pm.makePersistent(award);
					req.setAttribute("success", "Award created successfully.");
					req.setAttribute("courses",  getCourses(teacher));
					req.getRequestDispatcher("CreateAward.jsp").forward(req, resp);
				}
			} catch (ServletException e) {
				e.printStackTrace();
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
						teacher.getCourses();
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
			return JDOHelper.getPersistenceManagerFactory("transactions-optional")
					.getPersistenceManager();
		}

}
