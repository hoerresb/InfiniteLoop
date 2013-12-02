<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				
<%@include file='/templates/teacher_header.jsp'%>	
	<div id='content'>
		<%@include file='/templates/error.html'%>
		<span class="success">${success}</span><br/>
		<span class='title'>Register a Student:</span>
		<form id='form-id' method='POST' action='/RegisterStudentServlet'>
			<label for='firstname-id'>First Name:</label>
			<input id='firstname-id' class='text-input' type='text' name='firstname' autofocus='autofocus' value='${firstname}'/><br/><br/>
			<label for='lastname-id'>Last Name:</label>
			<input id='lastname-id' class='text-input' type='text' name='lastname' value='${lastname}'/><br/><br/>
			<label for='email-id'>E-Mail:</label>
			<input id='email-id' class='text-input' type='email' name='email' value='${email}'/><br/><br/>
			<label for='phone_num-id'>Phone number:</label>
			<input id='phone_num-id' class='text-input' type='text' name='phonenumber' value='${phonenumber}'/><br/><br/>
			<label for='username-id'>Username:</label>
			<input id='username-id' class='text-input' type='text' name='student_username' value='${student_username}'/><br/><br/> 
			<label for='password'>Password:</label>
			<input id='password' class='text-input' type='password' name='password' value='${password}'/><br/><br/>
			<label for='password_repeat'>Retype Password:</label>
			<input id='password_repeat' class='text-input' type='password' name='password_repeat' value='${password_repeat}'/><br/><br/>
			
	        <table class="register">
	        	<tr>
	        		<td class="register_left">
						<label for='course_list'>Courses: </label>
					</td>
					<td class="register_right">
				        <select id="course_opts" name="course_opts" multiple>
				            <optgroup>
				               <c:forEach items="${course_list}" var="course">
						     	 <option value="${course.course_id.id}">${course.name}</option>
							   </c:forEach> 
				            </optgroup>
				        </select><br/><br/>
			        </td>
				</tr>
			</table>
			<div id='button-area'>
				<button id='submit-id' type='submit'>Register</button><br/><br/> 
			</div>
		</form>
	</div>	
<%@include file='/templates/footer.html'%>
		
