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
		Charge charge;
		for (User student : students) {
			for (int i = 0; i < classlist.length; i++) {
				String[] date_string = req.getParameter(student.getUser_id() + "_" + classlist[i] + "_deadline").split("-"); // String[month, day, year]
				int[] date_int = new int[date_string.length]; // Int[3]
				for (int k = 0; k < date_string.length; k++) {
					date_int[k] = Integer.parseInt(date_string[k]); // Int[month, day, year]
				}
				Date deadline = new Date(date_int[2], (date_int[0]) - 1, date_int[1]); // Date(year, month-1, day)
				charge = new Charge(Double.parseDouble(req.getParameter(student.getUser_id() + "_"+ classlist[i] + "_charge")), deadline, ""); // Charge(amount, deadline, reason)
				charges[i] = charge;
				student.setCharges(charges);

				int day = charges[i].getDeadline().getDate();
				int month = charges[i].getDeadline().getMonth() + 1;
				int year = charges[i].getDeadline().getYear();
				System.out.println(charges[i].getAmount());
				System.out.println(month + "-" + day + "-" + year);
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
	private List<User> getStudents()
	{
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
	private List<User> setAllStudentCourses()
	{
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