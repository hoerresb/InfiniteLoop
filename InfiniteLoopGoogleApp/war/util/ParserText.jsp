<%@ page import="java.io.*" %>

<%
	FileReader in = new FileReader("util/ParserText.txt");
	BufferedReader br = new BufferedReader(in);
	String line, var, val;
	String[] var_val;
	String instructor_txt = "", student_txt = "", class_txt = "", award_txt = "", charge_txt = "";
	while ((line = br.readLine()) != null) {
		var_val = line.split("=");
		var = var_val[0].trim();
		val = var_val[1].trim();
		val= val.substring(1, val.length()-1);
		if (var.equals("instructor")) {
			instructor_txt = val;
		} else if (var.equals("student")) {
			student_txt = val;
		} else if (var.equals("class")) {
			class_txt = val;
		} else if (var.equals("award")) {
			award_txt = val;
		} else if (var.equals("charge")) {
			charge_txt = val;
		}
	}
%>