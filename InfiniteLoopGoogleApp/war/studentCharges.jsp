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
			<c:forEach items='${students}' var='student'>
				<tr>
					<td>
						<a href='#'>${student.fullName}</a>
					</td>
					<td>
						<c:forEach items='${student.courses}' var='course'>
							<label><a href='#'>${course.name}</a></label><br/>
						</c:forEach>
					</td>
					<td>
						<c:forEach items='${charges}' var='charge'>
							<input class='charge_input' name='${student.user_id}_${course.name}_charge' type='text' value='${charge.amount}'><br/>
						</c:forEach>
					</td>
					<td>
						<c:forEach items='${deadlines}' var='deadline'>
							<input class='charge_due' name='${student.user_id}_${course.name}_deadline' type='text' value='${charge.deadline.month+1}-${charge.deadline.date}-${charge.deadline.year}'><br/>
						</c:forEach>
					</td>
					<td>
						<a href='mailto:${student.email}'>${student.email}</a>
					</td>
				</tr>
			</c:forEach>					
			</table>
			<div id='button-area'>
				<button id='submit-id' type='submit'>Submit</button><br/><br/> 
			</div>
		</form>
	</div>
<%@include file='/templates/footer.html'%>