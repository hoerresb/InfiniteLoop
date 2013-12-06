<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Create Admin</title>
	</head>
	<body>
		<form action='/createCharge' method='POST'>
			<label for='amount'>Amount: </label>
			<input type='text' id='amount' name='amount' placeholder="4.50"/><br/><br/>
			<label for='deadline'>Deadline: </label>
			<input type='text' id='deadline' name='deadline' placeholder="MM/dd/yyyy"/><br/><br/>
			<label for='reason'>Reason: </label>
			<input type='text' id='reason' name='reason'/><br/><br/>
			<label for='student_list'>Students: </label>
	        <select id="student_list" name="student_list" multiple>
	            <optgroup>
	               <c:forEach items="${student_list}" var="student">
			     	 <option value="${student.user_id.id}">${student.fullName}</option>
				   </c:forEach> 
	            </optgroup>
	        </select><br/><br/>
			<input type='submit' value='Create Charge' />
		</form>
	</body>
</html>