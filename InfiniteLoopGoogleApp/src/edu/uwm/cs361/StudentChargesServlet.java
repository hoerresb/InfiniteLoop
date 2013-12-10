package edu.uwm.cs361;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.jdo.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.*;
import edu.uwm.cs361.factories.*;

@SuppressWarnings("serial")
public class StudentChargesServlet extends HttpServlet {

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"MM/dd/yyyy");

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		
		try {
			req.setAttribute("students", getStudents(pm));
		} finally {
			pm.close();
		}
		req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<String> errors = new ArrayList<String>();

		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		try{
			List<Student> students = getStudents(pm);
			Date currentDate = new Date();
			String reason, s_amount;
			StudentChargesFactory charge_fact = new StudentChargesFactory();
			Charge c;

				Student student = (Student) pm.getObjectById(Student.class,Long.parseLong(req.getParameter("student_id")));
				s_amount = req.getParameter(student.getUser_id() + "_add_charge_amount");
				reason = req.getParameter(student.getUser_id() + "_add_charge_reason");
				c = charge_fact.createCharge(student, s_amount, currentDate, reason);

				if (charge_fact.hasErrors()) {
					req.setAttribute(student.getUser_id() + "_add_charge_amount", s_amount);
					req.setAttribute(student.getUser_id() + "_add_charge_reason", reason);
					req.setAttribute("errors", charge_fact.getErrors());
					req.getRequestDispatcher("StudentCharges.jsp").forward(req, resp);
				} else {
					pm.makePersistent(c);
					student.getCharges().add(c);
					pm.flush();

					req.setAttribute("success", "Charge added successfully.");
					req.setAttribute("students", getStudents(pm));
					req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
				}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Student> getStudents(PersistenceManager pm) {
		List<Student> students = new ArrayList<Student>();

		students = (List<Student>) pm.newQuery(Student.class).execute();
		for (Student s : students) {
			s.getCharges();
			s.getCourses();
		}

		return students;
	}
}