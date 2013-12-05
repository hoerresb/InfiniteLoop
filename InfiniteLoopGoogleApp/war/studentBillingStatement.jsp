<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="edu.uwm.cs361.entities.Admin" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file='/templates/student_header.jsp'%>	

<div class='chargesContainer'>
	<table>
		<tr>
			<th class="title" colspan="4">Billing Statement</th>
		</tr>
		<th>Date</th>
		<th>Description</th>
		<th>Amount</th>
		<th>Total</th>
		<c:set value='${0.0}' var='total'/>
		<c:forEach items='${charges}' var='charge'>
			<tr>
				<td>
					<span> ${charge.deadline.month+1}-${charge.deadline.day+1}-${charge.deadline.year+1900}</span>
				</td>
				<td>
					<span>${charge.reason}</span>
				</td>
				<td>
					<span>${charge.amount}</span>
					<c:set value='${total + charge.amount}' var='total'/>
				</td>
				<td>
					<span>${total}</span>
				</td>
			</tr>
		</c:forEach>
	</table>		
</div>
<%@include file='/templates/footer.html'%>
							
									