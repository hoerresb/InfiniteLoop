<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="edu.uwm.cs361.util.PageTemplate" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="edu.uwm.cs361.StudentChargesServlet" %>
<%@ page import="edu.uwm.cs361.entities.*" %>
<%@ page import="edu.uwm.cs361.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			
				
<%@include file='/templates/admin_header.html'%>	
	<%@include file='/templates/error.html'%>
	<div class='chargesContainer'>
		<form id='form-id' method='POST' action='/studentCharges'>
			<table>
				<tr>
					<th>Student Name</th>
					<th>Classes</th>
					<th>Charges</th>
					<th>Due</th>
					<th>E-mail</th>
				</tr>
					<%! 
						private PersistenceManager getPersistenceManager() {
							return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
						} 
					%>
					<%! PersistenceManager pm = getPersistenceManager(); %>
					<%! List<User> users = (List<User>) pm.newQuery(User.class).execute(); %>
					<%! int numStudents = 0, count = 0; %>
					<%! String[] classlist = {"Cooking For Dummies","Class 2","Class 3"}; %>
					<%! Charge[] charges = {new Charge(12, new Date(2013,11,31), ""), new Charge(15, new Date(2013,11,31), "") , new Charge(18, new Date(2013,11,31), "")};  %>
					<% 
						try {
							numStudents = 0;
							count = 0;
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
					%>	
					<% for (int i=0; i<students.length; ++i) { %>
						<tr>
							<td>
								<a href='#'><%=students[i].getFullName()%></a>
							</td>
							<td>
								<% for (int j=0; j<classlist.length; j++) { %>
									<label for='<%=classlist[j]%>'><a href='#'><%=classlist[j]%></a></label><br/>
								<% } %>
							</td>
							<td>
								<% for (int j=0; j<classlist.length; j++) { %>
									<input class='charge_input' id='<%=classlist[j]%>_charge' type='text' value='<%=charges[j].getAmount()%>'><br/>
								<% } %>
							</td>
							<td>
								<% for (int j=0; j<classlist.length; j++) { %>
									<input class='charge_due' id='<%=classlist[j]%>_deadline' type='text' value='<%=(charges[j].getDeadline().getMonth()+1)+"-"+charges[j].getDeadline().getDate()+"-"+charges[j].getDeadline().getYear()%>'><br/>
								<% } %>
							</td>
							<td>
								<a href='mailto:<%=students[i].getEmail()%>'><%=students[i].getEmail()%></a>
							</td>
						</tr>
					<% } %>
			</table>
			<div id='button-area'>
				<button id='submit-id' type='submit'>Submit</button><br/><br/> 
			</div>
		</form>
	</div>
<%@include file='/templates/footer.html'%>
		
