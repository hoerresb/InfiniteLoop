<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Create Admin</title>
	</head>
	<body>
		<form action='/createAdmin' method='POST'>
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
			<input type='submit' value='Create Admin' />
		</form>
	</body>
</html>