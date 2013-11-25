package edu.uwm.cs361.entities;

import java.util.Date;
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
	private String startDate;

	@Persistent
	private String endDate;

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

	public Course(String name, String startDate, String endDate,
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

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public Set<String> getMeetingDays() {
		return meetingDays;
	}

	public String getTime() {
		return time;
	}

	public String getPlace() {
		return place;
	}

	public String getPaymentOption() {
		return payment_option;
	}
	
	public double getPayment_amount() {
		String[] amount_duration = this.payment_option.split(" per ");
		return Double.parseDouble(amount_duration[0]);
	}

	public String getDescription() {
		return description;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
}