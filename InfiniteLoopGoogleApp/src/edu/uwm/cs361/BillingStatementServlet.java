package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.factories.BillingStatementFactory;
import edu.uwm.cs361.factories.PersistenceFactory;
import edu.uwm.cs361.factories.SpecificStudentPageFactory;

@SuppressWarnings("serial")
public class BillingStatementServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		long id = Long.parseLong(req.getParameter("student_id"));
		try {
			BillingStatementFactory billFact = new BillingStatementFactory();
			Student student = billFact.getStudent(pm, id);
			
			req.setAttribute("studentName", student.getFullName());
			req.setAttribute("balance", billFact.getBalance(pm, student));
			req.setAttribute("charges", billFact.getCharges(pm, student));
			req.getRequestDispatcher("billingStatement.jsp").forward(req, resp);
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			pm.flush();
			pm.close();
		}
	}
}
