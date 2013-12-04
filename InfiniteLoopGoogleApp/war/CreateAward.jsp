<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%@include file='/templates/teacher_header.jsp'%>

		<div id="content">
		<%@include file='/templates/error.html'%>
			<span class="success">${success}</span><br/>
			<span class='title'>Create an Award:</span><br/>

			<form action="/CreateAward" method="POST">

				<label for="award_courses">Courses:</label>

					<select id="award_courses" name="award_courses">
		
						<c:forEach items="${courses}" var="course">
							<option value="${course.course_id.id}">${course.name}</option>   
            					</c:forEach>

					</select><br/><br/>
					
				<label for="award_name">Award Name:</label>
					<input id="award_name" type="text" name="award_name" value="${award_name}"/><br/><br/>
				
				<label for="award_description">Award Description:</label>
					<p>
						<textarea id="award_description" name="award_description" rows="5" cols="35">${award_description}</textarea><br/><br/>	
					</p>
				<input type="submit" value="Create Award" />
			</form>
		</div>
		
<%@include file='/templates/footer.html'%>
