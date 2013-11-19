<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Create Teacher</title>
	</head>
	<body>
		<form action='/addTeacher' method='POST'>
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
			<label for='instructor_types'>Instructor Types: </label>
			<input type='text' id='instructor_types' name='instructor_types'/><br/><br/>
			
			<label for='student_list'>Students: </label>
	        <select id="student_opts" name="student_opts" multiple>
	            <optgroup>
	               <c:forEach items="${student_list}" var="student">
			     	 <option value="${student.user_id.id}">${student.fullName}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>
			
			<label for='course_list'>Courses: </label>
	        <select id="course_opts" name="course_opts" multiple>
	            <optgroup>
	               <c:forEach items="${course_list}" var="course">
			     	 <option value="${course.course_id.id}">${course.name}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>
			
			<input type='submit' value='Create Teacher' />
		</form>
	</body>
</html>