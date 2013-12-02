<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		
<%@include file='/templates/admin_header.jsp'%>
<div id='content'>
<%@include file='/templates/error.html'%>
	<span class="success">${success}</span><br/>
	<span class='title'>Create a Class:</span>
	<form id='form-id' method='POST' action='/createClass'>
		<h3>General Info:</h3>
		<div id="general_info_div" class="subsection">
			<label for="classname">Class name:</label>
			<input id="classname" class="text-input" type="text" name="classname" autofocus="autofocus"  value='${classname}'/><br/><br/>
		
			<label for="classstart">Class Start Date:</label>
			<input id="classstart" class="text-input" type="date" name="classstart" value='${classstart}'/><br/><br/>
			
			<label for="classend">Class End Date:</label>
			<input id="classend" class="text-input" type="date" name="classend" value='${classend}'/><br/><br/>
			
			<label for="meeting_times">Weekly Meeting Times:</label><br/>
			<div id="meeting_times">
				<label for="meeting_days">Days:</label><br/>
				<label for="meeting_times_mon"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="M" ${(type == "M") ? "selected" : ""}>M</label>
				<label for="meeting_times_tue"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="T" ${(type == "T") ? "selected" : ""}>T</label>
				<label for="meeting_times_wed"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="W" ${(type == "W") ? "selected" : ""}>W</label>
				<label for="meeting_times_thur"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="Th" ${(type == "Th") ? "selected" : ""}>Th</label>
				<label for="meeting_times_fri"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="F" ${(type == "F") ? "selected" : ""}>F</label>
				<label for="meeting_times_sat"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="S" ${(type == "S") ? "selected" : ""}>S</label>
				<label for="meeting_times_sun"><input class="meeting_time_op" type="checkbox" name="meeting_times" value="Su" ${(type == "Su") ? "selected" : ""}>Su</label><br/><br/>
			
				<label for="time">Time:</label>
				<input id="time" class="text-input" type="time" name="time"  value='${time}'/><br/><br/>
			</div>
			
			<label for="place">Meeting place:</label>
			<input id="place" class="text-input" type="text" name="place" value='${place}'/><br/><br/>
			
			<label for="payment_value">Payment value:</label>
			<span id="payment_value_span">
			$<input id="payment_value" class="text-input" type="text" name="payment_value" value="${payment_value}"/> per 
				<select id="payment_duration" name="payment_duration">
		            <optgroup>
		            	<option value="session" ${(payment_duration == "session") ? "selected" : ""}>session</option>
				     	 <option value="hour" ${(payment_duration == "hour") ? "selected" : ""}>hour</option>
				     	 <option value="day" ${(payment_duration == "day") ? "selected" : ""}>day</option>
				     	 <option value="week" ${(payment_duration == "week") ? "selected" : ""}>week</option>
				     	 <option value="month" ${(payment_duration == "month") ? "selected" : ""}>month</option>
				     	 <option value="year" ${(payment_duration == "year") ? "selected" : ""}>year</option>
		            </optgroup>
		        </select>
	        </span><br/><br/>
		</div>
		
		<label for="instr">Instructor:</label>
        <select id="instr" name="instr_options">
            <optgroup>
               <c:forEach items="${teachers}" var="teacher">
		     	 <option value="${teacher.user_id.id}">${teacher.fullName}</option>
			   </c:forEach> 
            </optgroup>
        </select><br/><br/>
                 
		<h3>Class Description:</h3>
		<p>	
			<textarea id="class_description" class="text-input" form="form-id" name="class_description" rows="5" cols="35">${class_description}</textarea><br/><br/><br/><br/>	
		</p>
		<div id="button-area">
			<button id="submit-id" type="submit">Create</button><br/><br/>
		</div>
	</form>
</div>	
<%@include file='/templates/footer.html'%>
			