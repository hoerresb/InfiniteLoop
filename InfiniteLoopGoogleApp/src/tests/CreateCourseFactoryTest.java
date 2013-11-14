package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;
import factories.CreateCourseFactory;
import factories.CreateInstructorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourseFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = getPersistenceManager();

		try {
			List<User> instructors = (List<User>) pm.newQuery(User.class).execute();
			for (User user : instructors) {
				pm.deletePersistent(user);
			}
			
			List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
			for (Course course : courses) {
				pm.deletePersistent(course);
			}
			
		} finally {
			pm.close();
		}
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testErrorOnBlankClassname() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("", "10/14/2013", "10/15/2013", meetingDays, "10:30", "EMS145", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class name."));
	}

	@Test
	public void testErrorOnBlankStartDate() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "", "10/15/2013", meetingDays, "10:30", "EMS145", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class start date."));
	}
	
	@Test
	public void testErrorOnBlankEndDate() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "", meetingDays, "10:30", "EMS145", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class end date."));
	}
	
	@Test
	public void testErrorOnNonSelectedMeetingDays() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = null;
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:30", "EMS145", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Must select a meeting day."));
	}

	@Test
	public void testErrorOnBlankMeetingTime() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "", "EMS145", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a meeting time."));
	}
	
	@Test
	public void testErrorOnBlankPlace() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45", "", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class meeting place."));
	}
	
	@Test
	public void testErrorOnEmptyPaymentOptions() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = null;
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45", "EMS203", payment_options, "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter payment options."));
	}
	
	@Test
	public void testErrorOnBlankDescription() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		User teacher = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45","EMS203", payment_options, "", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class description."));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSuccess() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
		User teacher = new User(UserConstants.TEACHER_NUM, "john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45","EMS203", payment_options,"its a good class", teacher);

		assertFalse(course_fact.hasErrors());
		assertEquals(0, course_fact.getErrors().size());
		assertEquals(c.getName(), "learning101");
		assertEquals(c.getStartDate(), "10/15/2013");
		assertEquals(c.getEndDate(), "10/16/2013");
		assertEquals(c.getTime(), "10:45");
		assertEquals(c.getPlace(), "EMS203");
		assertEquals(c.getDescription(), "its a good class");
		
		//add test for meeting days
		//add test for payment options
		
		Set<Course> courses2 = teacher.getCourses();
		assertEquals(courses2.size(),1);
		assertEquals(courses2.iterator().next().getName(), "learning101");
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
