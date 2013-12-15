package edu.uwm.cs361.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	@Persistent
	Set<String> meet;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key attendance_id;
	
	//precence.put("th", true)
	
	public StudentAttendance(List<String> p, int date, String s, Set<String> meet){
		this.present = p;
		this.WeekDate = date;
		this.student = s;
		this.meet = meet;
	}
	
	public Key getAttendance_id(){
		return attendance_id;
	}
	
	public int getDate(){
		return WeekDate;
	}
	
	public Set<String> getDays(){
		return meet;
	}
	
	public List<String> getPresent(){
		return present;
	}
	
	public String getStudentName(){
		return student;
	}
	

}
