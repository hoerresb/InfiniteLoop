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
		<span class="title">Select to view Attendance</span>
		<p></p>
			<div class="classSelect">
				<nav>
				<ul>
					<li><a href="">Select Class</a>
						<ul>
						
						<c:forEach items="${courses}" var="course">
							<c:forEach items="${teachers}" var="t">
								<c:forEach items="${t.courses}" var="t_course">
									<c:if test="${t_course}!=${course}">
										<c:set value="${t}" var="teacher"/>
									</c:if>
								</c:forEach>
							</c:forEach>
								<li><a href="/CourseAttendance?course_id=${course.course_id.id}">${course.name}</a></li>
						</c:forEach>
							
						</ul>
					</li>
				</ul>
			</nav>
		</div><!--classSelect-->
	</div><!--content-->


<%@include file='/templates/footer.html'%>
