package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateInstructorFactory;
import edu.uwm.cs361.factories.PersistanceFactory;

@SuppressWarnings("serial")
public class CreateInstuctorServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		PersistenceManager pm = PersistanceFactory.getPersistenceManager();
		
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
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher instructor = instr_fact.createInstructor(username, password, password_repeat, firstname, lastname, email, phonenumber, instructor_types_array);

		try {
			if (instr_fact.hasErrors()) {
				req.setAttribute("firstname", firstname);
				req.setAttribute("lastname", lastname);
				req.setAttribute("email", email);
				req.setAttribute("phonenumber", phonenumber);
				req.setAttribute("username", username);
				req.setAttribute("password", password);
				req.setAttribute("password_repeat", password_repeat);
				req.setAttribute("instructor_types", instructor_types);
				req.setAttribute("errors", instr_fact.getErrors());
				req.getRequestDispatcher("/createInstructor.jsp").forward(req, resp);
			} else {
				pm.makePersistent(instructor);
				resp.sendRedirect("/display");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}
}
