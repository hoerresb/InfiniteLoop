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
			
				
<%@include file='/templates/admin_header.jsp'%>	
	<div id="content">
		<%@include file='/templates/error.html'%>
		<span class="success">${success}</span><br/>
		<span class='title'>Student Charges:</span>
	</div>
	<div class='chargesContainer'>
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
						<a href='/billingStatement?student_id=${student.user_id.id}'>${student.fullName}</a>
					</td>
					<td>						
					<form id='form-id-${student.user_id}' method='POST' action='/StudentChargesServlet'>
					
						<label for='${student.user_id}_add_charge_amount'>Amount:</label>
						<input class='charge_input' name='${student.user_id}_add_charge_amount' type='text'><br/>

						<label for='${student.user_id}_add_charge_reason'>Reason:</label>
						<input class='charge_input' name='${student.user_id}_add_charge_reason' type='text'><br/>
						
						<input type="hidden" name="student_id" value="${student.user_id.id}" />
						<button id='add-id' type='submit'>Add</button>
				</form>			
					</td>
					<td>
						<c:set value='${0.0}' var='total'/>								
						
						<c:forEach items='${student.charges}' var='charge'>
							<c:set value='${total + charge.amount}' var='total'/>
							<c:set value='${charge.formattedDeadline}' var='deadline'/>
						</c:forEach>
						
						<span>Total: <a href='/billingStatement?student_id=${student.user_id.id}'>$${total}</a></span><br/>
						<span>Due: ${deadline}</span>
					</td>
					<td>
						<a href='mailto:${student.email}'>${student.email}</a>
					</td>
				</tr>
			</c:forEach>					
			</table>
	</div>
<%@include file='/templates/footer.html'%>