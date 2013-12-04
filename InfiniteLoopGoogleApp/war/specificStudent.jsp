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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file='/templates/teacher_header.jsp'%>	
	<div id="content">
	<span class="title">${studentName}</span>
	<h2 class="home_title">Account Charges:</h2>
	<div class="home_item subsection">
		<p>Charges:</p>
		<p class="subsection">
			<c:if test="${balance > 0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="Student is owed money!" var="balance_note"/>
			</c:if>
			<c:if test="${balance == 0}">
				<c:set value="green" var="balance_color"/>
				<c:set value="Student has nothing due!" var="balance_note"/>
			</c:if>
			<c:if test="${balance < 0}">
				<c:set value="red" var="balance_color"/>
				<c:set value="Student owes money!" var="balance_note"/>
			</c:if>
			Balance: <a style="color:${balance_color}" href="/StudentChargesServlet">$${balance}</a><br/>
			${balance_note}
		</p>
	</div>
	<h2 class="home_title">Classes:</h2>
	<div class="home_item subsection">
		<c:choose>
		      <c:when test="${fn:length(courses) == 0}">
		      		<p>This student is not enrolled in any courses.</p>
		      </c:when>
		      <c:otherwise>
				<c:forEach items="${courses}" var="course">
					<h3 class="home_title"><a href="cooking_course_info_teacher.html">${course.name}</a></h3> <!--  Make link go to specific course -->
						<ul>
							<li><b>Instructor:</b> <em>${course.teacher.fullName}</em></li>
							<li><b>Email:</b><em><a href="mailto:${course.teacher.email}">${course.teacher.email}</a></em></li>
							<li><b>Start Date:</b> <em>${course.startDate}</em></li>
							<li><b>End Date:</b> <em>${course.endDate}</em></li>
							<li><b>Meeting Days:</b> <em>${course.meetingDays}</em></li>
							<li><b>Time:</b> <em>${course.time}</em></li>
							<li><b>Location:</b> <em>${course.place}</em></li>
							<li><b>Payment Method:</b> <em>$${course.paymentOption}</em></li>
							<li><b>Description:</b> <em>${course.description}</em></li>
						</ul>
				</c:forEach>
			 </c:otherwise>
		</c:choose>
	</div>
	<h2 class="home_title">Awards:</h2>
	<div class="home_item subsection">
		<ul>
		<c:choose>
		      <c:when test="${fn:length(awards) == 0}">
		      		<p>This student has not received any awards.</p>
		      </c:when>
		      <c:otherwise>
				<c:forEach items="${awards}" var="award">
					<li>${award.awardName}</li>
				</c:forEach>
			  </c:otherwise>
			</c:choose>
		<ul>
	</div>
</div>
	
<%@include file='/templates/footer.html'%>
							
									