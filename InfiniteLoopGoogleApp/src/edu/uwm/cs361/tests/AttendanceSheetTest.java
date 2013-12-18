package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.AttendanceSheet;
import edu.uwm.cs361.entities.Admin;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.StudentAttendance;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.factories.AttendanceSheetFactory;
import edu.uwm.cs361.factories.PersistenceFactory;
import edu.uwm.cs361.factories.loginFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AttendanceSheetTest {

	
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;


	@Before
	public void setUp() {
		helper.setUp();
		pm = PersistenceFactory.getPersistenceManager();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void blankStudentName(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
		//Course(String name, Date startDate, Date endDate,
			//	Set<String> meetingDays, String time, String place,
			//	String payment_options, String description, Teacher teacher) 
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		daysPresent.add("1");
		daysPresent.add("0");
		int date = 4;
		String studentName = "";
		Set<String> meetDays = new HashSet();
		meetDays.add("M");
		meetDays.add("Tu");
		Course course = new Course("New course", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		assertNull(a);
		assertTrue(att.hasErrors());
		assertEquals(1, att.getErrors().size());
		assertTrue(att.getErrors().get(0).equals("Student's name is required."));
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void emptyMeetDays(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		daysPresent.add("1");
		daysPresent.add("0");
		int date = 4;
		String studentName = "Hi";
		Set<String> meetDays = new HashSet();
		Course course = new Course("New course", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		assertNull(a);
		assertTrue(att.hasErrors());
		assertEquals(1, att.getErrors().size());
		assertTrue(att.getErrors().get(0).equals("Meeting Days are required."));
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void emptyDaysPresent(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		int date = 4;
		String studentName = "Hi";
		Set<String> meetDays = new HashSet();
		meetDays.add("T");
		meetDays.add("TH");
		Course course = new Course("New course", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		assertNull(a);
		assertTrue(att.hasErrors());
		assertEquals(1, att.getErrors().size());
		assertTrue(att.getErrors().get(0).equals("Days present is required."));
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void emptyMultiple(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		int date = 4;
		String studentName = "";
		Set<String> meetDays = new HashSet();
		Course course = new Course("New course", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		assertNull(a);
		assertTrue(att.hasErrors());
		assertEquals(3, att.getErrors().size());
		assertTrue(att.getErrors().get(0).equals("Days present is required."));
		assertTrue(att.getErrors().get(1).equals("Student's name is required."));
		assertTrue(att.getErrors().get(2).equals("Meeting Days are required."));
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void invlaidClassName(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		daysPresent.add("1");
		daysPresent.add("0");
		int date = 4;
		String studentName = "Hi";
		Set<String> meetDays = new HashSet();
		meetDays.add("M");
		meetDays.add("Tu");
		Course course = new Course("", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		assertNull(a);
		assertTrue(att.hasErrors());
		assertEquals(1, att.getErrors().size());
		assertTrue(att.getErrors().get(0).equals("Course is required."));
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void invalidWeek(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
		//Course(String name, Date startDate, Date endDate,
			//	Set<String> meetingDays, String time, String place,
			//	String payment_options, String description, Teacher teacher) 
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		daysPresent.add("1");
		daysPresent.add("0");
		int date = 0;
		String studentName = "Hi";
		Set<String> meetDays = new HashSet();
		meetDays.add("M");
		meetDays.add("Tu");
		Course course = new Course("New course", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		
		assertTrue(att.hasErrors());
		assertEquals(1, att.getErrors().size());
		assertTrue(att.getErrors().get(0).equals("Date is required."));
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void validAttendance(){
		AttendanceSheetFactory att = new AttendanceSheetFactory();
		//Course(String name, Date startDate, Date endDate,
			//	Set<String> meetingDays, String time, String place,
			//	String payment_options, String description, Teacher teacher) 
			
		Teacher teacher = new Teacher("teacher", "teacher", "teacher", "teacher", "teacher@t.com");	
		Date startDate = new Date (2013, 05, 17);
		Date endDate = new Date (2013, 05, 21);
		List<String> daysPresent = new ArrayList<String>();
		daysPresent.add("1");
		daysPresent.add("0");
		int date = 4;
		String studentName = "Hi";
		Set<String> meetDays = new HashSet();
		meetDays.add("M");
		meetDays.add("Tu");
		Course course = new Course("New course", startDate, endDate, meetDays, "8" , "here"	, "pay me"	,"no", teacher );
		
		StudentAttendance a = att.createAttendanceSheet(daysPresent, date, studentName, meetDays, course);
		
		
		assertFalse(att.hasErrors());
		assertEquals(0, att.getErrors().size());
		
		
		
	}
	
	
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
	
}
