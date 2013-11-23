<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				
<%@include file='/templates/teacher_header.html'%>	
	<div id='content'>
		<%@include file='/templates/error.html'%>
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
			<input id='username-id' class='text-input' type='text' name='username' value='${username}'/><br/><br/> 
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
				<tr>
					<td class="register_left">
						<label for='teacher_list'>Teachers: </label>
					</td>
				    <td class="register_right">
				        <select id="teacher_opts" name="teacher_opts" multiple>
				            <optgroup>
				               <c:forEach items="${teacher_list}" var="teacher">
						     	 <option value="${teacher.user_id.id}">${teacher.fullName}</option>
							   </c:forEach> 
				            </optgroup>
				        </select><br/><br/>
			        </td>
				</tr>
				<tr>
					<td class="register_left">
						<label for='award_list'>Awards: </label>
				    </td>
				    <td class="register_right">
				        <select id="award_opts" name="award_opts" multiple>
				            <optgroup>
				               <c:forEach items="${award_list}" var="award">
						     	 <option value="${award.award_id.id}">${award.name}</option>
							   </c:forEach> 
				            </optgroup>
				        </select><br/><br/>
					</td>
	        	</tr>
	        	<tr>
	        		<td class="register_left">
	       				<label for='charge_list'>Charges: </label>
	        		</td>
	        		<td class="register_right">
				        <select id="charge_opts" name="charge_opts" multiple>
				            <optgroup>
				               <c:forEach items="${charge_list}" var="charge">
						     	 <option value="${charge.charge_id.id}">${charge.amount} ${charge.reason}</option>
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
		
