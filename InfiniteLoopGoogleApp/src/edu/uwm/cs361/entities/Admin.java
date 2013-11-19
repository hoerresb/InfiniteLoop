package edu.uwm.cs361.entities;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Admin extends User {
	public Admin(String username, String password, String firstname, String lastname, String email) {
		super(username, password, firstname, lastname, email);
	}
}
