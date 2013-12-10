package edu.uwm.cs361.tests;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.*;
import edu.uwm.cs361.factories.PersistenceFactory;
import edu.uwm.cs361.factories.StudentChargesFactory;

import java.util.*;

import javax.jdo.*;

import org.junit.*;

public class StudentChargesFactoryTest {
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
	
	@Test
	public void testErrorOnBlankAmount() {
		StudentChargesFactory charge_fact = new StudentChargesFactory();
		String amount = "";
		Date deadline = new Date(2013,8,12);
		String reason = "Because I said so.";
		Student student = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com");
		
		Charge c = charge_fact.createCharge(student, amount, deadline, reason);
		
		assertNull(c);
		assertTrue(charge_fact.hasErrors());
		assertEquals(1, charge_fact.getErrors().size());
		assertTrue(charge_fact.getErrors().get(0).equals("Please enter an amount."));
	}
	
	@Test
	public void testErrorOnBlankDate() {
		StudentChargesFactory charge_fact = new StudentChargesFactory();
		String amount = "6";
		Date deadline = null;
		String reason = "Because I said so.";
		Student student = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com");
		
		Charge c = charge_fact.createCharge(student, amount, deadline, reason);
		
		assertNull(c);
		assertTrue(charge_fact.hasErrors());
		assertEquals(1, charge_fact.getErrors().size());
		assertTrue(charge_fact.getErrors().get(0).equals("Please enter a date."));
	}
	
	@Test
	public void testErrorOnBlankReason() {
		StudentChargesFactory charge_fact = new StudentChargesFactory();
		String amount = "6";
		Date deadline = new Date(2013,8,12);
		String reason = "";
		Student student = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com");
		
		Charge c = charge_fact.createCharge(student, amount, deadline, reason);
		
		assertNull(c);
		assertTrue(charge_fact.hasErrors());
		assertEquals(1, charge_fact.getErrors().size());
		assertTrue(charge_fact.getErrors().get(0).equals("Please enter a reason."));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSuccess () {
		StudentChargesFactory charge_fact = new StudentChargesFactory();
		String amount = "6";
		Date deadline = new Date(2013,8,12);
		String reason = "Because I said so.";
		Student student = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com");
		
		Charge c = charge_fact.createCharge(student, amount, deadline, reason);
		student.getCharges().add(c);
		assertFalse(charge_fact.hasErrors());
		assertEquals(0, charge_fact.getErrors().size());
		assertEquals(c.getAmount(), 6, 0);
		assertEquals(c.getDeadline(), new Date(2013,8,12));
		assertEquals(c.getReason(), "Because I said so.");
		
		Set<Charge> charges = student.getCharges();
		assertEquals(1,charges.size());
	}
}
