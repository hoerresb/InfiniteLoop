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
			resp.sendRedirect("IssueAward.jsp");
			
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
