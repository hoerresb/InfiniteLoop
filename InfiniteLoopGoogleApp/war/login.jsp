<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.jdo.JDOHelper" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<@% page import="edu.uwm.cs361.entities.User" %>

<!DOCTYPE html> 
	<html>
						<head>
							<title>Monet Mall</title>
							<link type=\"text/css\" rel=\"stylesheet\" href='/css/stylesheet.css'/> 
						</head> 
						<body>
							<div id="container"> 
								<div id="banner"></div> 
								<div id="content">
									<span class="title">Log In</span>
									<form id="form-id" method="POST" action='/login'> 
										<label for="username-id">Username:</label>
										<input id="username-id" class="text-input" type="text" name="username" autofocus="autofocus" ><br/><br/> 
										<label for="password-id">Password:</label>
										<input id="password-id" class="text-input" type="password" name="password" ><br/><br/> 
										<div id="button-area">
											<button id="submit-id" type="submit">Login</button><br/><br/> 
										</div> 
									</form> 
								</div> 
							</div> 
						</body> 
					</html>