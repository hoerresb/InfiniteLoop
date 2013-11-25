<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="javax.jdo.PersistenceManager" %>
<html>

<head>
        <title>Monet Mall</title>
        <link type="text/css" rel="stylesheet" href="stylesheet.css"/>
</head>

<body>

	<%
		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();



	%>



        <div id="container">
                <div id="banner"></div>

                <div id="navbar">
                        <div class="nav"><a href="teacher_home.html">Home</a></div>
                        <div class="nav"><a href="attendance.html">Attendance</a></div>
                        <div class="nav"><a href="awards.html">Awards</a></div>
                        <div class="nav"><a href="register_student.html">Register Student</a></div>
                        <div id="login" class="nav"><a href="login.html">Log Out</a></div>
                </div>

                <div id="content">

			<span class="title">Cooking For Dummies</span><br/>
                        <span class="title">Certifications</span>

			<ul>
				<%
					for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) {
				%>
					<li class = "listK"><%=award.getAwardName()%>
						<ul>
							<li><%=award.getAwardDescription()%></li>
						</ul>
					</li>
				<%
					}
				%>
			</ul>

			<table class = "tableK">

                                <tr>
                                        <th>Certification </th><th></th><th> Student Name</th>
                                </tr>

				<%
					for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) {
				%>
					<tr>
                                        	<td><%=award.getAwardName()%></td><td></td>
                                	</tr>

					<%
						for (old_User student: award.getUsers()){
					%>
						<tr><td></td><td><%=student.getFullName()%></td></tr>
					<%
						}
					%>

				<%
					}
				%>

			<form action="/IssueAward" method="POST">

				<label for="award">Award:</label>

					<select id="award" name="award">
		
						<%
									for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) {
								%>

							<option value="<%=award.getAward_id().getid()%>"><%=award.getAwardName()%></option>

						<%
							}
						%>
					</select><br/><br/>


				<label for="students">Student:</label>

					<select multiple id="students" name="students">

						<%
							for (old_User user : (List<old_User>) pm.newQuery(old_User.class).execute()) {
						%>

							<option value="<%=user.getUser_id().getid()%>"><%=user.getFullName()%></option>
						<% } %>

					</select><br/><br/>

				<input type="submit" value="Issue Award" />
			</form>
		</div>
                
        </div>        
        
</body>


</html>


