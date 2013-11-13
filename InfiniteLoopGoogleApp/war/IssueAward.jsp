<%@ page import="javax.jdo.PersistenceManager" %>




<html>

<head>
        <title>Monet Mall</title>
        <link type="text/css" rel="stylesheet" href="stylesheet.css"/>
</head>

<body>

	<%

		PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();


		List<User> users = (List<User>) pm.newQuery(User.class).execute();
		if(users.size() == 0){
			pm.makePersistent(new User(UserConstants.STUDENT_NUM, "michaelds", "1234", "Michael", "DeSanta", "michaelds@uwm.edu", "111-111-1111", new Charge[0]));
			pm.makePersistent(new User(UserConstants.STUDENT_NUM, "trevorp", "5678", "Trevor", "Philips", "trevorp@uwm.edu", "111-111-1112", new Charge[0]));
			pm.makePersistent(new User(UserConstants.STUDENT_NUM, "frankc", "9011", "Franklin", "Clinton", "frankc@uwm.edu", "111-111-1113", new Charge[0]));
		}
		List<Award> awards = (List<Award>) pm.newQuery(Award.class).execute();
		if(awards.size() == 0){
			pm.makePersistent(new Award("Beginner Chef", "Prepared your first meal in class...No, you don't have to eat it.")); 
			pm.makePersistent(new Award("Intermediate Chef", "You're classmates enjoyed a meal you cooked for them."));
			pm.makePersistent(new Award("Head Chef", "Our restaurant was proud to have you in charge of the kitchen for a day."));
			pm.makePersistent(new Award("Master Chef", "Successfully catered a client's family re-union."));
		}
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
				<% for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) { %>
					<li class = "listK"><%= award.getAwardName() %>
						<ul>
							<li><%= award.getAwardDescription() %></li>
						</ul>
					</li>
				<% } %>
			</ul>

			<table class = "tableK">

                                <tr>
                                        <th>Certification </th><th></th><th> Student Name</th>
                                </tr>

				<% for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) { %>
					<tr>
                                        	<td><%=award.getAwardName() %></td><td></td>
                                	</tr>

					<% for (User student: award.getUsers()){ %>
						<tr><td></td><td><%= student.getFullName() %></td></tr>
					<% } %>

				<% } %>

			<form action="/IssueAward" method="POST">

				<label for="award">Award:</label>

					<select id="award" name="award">
		
						<% for (Award award : (List<Award>) pm.newQuery(Award.class).execute()) { %>

							<option value="<%=award.getAward_id()%>"><%=award.getAwardName()%></option>

						<% } %>
					</select><br/><br/>


				<label for="students">Student:</label>

					<select multiple id="students" name="students">

						<% for (User user : (List<User>) pm.newQuery(User.class).execute()) { %>

							<option value="<%=user.getUser_id()%>"><%=user.getFullName()%></option>
						<% } %>

					</select><br/><br/>

				<input type="submit" value="Issue Award" />
			</form>
		</div>
                
        </div>        
        
</body>


</html>


