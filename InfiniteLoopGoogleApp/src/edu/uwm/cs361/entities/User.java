package edu.uwm.cs361.entities;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
abstract public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key user_id;

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
	
	public User(String username, String password, String firstname, String lastname, String email) {
		this.username = username;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
	}

	public Key getUser_id() {
		return user_id;
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


	public String getUsername() {
		return username;
	}
}
