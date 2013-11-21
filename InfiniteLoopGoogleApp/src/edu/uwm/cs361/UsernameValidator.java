package edu.uwm.cs361;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import edu.uwm.cs361.entities.Admin;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;

public class UsernameValidator {

	public static boolean usernameExists(String username) {
		boolean adminExistsWithUsername = isAdminWithUsername(username);
		boolean teacherExistsWithUsername = isTeacherWithUsername(username);
		boolean studentExistsWithUsername = isStudentWithUsername(username);
		
		if(adminExistsWithUsername || teacherExistsWithUsername || studentExistsWithUsername) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean isStudentWithUsername(String username) {
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Student.class);
    	q.setFilter("username == nameParameter");
		q.declareParameters("String nameParameter");
		List<Student> execute = (List<Student>) q.execute(username);
    	if (execute.size() > 0) {
    	    return true;
    	} else {
    		return false;
    	}
	}

	@SuppressWarnings("unchecked")
	private static boolean isTeacherWithUsername(String username) {
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Teacher.class);
	   	q.setFilter("username == nameParameter");
		q.declareParameters("String nameParameter");
		List<Teacher> execute = (List<Teacher>) q.execute(username);
	   	if (execute.size() > 0) {
	   	    return true;
	   	} else {
	   		return false;
	   	}
	}

	@SuppressWarnings("unchecked")
	private static boolean isAdminWithUsername(String username) {
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Admin.class);
	   	q.setFilter("username == nameParameter");
		q.declareParameters("String nameParameter");
		List<Admin> execute = (List<Admin>) q.execute(username);
	   	if (execute.size() > 0) {
	   	    return true;
	   	} else {
	   		return false;
	   	}
	}
	
	private static PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
