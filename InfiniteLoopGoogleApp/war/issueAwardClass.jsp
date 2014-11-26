<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file='/templates/teacher_header.jsp'%>
	<div id="content">
		<form id='form-id' method='POST' action='issueAwardClass'>
			<label for="course_select">Select <%=class_txt%> to assign <%=award_txt%>s for:</label><br />
	        <p class="centerME">
		        <select id="course_select" name="course_options">
		            <optgroup>
		               <c:forEach items="${courses}" var="course">
				     	 <option value="${course.course_id.id}">${course.name}</option>
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
