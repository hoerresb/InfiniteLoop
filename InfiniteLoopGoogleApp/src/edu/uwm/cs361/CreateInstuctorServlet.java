package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;

@SuppressWarnings("serial")
public class CreateInstuctorServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		List<String> errors = new ArrayList<String>();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String password_repeat = req.getParameter("password_repeat");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String phonenumber = (req.getParameter("phonenumber")).equals("null") ? null : req.getParameter("phonenumber");
		
		String instructor_types = req.getParameter("instructor_types");
		String[] instructor_types_array = null;
		if(!req.getParameter("instructor_types").equals("null")) {
			instructor_types_array = instructor_types.split(",");
		}
		
		PersistenceManager pm = getPersistenceManager();
		
		if (username.isEmpty()) {
			errors.add("Username is required.");
		}
		if (password.isEmpty()) {
			errors.add("Password is required.");
		} else if (!password.equals(password_repeat)) {
			errors.add("Passwords do not match.");
		}
		if (phonenumber.isEmpty()) {
			errors.add("Phone Number is required.");
		}
		if (email.isEmpty()) {
			errors.add("Email is required.");
		}

		try {
			if (errors.size() > 0) {
				req.setAttribute("firstname", firstname);
				req.setAttribute("lastname", lastname);
				req.setAttribute("email", email);
				req.setAttribute("phonenumber", phonenumber);
				req.setAttribute("username", username);
				req.setAttribute("password", password);
				req.setAttribute("password_repeat", password_repeat);
				req.setAttribute("instructor_types", instructor_types);
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("/createInstructor.jsp").forward(req, resp);
			} else {
				createInstructor(username, password, firstname, lastname,
						email, phonenumber, instructor_types_array, pm);
				resp.sendRedirect("/display");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public void createInstructor(String username, String password,
			String firstname, String lastname, String email,
			String phonenumber, String[] instructor_types_array,
			PersistenceManager pm) {
		pm.makePersistent(new User(UserConstants.TEACHER_NUM,username,password,firstname,lastname,email,phonenumber,instructor_types_array));
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
