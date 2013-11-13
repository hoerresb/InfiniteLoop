package edu.uwm.cs361;

import java.io.IOException;



import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AdminHome extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		
		String username = null;

		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("Adminname")) {
					username = c.getValue();
				}
			}
		}

		resp.setContentType("text/html");
		
		resp.getWriter().println( "<!DOCTYPE html>" +
				"<html>" +
				"	<head>" +
				"		<title>Monet Mall</title>" +
				"		<link type='text/css' rel='stylesheet' href='/css/stylesheet.css'/>" +
				"	</head>" +
				"	<body>" +
				"		<div id='container'>" +
				"			<div id='banner'></div>" +
				"			<div id='navbar'>" +
				"				<div class='nav'><a href='/AdminHome'>Home</a></div>" +
				"				<div class='nav'><a href=''>Create Class</a></div>" +
				"				<div class='nav'><a href='/createInstructor.jsp'>Create Instructor</a></div>" +
				"				<div class='nav'><a href='/studentCharges.jsp'>Student Charges</a></div>" +	
				"				<div id='login' class='nav'><a href='/login.jsp'>Log Out</a></div>" +
				"			</div>"+
				"			<div id='content'>	"	+
				"            <h1>Welcome, " + username + "</h1>"+
				"</div>" +
				"</div>" +
				"</body>"+
				"</html>");
		
		
	}
		
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		
	}
	@SuppressWarnings("unused")
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}