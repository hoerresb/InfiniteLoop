package edu.uwm.cs361.entities;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class User
{
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
	
	public User(int user_type, String username, String password, String firstName, String lastName, String email)
	{
		this.user_type = user_type;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Key getUser_id()
	{
		return user_id;
	}
	
	public int getUser_type()
	{
		return user_type;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}

	public String getEmail()
	{
		return email;
	}
}
