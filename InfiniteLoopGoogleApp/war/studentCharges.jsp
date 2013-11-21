<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
		<form id='form-id' method='POST' action='/StudentChargesServlet'>
			<table>
				<tr>
					<th>Student Name</th>
					<th>Add Charges</th>
					<th>Current Due</th>
					<th>E-mail</th>
				</tr>					
			<c:forEach items='${students}' var='student'>
				<tr>
					<td>
						<a href='#'>${student.fullName}</a>
					</td>
					<td>
						
						<c:set value='${student.charges}' var='charge'/>
						<label for='${student.user_id}}_add_charge'>Amount:</label>
						<input class='charge_input' name='${student.user_id}_add_charge' type='text'>
						<button id='add-id' type='submit'>Add</button>
					</td>
					<td>
						<c:set value='${0.0}' var='total'/>
						<c:forEach items='${student.charges}' var='charge'>
							<c:set value='${total + charge.amount}' var='total'/>
							<c:set value='${charge.deadline}' var='deadline'/>
						</c:forEach>
						<span>Total: $${total}</span><br/>
						<span>Due: ${deadline.month+1}-${deadline.day}-${deadline.year}</span>
					</td>
					<td>
						<a href='mailto:${student.email}'>${student.email}</a>
					</td>
				</tr>
			</c:forEach>					
			</table>
		</form>
	</div>
<%@include file='/templates/footer.html'%>