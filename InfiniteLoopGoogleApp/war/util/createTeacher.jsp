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
			<label for='student_list'>Instructor Types: </label>
			<input type='text' id='student_list' name='student_list' placeholder='John, Matt, Nick ...'/><br/><br/>
			<label for='course_list'>Instructor Types: </label>
			<input type='text' id='course_list' name='course_list'/><br/><br/>
			<input type='submit' value='Create Teacher' />
		</form>
	</body>
</html>