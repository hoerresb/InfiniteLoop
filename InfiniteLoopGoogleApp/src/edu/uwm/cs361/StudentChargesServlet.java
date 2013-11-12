package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.PageTemplate;
import edu.uwm.cs361.util.UserConstants;

@SuppressWarnings("serial")
public class StudentChargesServlet extends HttpServlet {	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		List<String> errors = new ArrayList<String>();

		String[] classlist = {"Cooking For Dummies","Class 2","Class 3"}; 
		Charge[] charges = new Charge[classlist.length];
		
		PersistenceManager pm = getPersistenceManager();
		List<User> users = (List<User>) pm.newQuery(User.class).execute();
		User[] students;
		int numStudents = 0, count = 0;
		try {
			numStudents = 0;
			count = 0;
			for (User user : users) {
				if (user.getUser_type()==UserConstants.STUDENT_NUM) {
					numStudents++;			
				}
			}
			students = new User[numStudents];
			for (User user : users) {
				if (user.getUser_type()==UserConstants.STUDENT_NUM) {
					students[count] = user;
					++count;
				}
			}
		} finally {
			pm.close();
		}
		pm = getPersistenceManager();
		for (int i=0; i<students.length; i++) {
			for (int j=0; j<classlist.length; j++) {
				charges[j] = new Charge(Double.parseDouble(req.getParameter(students[i].getUser_id()+"_"+classlist[j]+"_charge")));
				students[i].setCharges(charges);
				System.out.println(charges[j].getAmount());
			}
		}
		
		try {
			if (errors.size() > 0) {
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("studentCharges.jsp").forward(req, resp);
			} else {
				pm.makePersistent(students);
				resp.sendRedirect("studentCharges.jsp");
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	private void displayForm(HttpServletRequest req, HttpServletResponse resp, List<String> errors) throws IOException {
//		resp.setContentType("text/html");
//		
//		PersistenceManager pm = getPersistenceManager();
//		List<User> users = (List<User>) pm.newQuery(User.class).execute();		
//		
//		int numStudents = 0, count = 0;
//		try { 
//			for (User user : users) {
//				if (user.getUser_type()==UserConstants.STUDENT_NUM) {
//					numStudents++;			
//				}
//			}
//		} finally {
//			pm.close();
//		}
//		
//		User[] students = new User[numStudents];
//		try { 
//			for (User user : users) {
//				if (user.getUser_type()==UserConstants.STUDENT_NUM) {
//					students[count] = user;
//					++count;
//				}
//			}
//		} finally {
//			pm.close();
//		}
//		
//		
//		
//		String[] classlist = {"Cooking For Dummies","Class 2","Class 3"};
//		
//		
//		
//		Charge[] charges = {new Charge(12, new Date(2013,11,31), ""), new Charge(15, new Date(2013,11,31), "") , new Charge(18, new Date(2013,11,31), "")}; 
//		
//		double[] charge_amount = new double[charges.length];
//		for (int i=0; i<charge_amount.length; i++) {
//			charge_amount[i] = charges[i].getAmount();
//		}
//		
//		Date[] deadlines = new Date[charges.length];
//		for (int i=0; i<deadlines.length; i++) {
//			deadlines[i] = charges[i].getDeadline();
//		}
//		
//		String[] names = new String[students.length];
//		for (int i=0; i<students.length; i++) {
//			names[i] = students[i].getFullName();
//		}		
//		
//		String[] emails = new String[students.length];	
//		for (int i=0; i<students.length; i++) {
//			emails[i] = students[i].getEmail();
//		}
//		
//		String page = PageTemplate.printAdminHeader();
//		page += PageTemplate.printErrors(errors);		
//				
//		
//		
//		page +=			"<form id='form-id' method='POST' action='/studentCharges'>\r\n" +
//							"<div class='chargesContainer'>\r\n";
//
//
//		
//		page +=					"<table>\r\n" + 
//									"<tr>\r\n" + 
//										"<th>Student Name</th>\r\n" + 
//										"<th>Classes</th>\r\n" +
//										"<th>Charges</th>\r\n" + 
//										"<th>Due</th>\r\n" +
//										"<th>E-mail</th>\r\n" + 
//									"</tr>\r\n"; 
//		for (int i=0; i<students.length; i++) {
//			page += 			    "<tr>\r\n" +
//										"<td>\r\n" +
//											"<a href=''>"+students[i].getFirstName()+" "+students[i].getLastName()+"</a>\r\n" +
//										"</td>\r\n" +
//										"<td>\r\n";
//			for (int j=0; j<classlist.length && j<charge_amount.length; j++) {
//				page += "<label for='"+classlist[j]+"'><a href=''>"+classlist[j]+"</a></label><br/>\r\n";
//			}
//			page +=					    "</td>\r\n" +
//										"<td>\r\n";
//			for (int j=0; j<classlist.length && j<charge_amount.length; j++) {
//				page += "<input class='charge_input' id='"+classlist[j]+"' name='"+classlist[j]+"_charges' type='text' value='"+charge_amount[j]+"'/><br/>\r\n";
//			}
//			page +=						"</td>\r\n" +
//										"<td>\r\n";
//			for (int j=0; j<deadlines.length && j<classlist.length; j++) {
//				page += "<input class='charge_due' id='"+classlist[j]+"_deadline' name='"+classlist[j]+"_charge_deadline' type='text' value='"+(deadlines[j].getMonth()+1)+"-"+deadlines[j].getDate()+"-"+deadlines[j].getYear()+"'/><br/>\r\n";
//			}
//			page +=					    "</td>\r\n" +
//										"<td>\r\n" +
//											"<a href='mailto:"+students[i].getEmail()+"'>"+students[i].getEmail()+"</a>\r\n" +
//										"</td>\r\n" +
//									"</tr>\r\n";
//		}
//	
//		page +=					"</table>\r\n" + 
//								"<div id='button-area'>\r\n" + 
//									"<button id='submit-id' type='submit'>Submit</button><br/><br/>\r\n" + 
//								"</div>\r\n" + 
//							"</form>\r\n" +
//						"</div>\r\n";
//		
//		page += PageTemplate.printFooter();
//		
//		resp.getWriter().println(page);
//	}
}
