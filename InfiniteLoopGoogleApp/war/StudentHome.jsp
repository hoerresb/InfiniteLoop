<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="edu.uwm.cs361.entities.Admin" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file='/templates/student_header.html'%>	
	<div id="content">
		<h2> Welcome, ${username} </h2>
		<h3>Charges</h3>
		<div class="home_item">
			<p>
				<c:if test="${balance > 0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="You are owed money!" var="balance_note"/>
				</c:if>
				<c:if test="${balance == 0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="You have nothing due!" var="balance_note"/>
				</c:if>
				<c:if test="${balance < 0}">
					<c:set value="red" var="balance_color"/>
					<c:set value="You owe money!" var="balance_note"/>
				</c:if>
				Balance: <a style="color:${balance_color}" href="/StudentChargesServlet">$${balance}</a><br/>
				${balance_note}
			</p>
		</div>
		<h3>Classes</h3>
		<div class="home_item">
			<span>student.course.name</span>
			<ul>
				<li> if(student.course == student.teacher.course) {</li>
				<li>Instructor: teacher.fullname</li>
				<li>Email: <a href="mailto:teacher.email">teacher.email</a></li>
				<li>Start Date: student.course.startdate</li>
				<li>End Date: student.course.enddate</li>
				<li>Meeting Days: student.course.meetingdays</li>
				<li>Time: student.course.time</li>
				<li>Location: student.course.place</li>
				<li>Payment Method: student.course.payment_option</li>
				<li>Description: student.course.description</li>
				<li> } </li>
			</ul>
		</div>
		<h3>Awards</h3>
		<div class="home_item">
			<span>And here is where awards would be...</span>
			<ul>
				<li>IF</li>
				<li>I</li>
				<li>HAD</li>
				<li>ANY</li>
			</ul>
		</div>
	</div>
<%@include file='/templates/footer.html'%>
							
									