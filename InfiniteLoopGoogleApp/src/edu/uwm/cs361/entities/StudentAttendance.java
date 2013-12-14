package edu.uwm.cs361.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
@PersistenceCapable
public class StudentAttendance {
	
	@Persistent
	int WeekDate;
	
	@Persistent
	String student;
	
	@Persistent
	List<String> present;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key attendance_id;
	
	//precence.put("th", true)
	
	public StudentAttendance(List<String> p, int date, String s){
		this.present = p;
		this.WeekDate = date;
		this.student = s;
	}
	
	public Key getAttendance_id(){
		return attendance_id;
	}
	
	public int getDate(){
		return WeekDate;
	}
	
	public List<String> getList(){
		return present;
	}
	
	public String get_a_Student(){
		return student;
	}
	

}
