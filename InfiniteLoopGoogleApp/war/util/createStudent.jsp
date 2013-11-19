<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Create Student</title>
	</head>
	<body>
		<form action='createStudent' method='POST'>
			<label for='username'>Username: </label>
			<input type='text' id='username' name='username' /><br/><br/>
			<label for='password'>Password: </label>
			<input type='text' id='password' name='password' /><br/><br/>
			<label for='firstname'>First Name: </label>
			<input type='text' id='firstname' name='firstname' /><br/><br/>
			<label for='laststname'>Last Name: </label>
			<input type='text' id='lastname' name='lastname' /><br/><br/>
			<label for='email'>Email: </label>
			<input type='text' id='email' name='email' /><br/><br/>
			<label for='phonenumber'>Phone Number: </label>
			<input type='text' id='phonenumber' name='phonenumber' /><br/><br/>
			
			<label for='course_list'>Courses: </label>
	        <select id="course_opts" name="course_opts" multiple>
	            <optgroup>
	               <c:forEach items="${course_list}" var="course">
			     	 <option value="${course.course_id.id}">${course.name}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>

			<label for='teacher_list'>Teachers: </label>
	        <select id="teacher_opts" name="teacher_opts" multiple>
	            <optgroup>
	               <c:forEach items="${teacher_list}" var="teacher">
			     	 <option value="${teacher.user_id.id}">${teacher.fullName}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>
			
			<label for='award_list'>Awards: </label>
	        <select id="award_opts" name="award_opts" multiple>
	            <optgroup>
	               <c:forEach items="${award_list}" var="award">
			     	 <option value="${award.award_id.id}">${award.name}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>
	        
	        <label for='charge_list'>Charges: </label>
	        <select id="charge_opts" name="charge_opts" multiple>
	            <optgroup>
	               <c:forEach items="${charge_list}" var="charge">
			     	 <option value="${charge.charge_id.id}">${charge.amount} ${charge.reason}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>
			
			<input type='submit' value='Create Student' />
		</form>
	</body>
</html>