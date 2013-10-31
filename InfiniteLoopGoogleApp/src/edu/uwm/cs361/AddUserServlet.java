package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;

@SuppressWarnings("serial")
public class AddUserServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");

		resp.getWriter().println("<form action='/addUser' method='POST'>");
		
			resp.getWriter().println("<select id='customer' name='userType' />");
				resp.getWriter().println("<option value=\"0\">Admin</option>");
				resp.getWriter().println("<option value=\"1\">Teacher</option>");
				resp.getWriter().println("<option value=\"2\">Student</option>");
			resp.getWriter().println("</select>");
			
			resp.getWriter().println("<label for='username'>Username: </label>");
			resp.getWriter().println("<input type='text' id='username' name='username' />");
			
			resp.getWriter().println("<label for='password'>Password: </label>");
			resp.getWriter().println("<input type='text' id='password' name='password' />");
			
			resp.getWriter().println("<label for='firstname'>First Name: </label>");
			resp.getWriter().println("<input type='text' id='firstname' name='firstname' />");
			
			resp.getWriter().println("<label for='laststname'>Last Name: </label>");
			resp.getWriter().println("<input type='text' id='lastname' name='lastname' />");
			
			resp.getWriter().println("<label for='email'>Email: </label>");
			resp.getWriter().println("<input type='text' id='email' name='email' />");
			
			resp.getWriter().println("<input type='submit' value='Create User' />");
		resp.getWriter().println("</form>");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = getPersistenceManager();

		try {
			pm.makePersistent(new User(Integer.parseInt(req.getParameter("userType")),req.getParameter("username"),req.getParameter("password"),
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
