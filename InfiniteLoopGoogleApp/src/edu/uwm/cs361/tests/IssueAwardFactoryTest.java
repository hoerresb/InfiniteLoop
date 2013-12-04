package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.factories.IssueAwardFactory;
import edu.uwm.cs361.factories.PersistanceFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IssueAwardFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = PersistanceFactory.getPersistenceManager();

		List<Award> awards = (List<Award>) pm.newQuery(Award.class).execute();

		for (Award award : awards) {
			pm.deletePersistent(user);
		}
		pm.flush();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testErrorOnBlankAwardName() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Teacher teacher = new Teacher("username","password1","fname","lname", "email",
					      "8478478478", new String[] {"teacher1","teacher2"});
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Course c = new Course("cooking","10/14/2013", "10/15/2013", meetingDays,
					"10:30", "EMS145", "Pay now", "its a class", teacher);
		Award a = aFact.createAward(c, "", "congrats");

		assertNull(a);
		assertTrue(aFact.hasErrors());
		assertEquals(1, aFact.getErrors().size());
		assertTrue(aFact.getErrors().get(0).equals("Please enter an award name."));
	}

	@Test
	public void testErrorOnBlankAwardDescription() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Teacher teacher = new Teacher("username","password1","fname","lname", "email",
					      "8478478478", new String[] {"teacher1","teacher2"});
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Course c = new Course("cooking","10/14/2013", "10/15/2013", meetingDays,
					"10:30", "EMS145", "Pay now", "its a class", teacher);
		Award a = aFact.createAward(c, "award0", "");

		assertNull(a);
		assertTrue(aFact.hasErrors());
		assertEquals(1, aFact.getErrors().size());
		assertTrue(aFact.getErrors().get(0).equals("Please enter an award description."));
	}

	@Test
	public void testErrorOnNullCourse() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Award a = aFact.createAward(null, "award0", "congrats");

		assertNull(a);
		assertTrue(aFact.hasErrors());
		assertEquals(1, aFact.getErrors().size());
		assertTrue(aFact.getErrors().get(0).equals("Please specify a course."));
	}

	@Test
	public void testSuccess() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Teacher teacher = new Teacher("username","password1","fname","lname", "email",
					      "8478478478", new String[] {"teacher1","teacher2"});
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Course c = new Course("cooking","10/14/2013", "10/15/2013", meetingDays,
					"10:30", "EMS145", "Pay now", "its a class", teacher);
		Award a = aFact.createAward(c, "award0", "congrats");

		assertFalse(aFact.hasErrors());
		assertEquals(0, aFact.getErrors().size());
		assertEquals(u.getAwardName(), "award0");
		assertEquals(u.getAwardDescription(), "congrats");
	}
