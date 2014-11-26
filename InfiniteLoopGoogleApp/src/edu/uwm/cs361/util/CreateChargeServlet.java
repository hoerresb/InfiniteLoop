package edu.uwm.cs361.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Student;

@SuppressWarnings("serial")
public class CreateChargeServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setAttribute("student_list", getStudents());
		req.getRequestDispatcher("/util/createCharge.jsp").forward(req, resp);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		Double amount = Double.parseDouble(req.getParameter("amount"));
		Date deadline = new Date(req.getParameter("deadline"));
		String reason = req.getParameter("reason");
	
		Charge c = createCharge(amount,deadline,reason);
		getSelectedStudents(req, c);
		resp.sendRedirect("/display");
	}
	
	private void getSelectedStudents(HttpServletRequest req, Charge c) {
		PersistenceManager pm = getPersistenceManager();
		String[] student_ids_str = req.getParameterValues("student_list");
		Set<Student> students  =  new HashSet<Student>();
		for (String s : student_ids_str) {
			students.add(pm.getObjectById(Student.class, Long.parseLong(s)));
		}
		for(Student s : students) {
			s.getCharges().add(c);
		}
	}
	
	private Charge createCharge(Double amount, Date deadline, String reason) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Charge c = new Charge(amount,deadline,reason);
			pm.makePersistent(c);
			return c;
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Student> getStudents()
	{
		PersistenceManager pm = getPersistenceManager();	
		try {
			return (List<Student>) (pm.newQuery(Student.class)).execute();
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
