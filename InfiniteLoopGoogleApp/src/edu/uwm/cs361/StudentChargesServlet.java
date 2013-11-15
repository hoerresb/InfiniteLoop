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
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;

@SuppressWarnings("serial")
public class StudentChargesServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		req.setAttribute("students", setAllStudentCourses());
		req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		List<String> errors = new ArrayList<String>();
	
		String[] classlist = { "Cooking For Dummies", "Kung Fu", "Dog Training" };
		Charge[] charges = new Charge[classlist.length];

		PersistenceManager pm = getPersistenceManager();
		List<User> students = getStudents();
		
		for (User student : students) {
			for (Charge charge : student.getCharges()) {
				double amount = Double.parseDouble(req.getParameter(student.getUser_id() + "_charge"));
				String[] date_string = req.getParameter(student.getUser_id() + "_deadline").split("-");
				int[] date_int = new int[date_string.length]; // Int[3]
				for (int i=0; i<date_string.length; i++) {
					date_int[i] = Integer.parseInt(date_string[i]); // Int[month, day, year]
				}
				Date deadline = new Date(date_int[2], (date_int[0]) - 1, date_int[1]); // Date(year, month-1, day)
				charge = new Charge (amount,deadline,"");
			}
		}
		
		try {
			if (errors.size() > 0) {
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("studentCharges.jsp").forward(req,resp);
			} else {
				for (User student : students) {
					pm.makePersistent(student);
					System.out.println(student.getFullName() + ": ");
					for (Charge c : student.getCharges()) {
						System.out.println("    " + c.getAmount() + " due: "
								+ c.getDeadline());
					}
				}
				resp.sendRedirect("studentCharges.jsp");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<User> getStudents() {
		PersistenceManager pm = getPersistenceManager();
		List<User> students = new ArrayList<User>();
		
		try {
			Query query = pm.newQuery(User.class);
			query.setFilter("user_type == " + UserConstants.STUDENT_NUM);
			students = (List<User>) query.execute();
			return students;
		} finally {
			pm.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<User> setAllStudentCourses() {
		PersistenceManager pm = getPersistenceManager();
		List<User> students = new ArrayList<User>();
		Set<String> meetingDays = new HashSet<String>();
		Set<String> payment_options = new HashSet<String>();
		double[] amount = new double[1];
		meetingDays.add("M");
		meetingDays.add("W");
		payment_options.add("30");
		for (String s_amount : payment_options) {
			amount[0] = Double.parseDouble(s_amount);
		}
		Course course = new Course("Kung Fu", "9-3-2013", "12-25-2013", meetingDays, "5:30PM", "Kung-Shi's Dojo", payment_options, "Kick stuff");
		Charge charge = new Charge(amount[0],new Date(2013,11,31),"");
		try {
			Query query = pm.newQuery(User.class);
			query.setFilter("user_type == " + UserConstants.STUDENT_NUM);
			students = (List<User>) query.execute();
			for (User student : students) {
				if (student.getCourses().size() == 0) {
					student.getCourses().add(course);
					student.getCharges().add(charge);
				}
				for (Course current : student.getCourses()) {
					if (current == course) {
						break;
					} else {
						student.getCourses().add(course);
						student.getCharges().add(charge);						
					}
				}
			}
			return students;
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}