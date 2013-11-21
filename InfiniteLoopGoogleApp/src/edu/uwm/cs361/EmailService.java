package edu.uwm.cs361;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Student;

public class EmailService {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("MM/dd/yyyy");
	
	public static void SendDeadlineEmails() {
		PersistenceManager pm = getPersistenceManager();
		try {
			List<DeadlineEmailObject> studentsWithDeadlineToday = populateEmailList(pm);
			sendEmails(studentsWithDeadlineToday);
		} finally {
			pm.close();
		}
	}

	private static List<DeadlineEmailObject> populateEmailList(PersistenceManager pm) {
		List<Student> students =  getStudents(pm);
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
	private static List<Student> getStudents(PersistenceManager pm) {
		return (List<Student>) pm.newQuery(Student.class).execute();
	}
	
	private static void sendEmails(List<DeadlineEmailObject> studentsWithDeadlineToday) {
		for(DeadlineEmailObject emailObj : studentsWithDeadlineToday) {
			sendEmail(emailObj);
			System.out.println("Student: " + emailObj.getStudent().getFullName() + "\nAmount: " + emailObj.getCharge().getAmount() + 
					"\nDeadline: " + dateFormatter.format(emailObj.getCharge().getDeadline()));
		}
	}

	private static void sendEmail(DeadlineEmailObject emailObj) {
		final String username = "infiniteloopinfiniteloop@gmail.com";
		final String password = "infiniteloop19";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("infiniteloop@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("njbonnet@uwm.edu"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
