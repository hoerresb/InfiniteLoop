

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

<%@include file='../util/ParserText.jsp'%>

<!DOCTYPE html>
	<html>
		<head>
			<title>Monet Mall</title>
			<link type="text/css" rel="stylesheet" href="/css/stylesheet.css"/>
		</head>
		
	<%
	String username = null;

		Cookie[] cookies = request.getCookies();
		
		if(cookies == null){
			response.sendRedirect("/login.jsp");
		}

		if (cookies != null) {
			for (Cookie c : cookies) {
			
			 if(c.getName().equals("Teachername")){
					username = c.getValue();
				}
			 if(c.getName().equals("Studentname")){
				Cookie student = new Cookie("Studentname", null);
				student.setMaxAge(0);
				response.addCookie(student);
				response.sendRedirect("/login.jsp");
			}
				 if(c.getName().equals("Adminname")){
					Cookie admin = new Cookie("Adminname", null);
					admin.setMaxAge(0);
					response.addCookie(admin);
					response.sendRedirect("/login.jsp");
			}
			if(c.getName().isEmpty()){
				response.sendRedirect("/login.jsp");
			}
				
			}
		}
		
		
		
		request.setAttribute("username", username);
%>
		
		
		
		
		
		
		
		
		<body>
			<div id="container">
				<div id="banner"></div>
				<div id="navbar">
					<div class="nav"><a href="/TeacherHome">Home</a></div>
					<ul>
						<li><span class="nav"><a href="">Attendance</a></span>
							<ul>
								<li class="nav"><a href="/Attendance">Set Attendance</a></li>
								<li class="nav"><a href="/ViewAttendance">View Attendace</a><li>
							</ul>
						</li>
					</ul>
					<ul>
						<li><span class="nav"><a href=""><%=award_txt%>s</a></span>
							<ul>
								<li class="nav"><a href="/CreateAward">Create <%=award_txt%></a></li>
								<li class="nav"><a href="/issueAwardClass"><%=award_txt%>s</a></li>
							</ul>
						</li>
					</ul>
					<div id="login" class='nav'><a href="/logout">Log Out</a></div>
				</div>