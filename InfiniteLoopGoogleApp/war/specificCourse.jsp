<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file='/templates/teacher_header.jsp'%>	
<div id="content">
	<div class="home_item subsection">
	<h2 class="home_title">${course.name}:</h2>
		<ul>
			<li><b>Start Date:</b> <em>${course.startDate}</em></li>
			<li><b>End Date:</b> <em>${course.endDate}</em></li>
			<li><b>Meeting Days:</b> <em>${course.meetingDays}</em></li>
			<li><b>Time:</b> <em>${course.time}</em></li>
			<li><b>Location:</b> <em>${course.place}</em></li>
			<li><b>Payment Method:</b> <em>$${course.paymentOption}</em></li>
			<li><b>Description:</b> <em>${course.description}</em></li>
		</ul>
	</div>
</div>
<%@include file='/templates/footer.html'%>