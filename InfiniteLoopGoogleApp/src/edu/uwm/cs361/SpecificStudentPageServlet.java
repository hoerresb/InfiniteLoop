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

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.PersistanceFactory;

@SuppressWarnings("serial")
public class SpecificStudentPageServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		long id = (long) req.getAttribute("student_id");
		Student student = getStudent(id);
		
		req.setAttribute("balance", getBalance(student));
		req.setAttribute("courses", getCourses(student));
		req.setAttribute("teachers", getTeachers(student));
		req.getRequestDispatcher("specificStudent.jsp").forward(req, resp);
	}

	@SuppressWarnings("unchecked")
	private Student getStudent(long id) {
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		List<Student> students = null;
		try {
			students = (List<Student>) pm.newQuery(Student.class).execute(); 
		} catch(Exception e) {
			e.getStackTrace();
		} finally {
			pm.close();
		}
		
		for(Student student : students) {
			if (student.getUser_id().getId() == id) {
				return student;
			}
		}
		
		return null; 
	}
	
	private double getBalance(Student student) {
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		double balance = 0;
		try {
			if (student != null) {
				if (student.getCharges() != null) {
					for (Charge charge : student.getCharges()) {
						balance += charge.getAmount();
					}
				}
			} else {
				balance = 0;
			}
		} finally {
			pm.close();
		}
		return balance*-1;
	}
	
	private Set<Course> getCourses(Student student) {
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		Set<Course> courses = new HashSet<Course>();
		try {
			if (student != null) {
				if (student.getCourses() != null) {
					courses = student.getCourses();
				}
			}
		} finally {
			pm.close();
		}
		return courses;
	}
	
	private Set<Teacher> getTeachers(Student student) {
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		Set<Teacher> teachers = new HashSet<Teacher>();
		try {
			if (student != null) {
				if (student.getTeachers() != null) {
					teachers = student.getTeachers();
				}
			}
		} finally {
			pm.close();
		}
		return teachers;
	}
}
