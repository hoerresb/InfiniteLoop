package edu.uwm.cs361.tests;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.*;

import java.util.*;

import javax.jdo.*;

import org.junit.*;

public class StudentChargesFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;
	private List<Student> students = new ArrayList<Student>();

	@Before
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
		Student user = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com");
		students.add(user);
		assertEquals(1, students.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddCharges () {
		double amount = 0;
		Date deadline = new Date();
		Student student = new Student("student", "student", "Student_fn", "Student_ln", "student@student.com");
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
