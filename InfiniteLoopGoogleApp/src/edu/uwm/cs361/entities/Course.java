package edu.uwm.cs361.entities;

import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

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
	private String[] meetingDays;
	
	@Persistent
	private String time;

	@Persistent
	private String place;

	@Persistent
	private Set<String> payment_options; //TODO: need a new table? How are we assigning these ...
	
	@Persistent
	private String description;

	public Course(String name, Date startDate, Date endDate,
			String[] meetingDays, String time, String place,
			Set<String> payment_options, String description) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.meetingDays = meetingDays;
		this.time = time;
		this.place = place;
		this.payment_options = payment_options;
		this.description = description;
	}

	public Key getCourse_id() {
		return course_id;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String[] getMeetingDays() {
		return meetingDays;
	}

	public String getTime() {
		return time;
	}

	public String getPlace() {
		return place;
	}

	public Set<String> getPayment_options() {
		return payment_options;
	}

	public String getDescription() {
		return description;
	}
}