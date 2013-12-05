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

<%@include file='/templates/teacher_header.jsp'%>	



<div id="content">
	
</div>


<div class="attendanceContainer">
	<table>
		<tr>
			<th>Name</th>
			    <c:forEach items="${meetingDays}" var="meet">
					    <th>${meet}</th>
			</c:forEach> 

			 <c:forEach items="${students}" var="student">
			 	<tr>
			 		<th>${student.fullName}</th>
			 	<c:forEach items="${meetingDays}" var="m">
			 		<th class="center"><input type="checkbox" name="myTextEditBox"></th>
			 		
			    </c:forEach>
			    </tr>
			 </c:forEach>
			
			
		</tr>
	
	</table>



</div>














<%@include file='/templates/footer.html'%>