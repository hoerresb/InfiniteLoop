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
<%@ page import="edu.uwm.cs361.entities.*" %>
<%@ page import="edu.uwm.cs361.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file='/templates/teacher_header.jsp'%>	

<form id="form-id" style="text-align:center;" method="POST" action='/AttendanceSheet'> 

<div id="content">
<%@include file='/templates/error.html'%>
<span class="success">${success}</span><br/>
<label for="attendance_select">Select to set Attendance for:</label><br />
<p class="centerME">
    <select id="attendance_select" name="work_please">
 
        <c:forEach var="i" begin="1" end="${weeks}">  
        	<option value="${i}">Week ${i} </option>  
		</c:forEach>
    </select><br/><br/>
</p>

</div><!--content-->


<div class="attendanceContainer">

	<table>
		<tr>
			<th>Name</th>
			    <c:forEach items="${course_select.meetingDays}" var="meet">
					    <th>${meet}</th>
			</c:forEach> 

			 <c:forEach items="${students}" var="student">
			 	<tr>
			 		<th>${student.fullName}</th>
			 	<c:forEach items="${course_select.meetingDays}" var="m">
			 		<th class="center"><input id="hiddenValues" type="checkbox" name="${student.user_id}_attendance" value="1">
			 		<input type="hidden" name="${student.user_id}_attendance" value="0"></th>
			 		
			    </c:forEach>
			    </tr>
			 </c:forEach>
		</tr>
	
	</table>
	
	<input type="hidden" name="course_id" value="${course_select.course_id.id}" />
	<p style="text-align:center;"><button id="submit-id" type="submit">Submit</button><br/><br/></p>
	</form>


</div>














<%@include file='/templates/footer.html'%>