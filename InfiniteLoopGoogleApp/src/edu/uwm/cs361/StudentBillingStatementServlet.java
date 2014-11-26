package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.factories.BillingStatementFactory;
import edu.uwm.cs361.factories.PersistenceFactory;
import edu.uwm.cs361.factories.SpecificStudentPageFactory;
import edu.uwm.cs361.factories.StudentBillingStatementFactory;

@SuppressWarnings("serial")
public class StudentBillingStatementServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		String username = null;

		Cookie[] cookies = req.getCookies();
		
		if(cookies == null){
			resp.sendRedirect("/login.jsp");
		}
		if (cookies != null) {
			for (Cookie c : cookies) {

		 if(c.getName().equals("Studentname")){
				username = c.getValue();
			}
		 if(c.getName().equals("Adminname")){
			Cookie admin = new Cookie("Adminname", null);
			admin.setMaxAge(0);
			resp.addCookie(admin);
			resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().equals("Teachername")){
				Cookie teacher = new Cookie("Teachername", null);
				teacher.setMaxAge(0);
				resp.addCookie(teacher);
				resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().isEmpty()){
					resp.sendRedirect("/login.jsp");
				}
				
			}
		}	
		

		try {
			StudentBillingStatementFactory billFact = new StudentBillingStatementFactory();
			Student student = billFact.getStudent(pm, username);
			req.setAttribute("studentName", student.getFullName());
			req.setAttribute("balance", billFact.getBalance(pm, student));
			req.setAttribute("charges", billFact.getCharges(pm, student));
			req.getRequestDispatcher("studentBillingStatement.jsp").forward(req, resp);
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			pm.flush();
			pm.close();
		}
	}
}
