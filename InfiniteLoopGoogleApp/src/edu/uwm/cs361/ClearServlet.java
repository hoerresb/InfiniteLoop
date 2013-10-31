package edu.uwm.cs361;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;

@SuppressWarnings("serial")
public class ClearServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = getPersistenceManager();

		try {
			for (User user : (List<User>) pm.newQuery(User.class).execute()) {
				pm.deletePersistent(user);
			}

			resp.sendRedirect("/display");
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
