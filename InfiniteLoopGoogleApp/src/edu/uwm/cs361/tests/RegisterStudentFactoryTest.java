package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateCourseFactory;
import edu.uwm.cs361.factories.RegisterStudentFactory;

import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegisterStudentFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;
	private Set<Course> courses = new HashSet<Course>();

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = getPersistenceManager();

		try {
			List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();

			for (Student student : students) {
				pm.deletePersistent(student);
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
	public void testErrorOnBlankFirstname() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
		courses.add(c);
		
		Student u = stud_fact.createStudent("username","password","password","","lname", "email", courses);

		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("First Name is required."));
	}
	
	@Test
	public void testErrorOnBlankLastname() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
		courses.add(c);
		
		Student u = stud_fact.createStudent("username","password","password","fname","", "email", courses);

		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Last Name is required."));
	}
	
	@Test
	public void testErrorOnBlankEmail() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
		courses.add(c);
		
		Student u = stud_fact.createStudent("username","password1","password1","fname","lname", "", courses);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Email is required."));
	}

	@Test
	public void testErrorOnBlankUsername() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
		courses.add(c);
		
		Student u = stud_fact.createStudent("","password","password","fname","lname", "email", courses);

		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Username is required."));
	}
	
	@Test
	public void testErrorOnBlankPassword() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
		courses.add(c);
		
		Student u = stud_fact.createStudent("username","","password","fname","lname", "email", courses);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Password is required."));
	}
	
	@Test
	public void testErrorOnNonMatchingPasswords() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
		courses.add(c);
		
		Student u = stud_fact.createStudent("username","password1","password2","fname","lname", "email", courses);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Passwords do not match."));
	}

	@Test
	public void testErrorOnNoCourse() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Student u = stud_fact.createStudent("username","password1","password1","fname","lname", "email", courses);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Must select courses."));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSuccess() {
		RegisterStudentFactory stud_fact = new RegisterStudentFactory();
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
		
		Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);

		Set<Course> expectedStud_courses = new HashSet<Course>();
		expectedStud_courses.add(c);
		
		Student u = stud_fact.createStudent("jSmith","jsmith","jsmith","John","Smith",
				"jSmith@email.com", expectedStud_courses);
	
		assertFalse(stud_fact.hasErrors());
		assertEquals(0, stud_fact.getErrors().size());
		assertEquals(u.getUsername(), "jSmith");
		assertEquals(u.getPassword(), "jsmith");
		assertEquals(u.getFullName(), "John Smith");
		assertEquals(u.getEmail(), "jSmith@email.com");
		
		Set<Course> studentCourses = u.getCourses(); 
		Iterator<Course> iterator = studentCourses.iterator();
		Iterator<Course> expected_iterator = expectedStud_courses.iterator();
		while(iterator.hasNext() && expected_iterator.hasNext()) {
			assertEquals(iterator.next(), expected_iterator.next());
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
