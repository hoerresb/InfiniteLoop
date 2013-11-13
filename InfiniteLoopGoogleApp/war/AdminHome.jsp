<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="edu.uwm.cs361.entities.User" %>
<%@ page import="javax.servlet.http.Cookie" %>



<%
String username = null;

		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("Adminname")) {
					username = c.getValue();
				}
			}
		}
%>


<!DOCTYPE html> 
	<html>
						<head>
							<title>Monet Mall</title>
							<link type='text/css' rel='stylesheet' href='/css/stylesheet.css'/> 
						</head> 
						<body>
							<div id="container"> 
								<div id="banner"></div> 
								<div id="content">
									<h2> Welcome, <%=username%> </h2>
								</div>
							</div>
						</body>
	</html>	
							
									