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
public class DisplayServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");

		PersistenceManager pm = getPersistenceManager();

		try {
			displayUsers(req, resp, pm);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void displayUsers(HttpServletRequest req, HttpServletResponse resp, PersistenceManager pm) throws IOException {
		resp.getWriter().println("<h1>Users</h1>");

		List<User> users = (List<User>) pm.newQuery(User.class).execute();

		if (users.size() == 0) {
			resp.getWriter().println("<p>There are no users.</p>");
		} else {
			resp.getWriter().println("<ul>");

			for (User user : users) {
				resp.getWriter().println("<li>(" + user.getUser_id() + "): " + user.getFullName() + "</li>");
			}

			resp.getWriter().println("</ul>");
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
