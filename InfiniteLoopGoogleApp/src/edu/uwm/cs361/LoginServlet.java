package edu.uwm.cs361;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;

import edu.uwm.cs361.entities.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
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
	List<Admin> us = (List<Admin>) pm.newQuery(Admin.class).execute();
	
		resp.setContentType("text/html");
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Admin");
		q.setFilter(Query.CompositeFilterOperator.and(
				new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username),
				new Query.FilterPredicate("password", Query.FilterOperator.EQUAL, password)));
		List<Entity> entities = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		Query q2 = new Query("Student");
		q.setFilter(Query.CompositeFilterOperator.and(
				new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username),
				new Query.FilterPredicate("password", Query.FilterOperator.EQUAL, password)));
		List<Entity> Sentities = ds.prepare(q2).asList(FetchOptions.Builder.withDefaults());
		
		Query q3 = new Query("Teacher");
		q.setFilter(Query.CompositeFilterOperator.and(
				new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username),
				new Query.FilterPredicate("password", Query.FilterOperator.EQUAL, password)));
		List<Entity> Tentites = ds.prepare(q3).asList(FetchOptions.Builder.withDefaults());
		
		if(entities.size() == 0 ){
			req.getRequestDispatcher("/login.jsp").forward(req, resp);			
		}
		//if(entities.size()>0){
		if(entities.size()>0){
			Cookie c = new Cookie("Adminname", req.getParameter("username"));
			resp.addCookie(c);
			resp.sendRedirect("/AdminHome");
			/*for(int i = 0; i < us.size(); i++){
				if(us.get(i).getPassword().equals(password) && us.get(i).getUsername().equals(username) && us.get(i).getUser_type() == 0){
					Cookie c = new Cookie("Adminname", req.getParameter("username"));
					resp.addCookie(c);
					resp.sendRedirect("/AdminHome");
				}
				if(us.get(i).getPassword().equals(password) && us.get(i).getUsername().equals(username) && us.get(i).getUser_type() == 1){
					resp.sendRedirect("/login.jsp");
				}
				if(us.get(i).getPassword().equals(password) && us.get(i).getUsername().equals(username) && us.get(i).getUser_type() == 2){
					resp.sendRedirect("/login.jsp");
				}
			}
			*/
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
