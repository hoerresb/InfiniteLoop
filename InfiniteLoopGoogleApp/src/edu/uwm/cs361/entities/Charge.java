package edu.uwm.cs361.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Charge {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key charge_id;
	
	@Persistent
	private double amount;
	
	@Persistent
	private Date deadline;
	
	@Persistent
	private String reason;
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("MM/dd/yyyy");
	
	//Constructor for test purposes for amounts
	public Charge(double amount) {
		this.amount = amount;
	}
	
	public Charge(double amount, Date deadline, String reason) {
		this.amount = amount;
		this.deadline = deadline;
		this.reason = reason;
	}
	
	public Key getCharge_id() {
		return charge_id;
	}

	public double getAmount() {
		return amount;
	}

	public String getFormattedDeadline() {
		return dateFormatter.format(deadline);
	}
	
	public Date getDeadline() {
		return deadline;
	}

	public String getReason() {
		return reason;
	}

}
