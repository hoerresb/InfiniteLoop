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
<c:forEach items="${courses}" var="course">
<h2>${course.name} Attendance</h2>
</c:forEach>
</div><!--content-->




<div class="attendanceContainer">
<c:forEach items="${course_select.attendance}" var="att" begin="1" end="1">
<p style="color:#fff"><span>Week ${att.date} </span></p>
</c:forEach>

<table>
<tr>
	<th>Name</th>
	    <c:forEach items="${course_select.meetingDays}" var="meet">
			    <th>${meet}</th>
	</c:forEach> 
	</tr>
<c:forEach items="${course_select.attendance}" var="att">

			<tr>
			 <c:forEach items="${att.studentName}" var="student">
			 	
			 		<td>${student}</td>
			 	
			 	<c:forEach items="${att.present}" var="m">
			 		<td class="center">${m}</td>
			 		
			    </c:forEach>
			    
			 </c:forEach>
		</tr>
	
	</c:forEach>
	
	</table>
	</div>

	










<%@include file='/templates/footer.html'%>