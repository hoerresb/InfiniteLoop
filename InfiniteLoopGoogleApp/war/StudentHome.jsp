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

<%@include file='/templates/student_header.jsp'%>	
	<div id="content">
		<h2> Welcome, ${username} </h2>
		<h3><%=charge_txt%>s</h3>
		<div class="home_item">
			<p>
				<c:if test="${balance > 0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="You are owed money!" var="balance_note"/>
				</c:if>
				<c:if test="${balance == 0.0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="You have nothing due!" var="balance_note"/>
				</c:if>
				<c:if test="${balance < 0}">
					<c:set value="red" var="balance_color"/>
					<c:set value="You owe money!" var="balance_note"/>
				</c:if>
				Balance: <a style="color:${balance_color}" href="/studentBillingStatement">$${balance}</a><br/>
				${balance_note}
			</p>
		</div>
		<h3><%=class_txt%>s</h3>
		<c:forEach items="${courses}" var="course">
			<div class="home_item">
				<span>${course.name}</span>
				<ul>
					<li><b><%=instructor_txt%>:</b> <em>${course.teacher.fullName}</em></li>
					<li><b>Email:</b><em><a href="mailto:${course.teacher.email}">${course.teacher.email}</a></em></li>
					<li><b>Start Date:</b> <em>${course.startDateFormatted}</em></li>
					<li><b>End Date:</b> <em>${course.endDateFormatted}</em></li>
					<li><b>Meeting Days:</b> <em>${course.meetingDays}</em></li>
					<li><b>Time:</b> <em>${course.time}</em></li>
					<li><b>Location:</b> <em>${course.place}</em></li>
					<li><b>Payment Method:</b> <em>$${course.paymentOption}</em></li>
					<li><b>Description:</b> <em>${course.description}</em></li>
				</ul>
			</div>
		</c:forEach>
		<h3><%=award_txt%>s</h3>
		<div class="home_item">
			<span><%=award_txt%>s</span>
			<ul>
				<c:forEach items="${courses}" var="course">
					<li>
						<b>${course.name}</b>
						<ul>
							<c:forEach items="${course.awards}" var="course_award">
								<c:if test="${num_awards > 0}">
									<c:forEach items="${student.awards}" var="student_award">
										<c:if test="${fn:contains(course_award.awardName, student_award.awardName)}">
											<li><em style="color: green">${course_award.awardName}</em></li>
										</c:if>
										<c:if test="${!fn:contains(course_award.awardName, student_award.awardName)}">
											<li><em style="color: red">${course_award.awardName}</em></li>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${num_awards == 0}">
									<li><em style="color: red">${course_award.awardName}</em></li>
								</c:if>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
<%@include file='/templates/footer.html'%>
							
									