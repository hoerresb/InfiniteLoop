package edu.uwm.cs361;

import java.io.IOException;
import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;

@SuppressWarnings("serial")
public class StudentChargesServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		req.setAttribute("students", getStudents());
		req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<String> errors = new ArrayList<String>();
		
		PersistenceManager pm = getPersistenceManager();
		List<Student> students = getStudents();
		double amount;
		Date deadline;
		String reason;

		try {
			for (Student student : students) {
				String t_amount = req.getParameter(student.getUser_id() + "_add_charge_amount");
				String t_deadline = req.getParameter(student.getUser_id() + "_add_charge_deadline");
				String t_reason = req.getParameter(student.getUser_id() + "_add_charge_reason");
				System.out.println(student.getUser_id());
				if (t_amount.isEmpty()) {
					System.out.println("You must enter amount!");
				}
				if (t_deadline.isEmpty()) {
					System.out.println("You must enter deadline!");
				}
				if (t_reason.isEmpty()) {
					System.out.println("You must enter a reason!");
				}
				if (errors.size() > 0) {
					req.setAttribute(student.getUser_id() + "_add_charge_amount", t_amount);
					req.setAttribute(student.getUser_id() + "_add_charge_deadline", t_deadline);
					req.setAttribute(student.getUser_id() + "_add_charge_reason", t_reason);
					req.setAttribute("errors", errors);
					req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
				} else if (!t_amount.isEmpty() && !t_deadline.isEmpty() && !t_reason.isEmpty()) {
					amount = Double.parseDouble(t_amount);
					deadline = new Date(t_deadline);
					reason = t_reason;
					Charge charge = new Charge(amount, deadline, reason);
					student.getCharges().add(charge);
					pm.makePersistent(student);
					System.out.println(student.getFullName() + " new charge: " + charge.getAmount() + " due " + charge.getDeadline() + " because of the reason " + charge.getReason());
				}
			}	
			if (errors.size() == 0) {
				req.setAttribute("students", getStudents());
				req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
			}
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
			for (Student s : students) {
				s.getCharges();
				s.getCourses();
			}
		} finally {
			pm.close();
		}
		
		return students;
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}