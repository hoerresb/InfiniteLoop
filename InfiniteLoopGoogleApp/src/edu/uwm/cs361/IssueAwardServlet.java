package edu.uwm.cs361;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.util.UserConstants;

@SuppressWarnings("serial")
public class IssueAwardServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		
		PersistenceManager pm = getPersistenceManager();
		
		pm.makePersistent(new User(UserConstants.STUDENT_NUM, "cjsampon", "1234", "Chris", "Sampon", "cjsampon@uwm.edu", "414-416-4881", null));

		pm.makePersistent(new User(UserConstants.STUDENT_NUM, "pyoung", "1234", "Kevin", "Young", "pyoung@uwm.edu", "414-520-2361", null));
		
		pm.makePersistent(new Award("beginner", "blah")); 
		pm.makePersistent(new Award("master", "blah blah")); 


		resp.getWriter().println("<form action='/issueAward' method='POST'>");
		
		resp.getWriter().println("<label for='award'>Award:</label>");
		resp.getWriter().println("<select id='award' name='award'>");
				
		for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) {
			resp.getWriter().println("<option value='" + award.getAward_id() + "'>" + award.getAwardName() + "</option>");
		}
		resp.getWriter().println("</select><br/><br/>");
		
		
		resp.getWriter().println("<label for='students'>Student:</label>");
		resp.getWriter().println("<select multiple id='students' name='students'>");
				
		for (User user : (List<User>) pm.newQuery(User.class).execute()) {
			resp.getWriter().println("<option value='" + user.getUser_id() + "'>" + user.getFullName() + "</option>");
		}
		resp.getWriter().println("</select><br/><br/>");
			
			
				
		resp.getWriter().println("<input type='submit' value='Issue Award' />");
		resp.getWriter().println("</form>");
			
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		
		PersistenceManager pm = getPersistenceManager();

		try {
			resp.setContentType("text/plain");
			long awardId = Long.parseLong(req.getParameter("award"));
			Award award = pm.getObjectById(Award.class, awardId);
			String[] studentIds = req.getParameterValues("students");
			
			for (String id : studentIds) {
				long studentId = Long.parseLong(id);
				User user = pm.getObjectById(User.class, studentId);

				user.getAwards().add(award);
				award.getUsers().add(user);
			}
			
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
