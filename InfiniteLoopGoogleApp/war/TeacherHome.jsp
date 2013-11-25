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
		<h2> Welcome, ${username} </h2>
		
		<h3>Classes</h3>
		<c:forEach items="${courses}" var="course">
			<c:forEach items="${teachers}" var="t">
				<c:forEach items="${t.courses}" var="t_course">
					<c:if test="${t_course}!=${course}">
						<c:set value="${t}" var="teacher"/>
					</c:if>
				</c:forEach>
			</c:forEach>
			<div class="home_item">
				<span>${course.name}</span>
				<ul>
					<li><b>Instructor:</b> <em>teacher.fullName</em></li>
					<li><b>Email:</b><em><a href="mailto:${teacher.email}">teacher.email</a></em></li>
					<li><b>Start Date:</b> <em>${course.startDate}</em></li>
					<li><b>End Date:</b> <em>${course.endDate}</em></li>
					<li><b>Meeting Days:</b> <em>${course.meetingDays}</em></li>
					<li><b>Time:</b> <em>${course.time}</em></li>
					<li><b>Location:</b> <em>${course.place}</em></li>
					<li><b>Payment Method:</b> <em>$${course.paymentOption}</em></li>
					<li><b>Description:</b> <em>${course.description}</em></li>
				</ul>
			</div>
		</c:forEach>

<%@include file='/templates/footer.html'%>
							
									