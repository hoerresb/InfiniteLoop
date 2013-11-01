package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.PageTemplate;

@SuppressWarnings("serial")
public class CreateInstuctorServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		displayForm(req, resp, new ArrayList<String>());
	}


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
				displayForm(req, resp, errors);
			} else {
				pm.makePersistent(new User(1,username,password,firstname,lastname,email,phonenumber,instructor_types_array));
				resp.sendRedirect("/display");
			}
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	private void displayForm(HttpServletRequest req, HttpServletResponse resp, List<String> errors) throws IOException {
		resp.setContentType("text/html");
		
		String username = req.getParameter("username") != null ? req.getParameter("username") : "";
		String password = req.getParameter("password") != null ? req.getParameter("password") : "";
		String password_repeat = req.getParameter("password_repeat") != null ? req.getParameter("password_repeat") : "";
		String firstname = req.getParameter("firstname") != null ? req.getParameter("firstname") : "";
		String lastname = req.getParameter("lastname") != null ? req.getParameter("lastname") : "";
		String email = req.getParameter("email") != null ? req.getParameter("email") : "";
		String phonenumber = req.getParameter("phonenumber") != null ? req.getParameter("phonenumber") : "";
		String instructor_types = req.getParameter("instructor_types");
		if(req.getParameter("instructor_types") == null || req.getParameter("instructor_types").equals("null")) {
			instructor_types = "";
		} 
		
		

		
		String page = PageTemplate.printHeader();
		page += PageTemplate.printErrors(errors);		
				
				
		page +=		"<div id='content'>" +
		"				<span class='title'>Create an Instructor:</span>" +
		"				<form id='form-id' method='POST' action='/createInstructor'>" +
		"					<label for='firstname-id'>First Name:</label>" +
		"					<input id='firstname-id' class='text-input' type='text' name='firstname' autofocus='autofocus' value='" + firstname + "'/><br/><br/>" +
		"					<label for='lastname-id'>Last Name:</label>" +
		"					<input id='lastname-id' class='text-input' type='text' name='lastname' value='" + lastname + "'/><br/><br/>" +
		"					<label for='email-id'>E-Mail:</label>" +
		"					<input id='email-id' class='text-input' type='email' name='email' value='" + email + "'/><br/><br/>" +
		"					<label for='phone_num-id'>Phone number:</label>" +
		"					<input id='phone_num-id' class='text-input' type='text' name='phonenumber' value='" + phonenumber + "'/><br/><br/>" +
		"					<label for='instructor_types'>Instructor Type:</label><br/>" +
		"					<input id='instructor_types' class='text-input' type='text' name='instructor_types' ";
				
		if(instructor_types.equals("")) {
			page += "placeholder='Ex: Dog Trainer, Tutor ...'";
		} else {
			page += "value='" + instructor_types + "'";
		}
		
		page += "					/><br/><br/>" +
		"					<label for='username-id'>Username:</label>" +
		"					<input id='username-id' class='text-input' type='text' name='username' value='" + username + "'/><br/><br/>" + 
		"					<label for='password'>Password:</label>" +
		"					<input id='password' class='text-input' type='password' name='password' value='" + password + "'/><br/><br/>" +
		"					<label for='password_repeat'>Retype Password:</label>" +
		"					<input id='password_repeat' class='text-input' type='password' name='password_repeat' value='" + password_repeat + "'/><br/><br/>" +
		"					<div id='button-area'>" +
		"						<button id='submit-id' type='submit'>Create</button><br/><br/>" + 
		"					</div>" +
		"				</form>" +
		"			</div>";
		
		page += PageTemplate.printFooter();
		
		resp.getWriter().println(page);
	}
}
