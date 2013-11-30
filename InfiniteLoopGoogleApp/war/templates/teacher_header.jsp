<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.servlet.http.Cookie" %>


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
					<div class="nav"><a href="/TeacherHome">Home</a></div>
					<div class="nav"><a href="">Attendance</a></div>
					<div class="nav"><a href="">Awards</a></div>
					<div class="nav"><a href="/RegisterStudentServlet">Register Student</a></div>
					<div id="login" class='nav'><a href="/logout">Log Out</a></div>
				</div>