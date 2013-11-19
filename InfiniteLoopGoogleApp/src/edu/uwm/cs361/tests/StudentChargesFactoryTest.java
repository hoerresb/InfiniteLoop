package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentChargesFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;
	private List<Student> students = new ArrayList<Student>();

	@Before
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void setUp() {
		helper.setUp();
		pm = getPersistenceManager();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testNoStudents() {
		assertEquals(0, students.size());
	}
	
	@Test
	public void testOneStudents() {
		Charge[] charges = {new Charge(12, new Date(2013,11,31), ""), new Charge(15, new Date(2013,11,31), "") , new Charge(18, new Date(2013,11,31), "")};
		Student user = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com", "111-111-1111", charges);
		students.add(user);
		assertEquals(1, students.size());
	}
	
	@Test
	public void testAddCharges () {
		double amount = 0;
		Date deadline = new Date();
		Charge[] charges = {};
		Student student = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com", "111-111-1111", charges);
		students.add(student);
		
		student.getCharges().add(new Charge(6, new Date(2013,8,12), ""));
		
		for(Student student1: students) {
			for(Charge charge: student1.getCharges()) {
				amount = charge.getAmount();
				deadline = charge.getDeadline();
			}
		}
		assertTrue(amount == 6);
		assertTrue(deadline.equals(new Date(2013,8,12)));
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
