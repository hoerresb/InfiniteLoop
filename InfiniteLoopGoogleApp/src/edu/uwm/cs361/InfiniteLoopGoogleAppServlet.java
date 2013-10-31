package edu.uwm.cs361;

import java.io.IOException;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class InfiniteLoopGoogleAppServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = getPersistenceManager();

		try {
			resp.setContentType("text/html");
			resp.getWriter().println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"	<head>\r\n" + 
					"		<title>Monet Mall</title>\r\n" + 
					"		<link type=\"text/css\" rel=\"stylesheet\" href=\"stylesheet.css\"/>\r\n" + 
					"	</head>\r\n" + 
					"	<body>\r\n" + 
					"		<div id=\"container\">\r\n" + 
					"			<div id=\"banner\"></div>\r\n" + 
					"			<div id=\"content\">\r\n" + 
					"				<span class=\"title\">Log In</span>\r\n" + 
					"				<form id=\"form-id\" method=\"POST\" action=\"http://webdevbasics.net/scripts/demo.php\">\r\n" + 
					"					<label for=\"username-id\">Username:</label>\r\n" + 
					"					<input id=\"username-id\" class=\"text-input\" type=\"text\" name=\"username\" autofocus=\"autofocus\" /><br/><br/>\r\n" + 
					"					<label for=\"password-id\">Password:</label>\r\n" + 
					"					<input id=\"password-id\" class=\"text-input\" type=\"password\" name=\"password\" /><br/><br/>\r\n" + 
					"					<div id=\"button-area\">\r\n" + 
					"						<button id=\"submit-id\" type=\"submit\">Login</button><br/><br/>\r\n" + 
					"					</div>\r\n" + 
					"				</form>\r\n" + 
					"			</div>\r\n" + 
					"		</div>\r\n" + 
					"	</body>\r\n" + 
					"</html>");
		} finally {
			pm.close();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
