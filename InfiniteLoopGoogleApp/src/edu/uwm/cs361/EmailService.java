package edu.uwm.cs361;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Student;

public class EmailService {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("MM/dd/yyyy");
	
	public static void SendDeadlineEmails() {
		List<DeadlineEmailObject> studentsWithDeadlineToday = populateEmailList();
		sendEmails(studentsWithDeadlineToday);
	}

	private static List<DeadlineEmailObject> populateEmailList() {
		List<Student> students =  getStudents();
		List<DeadlineEmailObject> studentsWithDeadlineToday = new ArrayList<DeadlineEmailObject>();
		String today = dateFormatter.format(System.currentTimeMillis());
		
		for(Student student : students) {
			Set<Charge> charges = student.getCharges();
			if(charges != null) {
				for(Charge charge : charges) {
					String deadline = dateFormatter.format(charge.getDeadline());
					if(deadline.equals(today)) {
						studentsWithDeadlineToday.add(new DeadlineEmailObject(student, charge));
					}
				}
			}
		}
		return studentsWithDeadlineToday;
	}

	@SuppressWarnings("unchecked")
	private static List<Student> getStudents() {
		PersistenceManager pm = getPersistenceManager();
		try {
			return (List<Student>) pm.newQuery(Student.class).execute();
		} finally {
			pm.close();
		}
	}
	
	private static void sendEmails(List<DeadlineEmailObject> studentsWithDeadlineToday) {
		for(DeadlineEmailObject emailObj : studentsWithDeadlineToday) {
			//sendEmail(emailObj);
			System.out.println("Student: " + emailObj.getStudent().getFullName() + "\nAmount: " + emailObj.getCharge().getAmount() + 
					"\nDeadline: " + dateFormatter.format(emailObj.getCharge().getDeadline()));
		}
	}

	private static PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
