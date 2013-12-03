package edu.uwm.cs361;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.PersistanceFactory;

@SuppressWarnings("serial")
public class SpecificStudentPageServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		long id = Long.parseLong(req.getParameter("student_id"));
		try {
			Student student = getStudent(pm, id);
			
			req.setAttribute("studentName", student.getFullName());
			req.setAttribute("balance", getBalance(pm, student));
			req.setAttribute("courses", getCourses(pm, student));
			req.setAttribute("teachers", getTeachers(pm, student));
			req.setAttribute("awards", getAwards(pm, student));
			req.getRequestDispatcher("specificStudent.jsp").forward(req, resp);
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			pm.flush();
			pm.close();
		}
	}

	private Set<Award> getAwards(PersistenceManager pm, Student student) {
		Set<Award> awards = new HashSet<Award>();
		if (student != null) {
			if (student.getAwards() != null) {
				awards = student.getAwards();
			}
		}
		return awards;
	}

	@SuppressWarnings("unchecked")
	private Student getStudent(PersistenceManager pm, long id) {
		List<Student> students = null;

		students = (List<Student>) pm.newQuery(Student.class).execute(); 
		for(Student student : students) {
			if (student.getUser_id().getId() == id) {
				return student;
			}
		}
		return null; 
	}
	
	private double getBalance(PersistenceManager pm, Student student) {
		double balance = 0;

		if (student != null) {
			if (student.getCharges() != null) {
				for (Charge charge : student.getCharges()) {
					balance += charge.getAmount();
				}
			}
		} else {
			balance = 0;
		}
		return balance*-1;
	}
	
	private Set<Course> getCourses(PersistenceManager pm, Student student) {
		Set<Course> courses = new HashSet<Course>();
		if (student != null) {
			if (student.getCourses() != null) {
				courses = student.getCourses();
			}
		}
		return courses;
	}
	
	private Set<Teacher> getTeachers(PersistenceManager pm, Student student) {
		Set<Teacher> teachers = new HashSet<Teacher>();

		if (student != null) {
			if (student.getTeachers() != null) {
				teachers = student.getTeachers();
			}
		}
		return teachers;
	}
}
