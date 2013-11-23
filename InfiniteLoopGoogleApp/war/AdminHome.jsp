<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="edu.uwm.cs361.entities.Admin" %>

<%
	String username = null;

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("Adminname")) {
					username = c.getValue();
				}
			}
		}
%>

<%@include file='/templates/admin_header.html'%>	
	<div id="content">
		<h2> Welcome, ${username} </h2>
		<div class="home_item">
			<span>Charges:</span>
			<p>
				<c:if test="${balance > 0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="You are turning a profit!" var="balance_note"/>
				</c:if>
				<c:if test="${balance == 0}">
					<c:set value="green" var="balance_color"/>
					<c:set value="You have broken even!" var="balance_note"/>
				</c:if>
				<c:if test="${balance < 0}">
					<c:set value="red" var="balance_color"/>
					<c:set value="You are losing money!" var="balance_note"/>
				</c:if>
				Balance: <a style="color:${balance_color}" href="/StudentChargesServlet">$${balance}</a><br/>
				${balance_note}
			</p>
		</div>
	</div>
<%@include file='/templates/footer.html'%>
							
									