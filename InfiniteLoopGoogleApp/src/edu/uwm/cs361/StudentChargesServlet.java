package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.PageTemplate;
import edu.uwm.cs361.util.UserConstants;

@SuppressWarnings("serial")
public class StudentChargesServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		displayForm(req, resp, new ArrayList<String>());
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		List<String> errors = new ArrayList<String>();
		
		String[] classlist = {"Cooking For Dummies","Class 2","Class 3"};
		String[] charges = {"12","15","0"};
		User Chris = new User(UserConstants.ADMIN_NUM, "cjsampon", "1234", "Chris", "Sampon", "cjsampon@uwm.edu", "414-416-4881", classlist);
		
		String[] names = {Chris.getFullName()};
		String[] emails = {Chris.getEmail()};		
		
		PersistenceManager pm = getPersistenceManager();
		
		if (names.length != emails.length) {
			errors.add("The number of names does not equal the number of emails.");
		}
		
		if (classlist.length != charges.length) {
			errors.add("The number of classes does not equal the number of charges.");
		}
		
		try {
			if (errors.size() > 0) {
				displayForm(req, resp, errors);
			} else {
				pm.makePersistent(Chris);
				resp.sendRedirect("/studentCharges");
			}
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
	@SuppressWarnings("unchecked")
	private void displayForm(HttpServletRequest req, HttpServletResponse resp, List<String> errors) throws IOException {
		resp.setContentType("text/html");
		
		PersistenceManager pm = getPersistenceManager();
		List<User> users = (List<User>) pm.newQuery(User.class).execute();		
		
		int numStudents = 0, count = 0;
		try { 
			for (User user : users) {
				if (user.getUser_type()==UserConstants.STUDENT_NUM) {
					numStudents++;			
				}
			}
		} finally {
			pm.close();
		}
		
		User[] students = new User[numStudents];
		try { 
			for (User user : users) {
				if (user.getUser_type()==UserConstants.STUDENT_NUM) {
					students[count] = user;
					++count;
				}
			}
		} finally {
			pm.close();
		}
		
		
		String[] classlist = {"Cooking For Dummies","Class 2","Class 3"};
		String[] charges = {"12","15","0"};
		
		String[] names = new String[students.length];
		for (int i=0; i<students.length; i++) {
			names[i] = students[i].getFullName();
		}		
		
		String[] emails = new String[students.length];	
		for (int i=0; i<students.length; i++) {
			emails[i] = students[i].getEmail();
		}
		
		String page = PageTemplate.printAdminHeader();
		page += PageTemplate.printErrors(errors);		
				
		
		
		page +=			"<form id='form-id' method='POST' action='/studentCharges'>\r\n" +
							"<div class='chargesContainer'>\r\n";


		
		page +=					"<table>\r\n" + 
									"<tr>\r\n" + 
										"<th>Student Name</th>\r\n" + 
										"<th>Classes</th>\r\n" +
										"<th>Charges</th>\r\n" + 
										"<th>E-mail</th>\r\n" + 
									"</tr>\r\n"; 
		for (int i=0; i<students.length; i++) {
			page += 			    "<tr>\r\n" +
										"<td>\r\n" +
											"<a href=''>"+students[i].getFullName()+"</a>\r\n" +
										"</td>\r\n" +
										"<td>\r\n";
			for (int j=0; j<classlist.length && j<charges.length; j++) {
				page += "<label for='"+classlist[j]+"'><a href=''>"+classlist[j]+"</a></label><br/>\r\n";
			}
			page +=					    "</td>\r\n" +
										"<td>\r\n";
			for (int j=0; j<classlist.length && j<charges.length; j++) {
				page += "<input id='"+classlist[j]+"' name='"+classlist[j]+"charges' type='text' value='"+charges[j]+"'/><br/>\r\n";
			}
			page +=					    "</td>\r\n" +
										"<td>\r\n" +
											"<a href='mailto:"+students[i].getEmail()+"'>"+students[i].getEmail()+"</a>\r\n" +
										"</td>\r\n" +
									"</tr>\r\n";
		}
	
		page +=					"</table>\r\n" + 
								"<div id='button-area'>\r\n" + 
									"<button id='submit-id' type='submit'>Submit</button><br/><br/>\r\n" + 
								"</div>\r\n" + 
							"</form>\r\n" +
						"</div>\r\n";
		
		page += PageTemplate.printFooter();
		
		resp.getWriter().println(page);
	}
}
