package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateCourseFactory;
import edu.uwm.cs361.factories.PersistanceFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		pm = PersistanceFactory.getPersistenceManager();

		try {
			List<Teacher> teachers = (List<Teacher>) pm.newQuery(Teacher.class).execute();
			for (Teacher teacher : teachers) {
				pm.deletePersistent(teacher);
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
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		
		Course c = course_fact.createCourse("", "10/14/2013", "10/15/2013", meetingDays, "10:30", "EMS145", "Pay now", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class name."));
	}

	@Test
	public void testErrorOnBlankStartDate() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "", "10/15/2013", meetingDays, "10:30", "EMS145", "Pay now", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class start date."));
	}
	
	@Test
	public void testErrorOnBlankEndDate() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "", meetingDays, "10:30", "EMS145", "Pay now", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class end date."));
	}
	
	@Test
	public void testErrorOnNonSelectedMeetingDays() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = null;
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:30", "EMS145", "Pay now", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Must select a meeting day."));
	}

	@Test
	public void testErrorOnBlankMeetingTime() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "", "EMS145", "Pay now", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a meeting time."));
	}
	
	@Test
	public void testErrorOnBlankPlace() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45", "", "Pay now", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class meeting place."));
	}
	
	@Test
	public void testErrorOnEmptyPaymentOptions() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45", "EMS203", "", "its a class", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a payment option."));
	}
	
	@Test
	public void testErrorOnBlankDescription() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45","EMS203", "Pay now", "", teacher);

		assertNull(c);
		assertTrue(course_fact.hasErrors());
		assertEquals(1, course_fact.getErrors().size());
		assertTrue(course_fact.getErrors().get(0).equals("Please enter a class description."));
	}
	
	@Test
	public void testSuccess() {
		CreateCourseFactory course_fact = new CreateCourseFactory();
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45","EMS203", "Pay now","its a good class", teacher);

		assertFalse(course_fact.hasErrors());
		assertEquals(0, course_fact.getErrors().size());
		assertEquals(c.getName(), "learning101");
		assertEquals(c.getStartDate(), "10/15/2013");
		assertEquals(c.getEndDate(), "10/16/2013");
		assertEquals(c.getTime(), "10:45");
		assertEquals(c.getPlace(), "EMS203");
		assertEquals(c.getDescription(), "its a good class");
		assertEquals(c.getTeacher().getFirstName(), "fname");
		
		Iterator<String> iterator = c.getMeetingDays().iterator();
		Iterator<String> iterator2 = meetingDays.iterator();
		while(iterator.hasNext() && iterator2.hasNext()) {
			assertEquals(iterator.next(),iterator2.next());
		}

		assertEquals(c.getPaymentOption(),"Pay now");
		
		Set<Course> courses2 = teacher.getCourses();
		assertEquals(courses2.size(),1);
		assertEquals(courses2.iterator().next().getName(), "learning101");
	}
}
