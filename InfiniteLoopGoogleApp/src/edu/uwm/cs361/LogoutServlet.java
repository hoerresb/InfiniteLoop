package edu.uwm.cs361;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

import edu.uwm.cs361.entities.old_User;

@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		Cookie c = new Cookie("Adminname", null);
		c.setMaxAge(0);

		resp.addCookie(c);
		resp.sendRedirect("/login.jsp");
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<String> errors = new ArrayList<String>();
		PersistenceManager pm = getPersistenceManager();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if (username.isEmpty()) {
			errors.add("Username is required.");
		}
		if (password.isEmpty()) {
			errors.add("Password is required.");
		}
		
		
		try{
			req.setAttribute("errors", errors);
	List<old_User> us = (List<old_User>) pm.newQuery(old_User.class).execute();
	
		resp.setContentType("text/html");
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User");
		q.setFilter(Query.CompositeFilterOperator.and(
				new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username),
				new Query.FilterPredicate("password", Query.FilterOperator.EQUAL, password)));
		List<Entity> entities = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		if(entities.size() == 0 ){
			req.getRequestDispatcher("/login.jsp").forward(req, resp);			
		}
		//if(entities.size()>0){
		if(us.size()>0){
			for(int i = 0; i < us.size(); i++){
				if(us.get(i).getPassword().equals(password) && us.get(i).getUsername().equals(username) && us.get(i).getUser_type() == 0){
					resp.sendRedirect("/AdminHome");
				}
				if(us.get(i).getPassword().equals(password) && us.get(i).getUsername().equals(username) && us.get(i).getUser_type() == 1){
					resp.sendRedirect("/login.jsp");
				}
				if(us.get(i).getPassword().equals(password) && us.get(i).getUsername().equals(username) && us.get(i).getUser_type() == 2){
					resp.sendRedirect("/login.jsp");
				}
			}
			//resp.sendRedirect("/login.jsp");
	//	}
		}
		}catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
		
		
		
	}


	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}

