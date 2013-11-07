package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import edu.uwm.cs361.entities.*;

import java.util.List;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = getPersistenceManager();

		try {
			resp.setContentType("text/html");
			
			String username = req.getParameter("username") != null ? req.getParameter("username") : "";
			String password = req.getParameter("password") != null ? req.getParameter("password") : "";

			resp.getWriter().println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"	<head>\r\n" + 
					"		<title>Monet Mall</title>\r\n" + 
					"		<link type=\"text/css\" rel=\"stylesheet\" href='/css/stylesheet.css'/>\r\n" + 
					"	</head>\r\n" + 
					"	<body>\r\n" + 
					"		<div id=\"container\">\r\n" + 
					"			<div id=\"banner\"></div>\r\n" + 
					"			<div id=\"content\">\r\n" + 
					"				<span class=\"title\">Log In</span>\r\n" + 
					"				<form id=\"form-id\" method=\"POST\" action='/login'>\r\n" + 
					"					<label for=\"username-id\">Username:</label>\r\n" + 
					"					<input id=\"username-id\" class=\"text-input\" type=\"text\" name=\"username\" autofocus=\"autofocus\" value='" + username + "'  /><br/><br/>\r\n" + 
					"					<label for=\"password-id\">Password:</label>\r\n" + 
					"					<input id=\"password-id\" class=\"text-input\" type=\"password\" name=\"password\" value='" + password + "' /><br/><br/>\r\n" + 
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
		PersistenceManager pm = getPersistenceManager();
		List<User> customers = (List<User>) pm.newQuery(User.class).execute();

		resp.setContentType("text/html");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User");
		q.setFilter(Query.CompositeFilterOperator.and(
				new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username),
				new Query.FilterPredicate("password", Query.FilterOperator.EQUAL, password)));
		List<Entity> entities = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		if(entities.size() == 0 ){
			doGet(req, resp);			
		}
		if(entities.size()>0){
			resp.sendRedirect("/studentCharges");
		}
		
		
		
		
		
		
	}


	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
