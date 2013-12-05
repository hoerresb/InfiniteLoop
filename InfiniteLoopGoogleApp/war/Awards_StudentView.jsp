<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>



<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="edu.uwm.cs361.entities.Award" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file='/templates/student_header.jsp'%>        
        <div id="content">
		<ul>Awards Earned:

			<c:forEach items="${awards}" var="award">

				<li>${award.awardName}</li>

			</c:forEach>
		
		</ul>


	</div>
<%@include file='/templates/footer.html'%>
