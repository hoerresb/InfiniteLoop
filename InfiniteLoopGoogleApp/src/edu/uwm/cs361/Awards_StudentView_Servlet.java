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
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.factories.PersistenceFactory;

@SuppressWarnings("serial")
public class CreateAwardServlet extends HttpServlet {
	private Teacher teacher;
		@Override
		public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
			String username = getUserName(req,resp);

			if(username == null){ resp.sendRedirect("/login.jsp");}
			Student student = getStudent(username);
			req.setAttribute("awards", student.getAwards());
			req.getRequestDispatcher("Awards_StudentView.jsp").forward(req, resp);
		}

		private PersistenceManager getPersistenceManager() {
			return JDOHelper.getPersistenceManagerFactory("transactions-optional")
					.getPersistenceManager();
		}


                @SuppressWarnings("unchecked")
                private Student getStudent(String username) {
                        PersistenceManager pm = getPersistenceManager();
                        List<Student> students = new ArrayList<Student>();
                        Student student = null;
                        try {
                                students = (List<Student>) pm.newQuery(Student.class).execute();
                                for (Student s : students) {
                                        if (s.getUsername().equals(username)) {
                                                student = s;
                                        }
                                }
                        } finally {
                                pm.close();
                        }
                        return student;
                }



        private String getUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {

                String username = null;
                Cookie[] cookies = req.getCookies();
                if(cookies == null){
                        return null;
                } else {
                        for (Cookie c : cookies) {
                                if(c.getName().equals("Studentname")){
                                        return c.getValue();
                                }
                        }
                }
                return username;
        }

}
