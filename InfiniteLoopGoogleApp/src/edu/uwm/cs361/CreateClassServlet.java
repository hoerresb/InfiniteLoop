package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course; 


@SuppressWarnings("serial")
public class CreateClassServlet extends HttpServlet {
	@Override
	d
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		List<String> errors = new ArrayList<String>();
		
		String classname = req.getParameter("classname");
		String classstart = req.getParameter("classstart");
		String classend = req.getParameter("classend");
		
		String[] meeting_times = req.getParameterValues("meeting_times");
		

		
		String time = req.getParameter("time");
		String place = req.getParameter("place");
		
		
		String options = req.getParameter("options");
		String[] options_array = null;
		if(!req.getParameter("options").equals("null")) {
			options_array = options.split(",");
		}
		
		String description = req.getParameter("description");
		
	
		
		
		
		PersistenceManager pm = getPersistenceManager();
		
		if (classname.isEmpty()) {
			errors.add("Please enter a Class Name.");
		}
		if (classstart.isEmpty()) {
			errors.add("Please enter a Class Start Time.");
		}
		if (classend.isEmpty()) {
			errors.add("Please enter a Class End Time.");
		}
		if (meeting_times.isEmpty()) {
			errors.add("Please enter Class Meeting Days.");
		}
		if (time.isEmpty()) {
			errors.add("Please enter a Time.");
		}
		if (place.isEmpty()) {
			errors.add("Please enter a Class Meeting Place.");
		}
		if (description.isEmpty()) {
			errors.add("Please enter a Class Description..");
		}
		
		

		try {
			if (errors.size() > 0) {
				req.setAttribute("classname", classname);
				req.setAttribute("classstart", classstart);
				req.setAttribute("classend", classend);
				req.setAttribute("meeting_times", meeting_times);
				req.setAttribute("time", time);
				req.setAttribute("place", place);
				req.setAttribute("description", description);
				req.setAttribute("options", options);
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("/createClass.jsp").forward(req, resp);
			} else {
				createClass(classname, classstart, classend, meeting_times, time,
						place, options_array, description, pm);
				resp.sendRedirect("/display");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public void createClass(String classname, String classstart,
			String classend, String[] meeting_times, String time, String place, String[] options_array, String description,
			PersistenceManager pm) {
		pm.makePersistent(new Course(classname, classstart, classend, meeting_times, time, place, options_array, description));
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}