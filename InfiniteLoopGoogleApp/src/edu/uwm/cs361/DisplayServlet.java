package edu.uwm.cs361;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Admin;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.PersistanceFactory;

@SuppressWarnings("serial")
public class DisplayServlet extends HttpServlet
{
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("MM/dd/yyyy");
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		try {
			//displayAdmins(req, resp, pm);
			//displayTeachers(req, resp, pm);
			displayStudents(req, resp, pm);
		    //displayCourses(req, resp, pm);
			//displayCharges(req, resp, pm);
			//EmailService.SendDeadlineEmails();
		} catch(Exception e){
			e.getStackTrace();
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void displayCharges(HttpServletRequest req, HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Charges</h1>");
		List<Charge> charges = (List<Charge>) pm.newQuery(Charge.class).execute();
		if (charges != null) {
			if (charges.size() == 0) {
				resp.getWriter().println("<p>There are no charges.</p>");
			} else {
				resp.getWriter().println("<ul>");
				for (Charge charge : charges) {
					String row = "";
					row += "<li>(" + charge.getCharge_id().getId() + ")<br/>Amount: "+ charge.getAmount() 
							+ "<br/>Deadline: " + charge.getDeadline()
							+ "<br/>Reason: " + charge.getReason() + "<br/>";
					row += "</li>";
					resp.getWriter().println(row);
				}
				resp.getWriter().println("</ul>");
			}
		} else {
			resp.getWriter().println("<p>There are no charges.</p>");
		}
	}

	@SuppressWarnings("unchecked")
	private void displayStudents(HttpServletRequest req, HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Students</h1>");
		List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
		if (students.size() == 0) {
			resp.getWriter().println("<p>There are no students.</p>");
		} else {
			resp.getWriter().println("<ul>");
			for (Student student : students) {
				String row = "";
				row += "<li>(<a href='/specificStudent?student_id=" + student.getUser_id().getId() + "'>" + 
						student.getUser_id().getId() + "</a>)"+
						"<br/>Name: "+ student.getFullName() + 
						"<br/>Username: " + student.getUsername() +
						"<br/>password: " + student.getPassword() + 
						"<br/>email: " + student.getEmail() + 
						"<br/>";
				row +=	"Courses: <ul>"; 
				Set<Course> courses = student.getCourses();
				for(Course c : courses) {
					row += "<li>Name: [" + c.getName() + "] Days: " + c.getMeetingDays() + "</li>";
				}
				row += "</ul>";
				
				row +=	"Charges: <ul>"; 
				Set<Charge> charges = student.getCharges();
				for(Charge c : charges) {
					row += "<li>Amount: $" + c.getAmount() + " Deadline: " + dateFormatter.format(c.getDeadline()) + " Reason: " + c.getReason() + "</li>";
				}
				row += "</ul>";
				
				row += "</li>";
				resp.getWriter().println(row);
			}
			resp.getWriter().println("</ul>");
		}
		
	}

	@SuppressWarnings("unchecked")
	private void displayAdmins(HttpServletRequest req, HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Admins</h1>");
		List<Admin> admins = (List<Admin>) pm.newQuery(Admin.class).execute();
		if (admins.size() == 0) {
			resp.getWriter().println("<p>There are no admins.</p>");
		} else {
			resp.getWriter().println("<ul>");
			for (Admin admin : admins) {
				String row = "";
				row += "<li>(" + admin.getUser_id().getId() + ")<br/>Name: "+ admin.getFullName() + "<br/>Username: " + admin.getUsername() +
						"<br/>password: " + admin.getPassword() + "<br/>email: " + admin.getEmail() + "<br/>";
				row += "</li>";
				resp.getWriter().println(row);
			}
			resp.getWriter().println("</ul>");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void displayTeachers(HttpServletRequest req, HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Teachers</h1>");
		List<Teacher> teachers = (List<Teacher>) pm.newQuery(Teacher.class).execute();
		if (teachers.size() == 0) {
			resp.getWriter().println("<p>There are no teachers.</p>");
		} else {
			resp.getWriter().println("<ul>");
			for (Teacher teacher : teachers) {
				String row = "";
				row += "<li>(" + teacher.getUser_id().getId() + ")<br/>Name: "+ teacher.getFullName() + "<br/>Username: " + teacher.getUsername() +
						"<br/>password: " + teacher.getPassword() + "<br/>email: " + teacher.getEmail() + "<br/>phone-number: " + teacher.getPhoneNumber() +
						 "<br/>Courses: <ul>"; 
				Set<Course> courses = teacher.getCourses();
				for(Course c : courses) {
					row += "<li>Name: [" + c.getName() + "] \nTeacher: " + c.getTeacher().getFullName() + "\nDays: " + c.getMeetingDays() + "</li>";
				}
				row += "</ul></li>";
				resp.getWriter().println(row);
			}
			resp.getWriter().println("</ul>");
		}
	}

	@SuppressWarnings("unchecked")
	private void displayCourses(HttpServletRequest req,
			HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Courses</h1>");
		List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
		if (courses.size() == 0) {
			resp.getWriter().println("<p>There are no courses.</p>");
		} else {
			resp.getWriter().println("<ul>");
			for (Course course : courses) {
				resp.getWriter().println("<li>(" + course.getCourse_id().getId() + "): " + course.getName() +
						 "<br/>Meeting days: " + course.getMeetingDays() + "</li>");
			}
			resp.getWriter().println("</ul>");
		}
	}
}
