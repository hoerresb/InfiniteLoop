

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



<%
	String username = null;

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
			
			  if(c.getName().equals("Teachername")){
				username = c.getValue();
			}
		 if(c.getName().equals("Adminname")){
			Cookie admin = new Cookie("Adminname", null);
			admin.setMaxAge(0);
			resp.addCookie(admin);
			resp.sendRedirect("/login.jsp");
		}
			 if(c.getName().equals("Studentname")){
				Cookie student = new Cookie("Studentname", null);
				student.setMaxAge(0);
				resp.addCookie(student);
				resp.sendRedirect("/login.jsp");
		}
			}
		}
		
		
		
		request.setAttribute("username", username);
%>





<!DOCTYPE html>
	<html>
		<head>
			<title>Monet Mall</title>
			<link type="text/css" rel="stylesheet" href="/css/stylesheet.css"/>
		</head>
		<body>
			<div id="container">
				<div id="banner"></div>
				<div id="navbar">
					<div class="nav"><a href="">Home</a></div>
					<div class="nav"><a href="">Attendance</a></div>
					<div class="nav"><a href="">Awards</a></div>
					<div class="nav"><a href="/RegisterStudentServlet">Register Student</a></div>
					<div id="login" class='nav'><a href="/logout">${username}, Log Out</a></div>
				</div>