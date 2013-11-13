package edu.uwm.cs361.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
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
	private String startDate;

	@Persistent
	private String endDate;

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

	public Course(String name, String classstart, String classend,
			String[] meetingDays, String time, String place,
			String[] options_array, String description) {
		super();
		this.startDate = classstart;
		this.endDate = classend;
		this.meetingDays = meetingDays;
		this.time = time;
		this.place = place;
		this.payment_options = new HashSet<String>(Arrays.asList(options_array));;
		this.description = description;
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
