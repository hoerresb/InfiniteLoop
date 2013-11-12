<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="edu.uwm.cs361.CreateClassServlet" %>
<%@ page import="edu.uwm.cs361.entities.*" %>
<%@ page import="edu.uwm.cs361.util.*" %>
<%@ page import="edu.uwm.cs361.util.PageTemplate" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

				
<%@include file='/templates/admin_header.html'%>
<%@include file='/templates/error.html'%>

<div id='content'>
	<span class='title'>Create a Class:</span>
	<form id='form-id' method='POST' action='/createClass'>
		<label for='classname'>Class Name:</label>
		<input id='classname' class='text-input' type='text' name='classname' autofocus ='autofocus' value='${classname}'/><br/><br/>
		<label for='classstart'>Class Start:</label>
		<input id='classstart' class='text-input' type='text' name='classstart' value='${classstart}'/><br/><br/>
		<label for='classend'>Class End:</label>
		<input id='classend' class='text-input' type='text' name='classend' value='${classend}'/><br/><br/>
		
		<label for='meeting_times'>Meeting Times:</label>
		<input id='meeting_times' class='text-input' type='text' name='meeting_times'
			<c:forEach begin="0" end="${fn:length(meeting_times) - 1}" var="index">
				<tr>
      				<td><c: day="${meeting_times[index]}"/></td>
				</tr>
			</c:forEach>
			
		<label for='time'>Time:</label>
		<input id='time' class='text-input' type='text' name='time' value='${time}'/><br/><br/> 
		<label for='place'>Place:</label>
		<input id='place' class='text-input' type='text' name='place' value='${place}'/><br/><br/>
		<label for='options'>Payment Options:</label><br/>
		<input id='options' class='text-input' type='text' name='options' 
			<c:choose>
			      <c:when test="${options == ''}">
			      		placeholder='Ex: $30/month, $150 for 6 months'/><br/><br/>
			      </c:when>
			      <c:otherwise>
			      		value='${options}'/><br/><br/>
			      </c:otherwise>
			</c:choose>
		
		<label for='description'>Description:</label>
		<input id='description' class='text-input' type='text' name='description' value='${description}'/><br/><br/>
		
		<div id='button-area'>
			<button id='submit-id' type='submit'>Create</button><br/><br/> 
		</div>
	</form>
</div>	
<%@include file='/templates/footer.html'%>
			