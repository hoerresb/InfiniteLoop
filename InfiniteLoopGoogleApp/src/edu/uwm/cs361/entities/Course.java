package edu.uwm.cs361.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Course {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key course_id;
	
	@Persistent
	private String name;
	
	@Persistent
	private Date startDate;

	@Persistent
	private Date endDate;

	@Persistent
	private Set<String> meetingDays;
	
	@Persistent
	private String time;

	@Persistent
	private String place;

	@Persistent
	private String payment_option;
	
	@Persistent
	private double payment_amount;
	
	@Persistent
	private String description;
	
	@Persistent
	@Unowned
	private Teacher teacher;
	
	@Persistent
	private Set<Award> awards;
	
	@Persistent
	@Unowned
	private Set<Student> students = new HashSet<Student>();
	
	@Persistent
	@Unowned
	private List<StudentAttendance> attendance = new ArrayList<StudentAttendance>();

	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("MM/dd/yyyy");

	public Course(String name, Date startDate, Date endDate,
			Set<String> meetingDays, String time, String place,
			String payment_options, String description, Teacher teacher) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.meetingDays = meetingDays;
		this.time = time;
		this.place = place;
		this.payment_option = payment_options;
		this.description = description;
		this.teacher = teacher;
	}

	public Key getCourse_id() {
		return course_id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String newName){
		name = newName;
		
	}

	public Date getStartDate() {
		return  startDate;
	}
	
	public void setStartDate(Date newStartDate){
		startDate = newStartDate;
	}
	
		
	
	public String getStartDateFormatted() {
		return dateFormatter.format(startDate);
	}
	
	

	public String getEndDateFormatted() {
		return dateFormatter.format(endDate);
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date newEndDate){
		
		endDate = newEndDate;
	}

	public Set<String> getMeetingDays() {
		return meetingDays;
	}
	
	public void setMeetingDays(Set<String> meetingDays2) {
		
		meetingDays = meetingDays2;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String newTime){
		
		time = newTime;
	}

	public String getPlace() {
		return place;
	}
	
	public void setPlace(String newPlace){
		place = newPlace;
	}
	

	public String getPaymentOption() {
		return payment_option;
	}
	
	public void setPaymentOptions(String newPaymentOption){
		
		payment_option = newPaymentOption;
	}
	
	public double getPayment_amount() {
		String[] amount_duration = this.payment_option.split(" per ");
		return Double.parseDouble(amount_duration[0]);
	}
	
	

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription){
		description = newDescription;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher newTeacher){
		
		teacher = newTeacher;
	}
	
	public Set<Award> getAwards() {
		return awards;
	}
	
	public List<StudentAttendance> getAttendance(){
		return attendance;
	}
	
	public Set<Student> getStudents() {
		return students;
	}
}