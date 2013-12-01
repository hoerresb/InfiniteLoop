<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%@include file='/templates/teacher_header.jsp'%>

		<div id="content">

			<form action="/IssueAward" method="POST">

				<label for="award_courses">Courses:</label>

					<select id="award_courses" name="award_courses">
		
					<c:forEach items="${courses}" var="course">
							<option value="${course.course_id}">${course.name}</option>   
            		</c:forEach>

					</select><br/><br/>

				<input type="submit" value="IssueAward" />
			</form>
		</div>
		
<%@include file='/templates/footer.html'%>
