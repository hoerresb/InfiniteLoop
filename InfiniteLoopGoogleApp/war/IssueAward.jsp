<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>

<%@include file='/templates/teacher_header.jsp'%>
	<div id="content">
	<%@include file='/templates/error.html'%>
	<span class="success">${success}</span><br/>
	
		<p>Please select an <%=award_txt%> and a <%=student_txt%> to give it to.</p>
	
		<form id='form-id' method='POST' action='/issueAward'>
		
	        
			<label for="award_select"><%=award_txt%>s:</label><br />	        
			<p class="centerME">
		        <select multiple id="award_options" name="award_options">
		            <optgroup>
		               <c:forEach items="${award_options}" var="award">
				     	 <option value="${award.awardName}">${award.awardName}</option>
					   </c:forEach> 
		            </optgroup>
		        </select><br/><br/>
			</p>
	        
			<label for="student_select"><%=student_txt%>s:</label><br />	        
	        	<p class="centerME">
		        <select multiple id="student_options" name="student_options">
		            <optgroup>
		               <c:forEach items="${student_options}" var="student">
				     	 <option value="${student.user_id.id}">${student.username}</option>
					   </c:forEach> 
		            </optgroup>
		        </select><br/><br/>
	        	</p>
	    	<p id="GoButton" class="centerME">
	    	<input type="hidden" name="course_id" value="${course_select.course_id.id}" />
				<button id='submit-id' type='submit'>Go</button><br/><br/> 
			</p>
		</form>
	</div>
<%@include file='/templates/footer.html'%>
