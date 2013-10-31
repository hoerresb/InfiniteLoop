package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;

@SuppressWarnings("serial")
public class CreateInstuctorServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");

		resp.getWriter().println("<!DOCTYPE html>" +
				"<html>" +
				"	<head>" +
				"		<title>Monet Mall</title>" +
				"		<link type='text/css' rel='stylesheet' href='/css/stylesheet.css'/>" +
				"	</head>" +
				"	<body>" +
				"		<div id='container'>" +
				"			<div id='banner'></div>" +
				"			<div id='navbar'>" +
				"				<div class='nav'><a href='admin_home.html'>Home</a></div>" +
				"				<div class='nav'><a href='create_a_class.html'>Create Class</a></div>" +
				"				<div class='nav'><a href='create_instructor.html'>Create Instructor</a></div>" +
				"				<div id='login' class='nav'><a href='/login'>Log Out</a></div>" +
				"			</div>" +
				"			<div id='content'>" +
				"				<span class='title'>Create an Instructor:</span>" +
				"				<form id=\"form-id\" method=\"POST\" action='/createInstructor'>" +
				"					<label for=\"firstname-id\">First Name:</label>" +
				"					<input id=\"firstname-id\" class=\"text-input\" type=\"text\" name=\"firstname\" autofocus=\"autofocus\" /><br/><br/>" +
				"					<label for=\"lastname-id\">Last Name:</label>" +
				"					<input id=\"lastname-id\" class=\"text-input\" type=\"text\" name=\"lastname\" /><br/><br/>" +
				"					<label for=\"email-id\">E-Mail:</label>" +
				"					<input id=\"email-id\" class=\"text-input\" type=\"email\" name=\"email\" /><br/><br/>" +
				"					<label for=\"phone_num-id\">Phone number:</label>" +
				"					<input id=\"phone_num-id\" class=\"text-input\" type=\"text\" name=\"phone_num\" /><br/><br/>" +
				"					<label for=\"instructor_type\">Instructor Type:</label><br/>" +
				"					<input id=\"instructor_type\" class=\"text-input\" type=\"text\" name=\"verify\" placeholder=\"Ex: Dog Trainer, Tutor ...\"/><br/><br/>" +
				"					<label for=\"username-id\">Username:</label>" +
				"					<input id=\"username-id\" class=\"text-input\" type=\"text\" name=\"username\" /><br/><br/>" + 
				"					<label for=\"password1-id\">Password:</label>" +
				"					<input id=\"password1-id\" class=\"text-input\" type=\"password\" name=\"password1\" /><br/><br/>" +
				"					" +
				"					<label for=\"password2-id\">Retype Password:</label>" +
				"					<input id=\"password2-id\" class=\"text-input\" type=\"password\" name=\"password2\" /><br/><br/>" +
				"					<div id=\"button-area\">" +
				"						<button id=\"submit-id\" type=\"submit\">Submit</button><br/><br/>" + 
				"					</div>" +
				"				</form>" +
				"			</div>" +
				"		</div>" +
				"	</body>" +
				"</html>");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = getPersistenceManager();

		try {
			pm.makePersistent(new User(Integer.parseInt(req.getParameter("1")),req.getParameter("username"),req.getParameter("password"),
					req.getParameter("firstname"),req.getParameter("lastname"),req.getParameter("email")));
			resp.sendRedirect("/display");
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
