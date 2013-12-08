<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%@include file='/templates/teacher_header.jsp'%>
	<div id="content">
	
		<p>Please select a course, an award from that course, and a student to give it to.</p>
	
		<form id='form-id' method='POST' action='IssueAward'>
		
			<label for="course_select">Courses:</label><br />
	        <p class="centerME">
		        <select id="course_options" name="course_options">
		            <optgroup>
		               <c:forEach items="${courses}" var="course">
				     	 <option value="${course.course_id.id}">${course.name}</option>
					   </c:forEach> 
		            </optgroup>
		        </select><br/><br/>
	        </p>
	        
			<label for="award_select">Awards:</label><br />	        
	        <p class="centerME">
		        <select id="award_options" name="award_options">
		            <optgroup>
		               <c:forEach items="${awards}" var="award">
				     	 <option value="${award.award_id.id}">${award.awardName}</option>
					   </c:forEach> 
		            </optgroup>
		        </select><br/><br/>
	        </p>
	        
			<label for="student_select">Students:</label><br />	        
	        <p class="centerME">
		        <select id="student_options" name="student_options">
		            <optgroup>
		               <c:forEach items="${students}" var="student">
				     	 <option value="${student.user_id.id}">${student.username}</option>
					   </c:forEach> 
		            </optgroup>
		        </select><br/><br/>
	        </p>
	    	<p id="GoButton" class="centerME">
				<button id='submit-id' type='submit'>Go</button><br/><br/> 
			</p>
		</form>
	</div>
<%@include file='/templates/footer.html'%>
