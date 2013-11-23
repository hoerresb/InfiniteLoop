package edu.uwm.cs361;

import java.io.IOException;
import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.*;

@SuppressWarnings("serial")
public class AdminHome extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException	{
		
		String username = null;

		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("Adminname")) {
					username = c.getValue();
				}
			}
		}

		req.setAttribute("username", username);
		req.setAttribute("balance", getBalance());
		req.getRequestDispatcher("AdminHome.jsp").forward(req, resp);
		
	}
	
	@SuppressWarnings("unchecked")
	private double getBalance() {
		PersistenceManager pm = getPersistenceManager();
		List<Student> students = new ArrayList<Student>();
		double balance = 0;
		try {
			students = (List<Student>) pm.newQuery(Student.class).execute();
			for (Student student : students) {
				for (Charge charge : student.getCharges()) {
					balance += charge.getAmount();
				}
				for (Course course : student.getCourses()) {
					balance += course.getPayment_amount();
				}
			}
		} finally {
			pm.close();
		}
		return balance*-1;
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}