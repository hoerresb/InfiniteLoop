package edu.uwm.cs361.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key user_id;
	
	@Persistent
	private int user_type; //TODO: not sure how to link this to another table (if there is a way)

	@Persistent
	private String username;
	
	@Persistent
	private String password;
	
	@Persistent
	private String firstName;

	@Persistent
	private String lastName;
	
	@Persistent
	private String email;
	
	@Persistent
	private String phone_number;
	
	@Persistent
	private Set<String> instructor_types;
	
	@Persistent
	@Unowned
	private Set<Charge> charges;
	
	@Persistent
	@Unowned
	private Set<Course> courses;
	
	
	@Persistent
	@Unowned
	private Set<Award> awards;
	
	//Constructor to create a student
	public User(int user_type, String username, String password, String firstName, String lastName, String email,
			String phone_number, Charge[] charges) {
		this.user_type = user_type;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone_number = phone_number;
		this.charges = new HashSet<Charge>(Arrays.asList(charges));
		this.awards = new HashSet<Award>();

	}
	
	public User(Course[] student_courses, int user_type, String username, String password, String firstName, String lastName, String email,
			String phone_number) {
		this.user_type = user_type;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone_number = phone_number;
		this.courses = new HashSet<Course>(Arrays.asList(student_courses));
		this.awards = new HashSet<Award>();

	}
	
	public User(int user_type, String username, String password, String firstName, String lastName, String email,
			String phone_number, String[] instructor_types) {
		this.user_type = user_type;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone_number = phone_number;
		this.instructor_types = new HashSet<String>(Arrays.asList(instructor_types));
		this.awards = new HashSet<Award>();

	}

	public Key getUser_id() {
		return user_id;
	}
	
	public int getUser_type() {
		return user_type;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhoneNumber() {
		return phone_number;
	}
	
	public Set<String> getInstructorTypes() {
		return instructor_types;
	}
	
	public void setCourses(Course[] course) {
		this.courses = new HashSet<Course>(Arrays.asList(course));
	}
	
	public Set<Course> getCourses() {
		return courses;
	}
	
	public void setCharges(Charge[] charge) {
		this.charges = new HashSet<Charge>(Arrays.asList(charge));
	}
	
	public Set<Charge> getCharges() {
		return charges;
	}
	
	public Set<Award> getAwards() {
		return awards;
	}
}