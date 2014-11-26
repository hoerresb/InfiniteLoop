package edu.uwm.cs361.factories;

import java.util.ArrayList;
import java.util.List;   

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import edu.uwm.cs361.entities.Admin;
import edu.uwm.cs361.entities.Charge;

public class loginFactory {
	
	PersistenceManager pm = getPersistenceManager();
	private List<String> errors = new ArrayList<String>();
	private List<Admin> users = new ArrayList<Admin>();
	
	public Admin createUser( String username, String password, String firstName, String lastName, String email) {
		
			if(username.trim().isEmpty()){
				errors.add("Username is required");
			}
			if(password.trim().isEmpty()){
				errors.add("Password is required");
			} 
			
			if(users.size()>0){
				if(users.get(0).getUsername().equals(username) && !users.get(0).getPassword().equals(password)){
					errors.add("Incorrect Password");
				}
			}
			
			if(hasErrors()) {
				return null;
			} else {
				 Admin u = new Admin(username, password,firstName,lastName,email);
				 users.add(u);
				 return u;
			}
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	} 
	
	public List<Admin> getUsers(){
		return new ArrayList<Admin>(users);
	}
	
	public boolean doLoginIncorrectPassword(String username, String password){
		if(users.size()>0){
			for(int i = 0 ; i < users.size(); i++){
			if(users.get(i).getUsername().equals(username) && !users.get(i).getPassword().equals(password)){
				errors.add("Incorrect Password");
				return false;
			}
		}
	
	}
		return true;
	}
	
	
	public boolean doLoginIncorrectUsername(String username, String password){
		if(users.size()>0){
			for(int i = 0 ; i < users.size(); i++){
			if(!users.get(i).getUsername().equals(username)){
				errors.add("Incorrect Username");
				return false;
			}
		}
	
	}
		return true;
	}
	
	public boolean doLoginCorrectLogin(String username, String password){
		if(users.size()>0){
			for(int i = 0 ; i < users.size(); i++){
			if((users.get(i).getUsername().equals(username)) && (users.get(i).getPassword().equals(password))){
				return true;
			}
		}
	
	}
		return false;
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
