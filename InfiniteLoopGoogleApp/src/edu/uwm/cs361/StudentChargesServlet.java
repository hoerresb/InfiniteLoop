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
		User Chris = new User(0, "cjsampon", "1234", "Chris", "Sampon", "cjsampon@uwm.edu", "414-416-4881", classlist);
		
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
	
	private void displayForm(HttpServletRequest req, HttpServletResponse resp, List<String> errors) throws IOException {
		resp.setContentType("text/html");
		String[] names = new String[1];
		String[] emails = new String[1];
		String[] classlist = new String[1];
		String[] charges = new String[1];
		if (req.getParameterValues("names") != null)
			names = req.getParameterValues("names");
		else
			names[0] = " ";
		if (req.getParameterValues("emails") != null)
			emails = req.getParameterValues("emails");
		else
			emails[0] = " ";
		if (req.getParameterValues("classlist") != null)
			classlist = req.getParameterValues("classlist");
		else
			classlist[0] = " ";
		if (req.getParameterValues("charges") != null)
			charges = req.getParameterValues("charges");
		else
			charges[0] = " ";
		
		String page = PageTemplate.printHeader();
		page += PageTemplate.printErrors(errors);		
				
				
		page +=			"<form id='form-id' method='POST' action='/studentCharges'>" +
		"					<div class='chargesContainer'>" + 
		"						<table>" + 
		"							<tr>" + 
		"								<th>Student Name</th>" + 
		"								<th>Classes</th>" +
		"								<th>Charges</th>" + 
		"								<th>E-mail</th>" + 
		"							</tr>" + 
		"							<tr>" + 
		"								<td><a href=''>";
		page += names[0];
		page +=								"</a></td>" + 
		"								<td>";
		
		for (int i=0; i<classlist.length && i<charges.length; i++) {
			page += "<label for='"+classlist[i]+"'><a href=''>"+classlist[i]+"</a></label><br/>";
		}
		
		page +=					       "</td>" +
		"								<td>";
		
		for (int i=0; i<classlist.length && i<charges.length; i++) {				
			page += "<input id='"+classlist[i]+"' name='"+classlist[i]+"charges' type='text' value='"+charges[i]+"'/><br/>";
		}
		
		page +=					  	   "</td>" + 
		"								<td>" +
		"									<a href='mailto:";
		page+= emails[0]+"'>"+emails[0]+"</a>";
		
		page +=						   "</td>" + 
		"							</tr>" + 						
		"						</table>" + 
		"						<div id='button-area'>" + 
		"							<button id='submit-id' type='submit'>Submit</button><br/><br/>" + 
		"						</div>" + 
		"					</form>" +
		"				</div>";
		
		page += PageTemplate.printFooter();
		
		resp.getWriter().println(page);
	}
}
