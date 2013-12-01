<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%@include file='/templates/teacher_header.jsp'%>

		<div id="content">

			<form action="/IssueAward2" method="POST">

				<label for="course_awards">Courses:</label>

					<select id="course_awards" name="course_awards">
		
					<c:forEach items="${awards}" var="award">
							<option value="${award.award_id}">${award.awardName}</option>   
            		</c:forEach>

					</select><br/><br/>
					
				<label for="course_awards">Courses:</label>

					<select multiple id="award_students" name="award_students">
		
						<c:forEach items="${students}" var="student">
							<option value="${student.user_id}">${student.username}</option>  
            			</c:forEach>

					</select><br/><br/>

				<input type="submit" value="IssueAward2" />
			</form>
		</div>
		
<%@include file='/templates/footer.html'%>
