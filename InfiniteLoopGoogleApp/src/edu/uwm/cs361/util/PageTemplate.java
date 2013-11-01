package edu.uwm.cs361.util;

import java.util.List;

public class PageTemplate {

	public static String printAdminHeader() {
		return "<!DOCTYPE html>" +
				"<html>" +
				"	<head>" +
				"		<title>Monet Mall</title>" +
				"		<link type='text/css' rel='stylesheet' href='/css/stylesheet.css'/>" +
				"	</head>" +
				"	<body>" +
				"		<div id='container'>" +
				"			<div id='banner'></div>" +
				"			<div id='navbar'>" +
				"				<div class='nav'><a href=''>Home</a></div>" +
				"				<div class='nav'><a href=''>Create Class</a></div>" +
				"				<div class='nav'><a href='/createInstructor'>Create Instructor</a></div>" +
				"				<div class='nav'><a href='/studentCharges'>Student Charges</a></div>" +	
				"				<div id='login' class='nav'><a href='login.html'>Log Out</a></div>" +
				"			</div>";
	}
	
	public static String printTeacherHeader() {
		return "<!DOCTYPE html>" +
				"<html>" +
				"	<head>" +
				"		<title>Monet Mall</title>" +
				"		<link type='text/css' rel='stylesheet' href='/css/stylesheet.css'/>" +
				"	</head>" +
				"	<body>" +
				"		<div id='container'>" +
				"			<div id='banner'></div>" +
				"			<div id='navbar'>" +
				"				<div class='nav'><a href=''>Home</a></div>" +
				"				<div class='nav'><a href=''>Attendance</a></div>" +
				"				<div class='nav'><a href=''>Awards</a></div>" +
				"				<div class='nav'><a href=''>Register Student</a></div>" +
				"				<div id='login' class='nav'><a href='/login'>Log Out</a></div>" +
				"			</div>";
	}
	
	public static String printStudentHeader() {
		return "<!DOCTYPE html>" +
				"<html>" +
				"	<head>" +
				"		<title>Monet Mall</title>" +
				"		<link type='text/css' rel='stylesheet' href='/css/stylesheet.css'/>" +
				"	</head>" +
				"	<body>" +
				"		<div id='container'>" +
				"			<div id='banner'></div>" +
				"			<div id='navbar'>" +
				"				<div class='nav'><a href='student_home.html'>Home</a></div>" +
				"				<div class='nav'><a href='achievements.html'>Achievements</a></div>" +
				"				<div id='login' class='nav'><a href='login.html'>Log Out</a></div>" +
				"			</div>";
	}
	
	public static String printFooter() {
		return "		</div>" +
				"	</body>" +
				"</html>";
	}
	
	public static String printErrors(List<String> errors) {
		String result = "";
		if (errors.size() > 0) {
			result += "<ul>";

			for (String error : errors) {
				result += "  <li class='error'>" + error + "</li>";
			}

			result += "</ul>";
		}
		return result;
	}
}
