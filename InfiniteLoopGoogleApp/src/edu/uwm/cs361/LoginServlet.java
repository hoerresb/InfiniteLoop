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
		
		if(errors.size() < 1) {
			try{
				req.setAttribute("errors", errors);
				resp.setContentType("text/html");
	
				List<Admin> adm = (List<Admin>) pm.newQuery(Admin.class).execute();
			
				List<Student> Student = (List<Student>) pm.newQuery(Student.class).execute();
				
				List<Teacher> Teacher = (List<Teacher>) pm.newQuery(Teacher.class).execute();
				
				if(adm.size()>0){
				for(int i = 0 ; i < adm.size(); i ++){
					if(adm.get(i).getPassword().equals(password) && adm.get(i).getUsername().equals(username)){
						Cookie c = new Cookie("Adminname", req.getParameter("username"));
						resp.addCookie(c);
						resp.sendRedirect("/AdminHome");
					}
				}}
				
				if(Student.size()>0){
				for(int i = 0 ; i < Student.size(); i ++){
					if(Student.get(i).getPassword().equals(password) && Student.get(i).getUsername().equals(username)){
						Cookie student = new Cookie("Studentname", req.getParameter("username"));
						resp.addCookie(student);
						resp.sendRedirect("/StudentHome");
					}
				}}
				
				if(Teacher.size()>0){
				for(int i = 0 ; i < Teacher.size(); i ++){
					if(Teacher.get(i).getPassword().equals(password) && Teacher.get(i).getUsername().equals(username)){
						Cookie teacher = new Cookie("Teachername", req.getParameter("username"));
						resp.addCookie(teacher);
						resp.sendRedirect("/TeacherHome");
					}
				}}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				pm.close();
			}
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/login.jsp").forward(req, resp);	
		}
	}


	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
