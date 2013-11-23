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
			throws IOException {
		List<String> errors = new ArrayList<String>();
	
		String[] classlist = { "Cooking For Dummies", "Kung Fu", "Dog Training" };
		Charge[] charges = new Charge[classlist.length];

		PersistenceManager pm = getPersistenceManager();
		List<Student> students = getStudents();
		
		for (Student student : students) {
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
				for (Student student : students) {
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

	@SuppressWarnings("unchecked")
	private List<Student> setAllStudentCourses() {
		PersistenceManager pm = getPersistenceManager();
		List<Student> students = new ArrayList<Student>();
		Set<String> meetingDays = new HashSet<String>();
		String payment_option;
		double amount;
		meetingDays.add("M");
		meetingDays.add("W");
		payment_option = "30";
		amount = Double.parseDouble(payment_option);
		
		Course course = new Course("Kung Fu", "9-3-2013", "12-25-2013", meetingDays, "5:30PM", "Kung-Shi's Dojo", payment_option, "Kick stuff");
		Charge charge = new Charge(amount,new Date(2013,11,31),"");
		try {
			Query query = pm.newQuery(Student.class);
			students = (List<Student>) query.execute();
			for (Student student : students) {
				if (student.getCourses().size() == 0) {
					for (Course current : student.getCourses()) {
						if (current != course) {
							student.getCourses().add(course);
							student.getCharges().add(charge);						
						}
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