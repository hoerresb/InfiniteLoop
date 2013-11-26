package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.DeadlineEmailObject;
import edu.uwm.cs361.EmailService;
import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.PersistanceFactory;

public class EmailServiceTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = PersistanceFactory.getPersistenceManager();

		List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();

		for (Student user : students) {
			pm.deletePersistent(user);
		}
		pm.flush();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testPopulationOfEmailList() {
		Date today = new Date();
		Date yesterday = new Date();
		yesterday.setDate(yesterday.getDate()-1);
		Date tomorrow = new Date();
		tomorrow.setDate(tomorrow.getDate()+1);
		
		//new Charge(double amount, Date deadline, String reason)
		Charge c1 = new Charge(11.0,today,"Ate too much turkey"); //within range TODAY NOW NORMAL
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		Charge c2 = new Charge(28.0,today,"Ate too much stuffing"); // within range TODAY 00:00:00 BOUNDRY
		today.setHours(23);
		today.setMinutes(59);
		today.setSeconds(59);
		Charge c3 = new Charge(28.0,today,"Ate too much ham"); // within range TODAY 23:59:59 BOUNDRY
		yesterday.setHours(23);
		yesterday.setMinutes(59);
		yesterday.setSeconds(59);
		Charge c4 = new Charge(28.0,yesterday,"Ate too much sweet patatoes"); // out of range YESTERDAY 23:59:59 BOUNDRY
		tomorrow.setHours(23);
		tomorrow.setMinutes(59);
		tomorrow.setSeconds(59);
		Charge c5 = new Charge(28.0,tomorrow,"Ate too much mashed potatoes"); // out of range TOMORROW 00:00:00 BOUNDRY
		Charge c6 = new Charge(28.0, new Date(680689800),"Ate too much ... nap time"); // out of range My Birthday 7/28/1991
		
		//new Student(String username, String password, String firstname, String lastname, String email, Set<Course> courses, Set<Teacher> teachers, Set<Award> awards, Set<Charge> charges)
		Student s1 = new Student("A", "pw", "A", "A", "A@A.A", null, null, null, new HashSet<Charge>(Arrays.asList(new Charge[] {c1})));
		Student s2 = new Student("B", "pw", "B", "B", "B@B.B", null, null, null, new HashSet<Charge>(Arrays.asList(new Charge[] {c2})));
		Student s3 = new Student("C", "pw", "C", "C", "C@C.C", null, null, null, new HashSet<Charge>(Arrays.asList(new Charge[] {c3})));
		Student s4 = new Student("D", "pw", "D", "D", "D@D.D", null, null, null, new HashSet<Charge>(Arrays.asList(new Charge[] {c4})));
		Student s5 = new Student("E", "pw", "E", "E", "E@E.E", null, null, null, new HashSet<Charge>(Arrays.asList(new Charge[] {c5})));
		Student s6 = new Student("F", "pw", "F", "F", "F@F.F", null, null, null, new HashSet<Charge>(Arrays.asList(new Charge[] {c6})));
		
		List<String> studentsThatHaveChargeWithinDeadline = Arrays.asList("A", "B", "C");
		
		pm.makePersistent(s1);
		pm.makePersistent(s2);
		pm.makePersistent(s3);
		pm.makePersistent(s4);
		pm.makePersistent(s5);
		pm.makePersistent(s6);
		
		List<DeadlineEmailObject> emailList = EmailService.populateEmailList(pm);
		for(DeadlineEmailObject obj : emailList) {
			assertTrue(studentsThatHaveChargeWithinDeadline.contains(obj.getStudent().getFirstName()));
			assertEquals(studentsThatHaveChargeWithinDeadline.size(), emailList.size());
		}
	}
}
