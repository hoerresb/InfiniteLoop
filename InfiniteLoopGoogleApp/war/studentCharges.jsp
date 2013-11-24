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
						<label for='${student.user_id}_add_charge_amount'>Amount:</label>
						<input class='charge_input' name='${student.user_id}_add_charge_amount' type='text'><br/>
						
						<label for='${student.user_id}_add_charge_deadline'>Deadline:</label>
						<input class='charge_input' name='${student.user_id}_add_charge_deadline' type='text'><br/>
						
						<label for='${student.user_id}_add_charge_reason'>Reason:</label>
						<input class='charge_input' name='${student.user_id}_add_charge_reason' type='text'><br/>
						<button id='add-id' type='submit'>Add</button>
						
					</td>
					<td>
						<c:set value='${0.0}' var='total'/>								
						
						<c:forEach items='${student.charges}' var='charge'>
							<c:set value='${total + charge.amount}' var='total'/>
							<c:set value='${charge.deadline}' var='deadline'/>
						</c:forEach>
						
						<c:forEach items='${student.courses}' var='course'>
							<c:set value='${total + course.payment_amount}' var='total'/>
						</c:forEach>
						<span>Total: $${total}</span><br/>
						<span>Due: ${deadline.month+1}-${deadline.day}-${deadline.year+1900}</span>
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