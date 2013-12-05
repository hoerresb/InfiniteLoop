package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.factories.PersistenceFactory;
import edu.uwm.cs361.factories.SpecificStudentPageFactory;

@SuppressWarnings("serial")
public class SpecificStudentPageServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		long id = Long.parseLong(req.getParameter("student_id"));
		try {
			SpecificStudentPageFactory specFact = new SpecificStudentPageFactory();
			Student student = specFact.getStudent(pm, id);
			
			req.setAttribute("studentName", student.getFullName());
			req.setAttribute("balance", specFact.getBalance(pm, student));
			req.setAttribute("courses", specFact.getCourses(pm, student));
			req.setAttribute("teachers", specFact.getTeachers(pm, student));
			req.setAttribute("awards", specFact.getAwards(pm, student));
			req.getRequestDispatcher("specificStudent.jsp").forward(req, resp);
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			pm.flush();
			pm.close();
		}
	}
}
