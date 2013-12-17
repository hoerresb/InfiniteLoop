package edu.uwm.cs361;

import java.util.List;
import java.util.Properties;

import javax.jdo.PersistenceManager;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.factories.PersistenceFactory;

public class EmailService {
	
	public static void SendDeadlineEmails() {
		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
		try {
			List<Student> students =  getStudents(pm);
			sendEmails(students);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private static List<Student> getStudents(PersistenceManager pm) {
		return (List<Student>) pm.newQuery(Student.class).execute();
	}
	
	private static void sendEmails(List<Student> students) {
		for(Student s : students) {
			sendEmail(s);
		}
	}

	private static void sendEmail(Student student) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("njbuwm@gmail.com", "Admin"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(student.getEmail(), student.getFullName()));
		    msg.setSubject("Deadline Reached");
			msg.setText(buildMessage(student));
			Transport.send(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	private static String buildMessage(Student student) {
		String email = "";
		email += "Dear " + student.getFullName() + " ,\n\n";
		email += "You owe us money. \n";
		email += "The deadline is today and I advise you to pay it or you will be deported to Idontpaymybills Island forever.\n";
		email += "You owe $" + student.getBalance() + ".\n\n";
		email += "Thank you,\nAutomated Emailer";
		return email;
	}
}
