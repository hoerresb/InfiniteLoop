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
			resp.getWriter().println("</select><br/><br/>");
			
			resp.getWriter().println("<label for='username'>Username: </label>");
			resp.getWriter().println("<input type='text' id='username' name='username' /><br/><br/>");
			
			resp.getWriter().println("<label for='password'>Password: </label>");
			resp.getWriter().println("<input type='text' id='password' name='password' /><br/><br/>");
			
			resp.getWriter().println("<label for='firstname'>First Name: </label>");
			resp.getWriter().println("<input type='text' id='firstname' name='firstname' /><br/><br/>");
			
			resp.getWriter().println("<label for='laststname'>Last Name: </label>");
			resp.getWriter().println("<input type='text' id='lastname' name='lastname' /><br/><br/>");
			
			resp.getWriter().println("<label for='email'>Email: </label>");
			resp.getWriter().println("<input type='text' id='email' name='email' /><br/><br/>");
			
			resp.getWriter().println("<label for='phonenumber'>Phone Number: </label>");
			resp.getWriter().println("<input type='text' id='phonenumber' name='phonenumber' /><br/><br/>");
			
			resp.getWriter().println("<label for='instructor_types'>Instructor Types: </label>");
			resp.getWriter().println("<input type='text' id='instructor_types' name='instructor_types' placeholder='Ex: Dog Trainer, Tutor ...'/><br/><br/>");
			
			resp.getWriter().println("<input type='submit' value='Create User' />");
		resp.getWriter().println("</form>");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		int userType = Integer.parseInt(req.getParameter("userType"));
		String username = req.getParameter("username");
		String password = req.getParameter("password");
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

		try {
			pm.makePersistent(new User(userType,username,password,firstname,lastname,email,phonenumber,instructor_types_array));
			resp.sendRedirect("/display");
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
