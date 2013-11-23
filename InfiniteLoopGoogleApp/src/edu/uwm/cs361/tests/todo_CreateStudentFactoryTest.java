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
import edu.uwm.cs361.factories.todo_CreateStudentFactory;

import java.util.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class todo_CreateStudentFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

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
	public void testErrorOnBlankUsername() {
		todo_CreateStudentFactory stud_fact = new todo_CreateStudentFactory();
		Student u = stud_fact.createStudent("","password","password","fname","lname", "email", "8478478478", null);

		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Username is required."));
	}

	@Test
	public void testErrorOnBlankPassword() {
		todo_CreateStudentFactory stud_fact = new todo_CreateStudentFactory();
		Student u = stud_fact.createStudent("username","","password","fname","lname", "email", "8478478478", null);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Password is required."));
	}
	
	@Test
	public void testErrorOnNonMatchingPasswords() {
		todo_CreateStudentFactory stud_fact = new todo_CreateStudentFactory();
		Student u = stud_fact.createStudent("username","password1","password2","fname","lname", "email", "8478478478", null);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Passwords do not match."));
	}
	
	@Test
	public void testErrorOnBlankPhoneNumber() {
		todo_CreateStudentFactory stud_fact = new todo_CreateStudentFactory();
		Student u = stud_fact.createStudent("username","password1","password1","fname","lname", "email", "", null);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Phone Number is required."));
	}
	
	@Test
	public void testErrorOnBlankEmail() {
		todo_CreateStudentFactory stud_fact = new todo_CreateStudentFactory();
		Student u = stud_fact.createStudent("username","password1","password1","fname","lname", "", "8478478478", null);
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Email is required."));
	}
	
//	@Test
//	public void testSuccess() {
//		todo_CreateStudentFactory stud_fact = new todo_CreateStudentFactory();
//		CreateCourseFactory course_fact = new CreateCourseFactory();
//		
//		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
//		Set<String> payment_options = new HashSet<String>(Arrays.asList(new String[] { "Pay now", "Pay then", "Give me your arm" }));
//		Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
//		
//		//Course c = course_fact.createCourse("learning101", "10/15/2013", "10/16/2013", meetingDays, "10:45","EMS203", payment_options,"its a good class", teacher);
//
//		Set<Course> expectedStud_courses = new HashSet<Course>();
//		expectedStud_courses.add(c);
//		
//		Student u = stud_fact.createStudent("jSmith","jsmith","jsmith","John","Smith",
//				"jSmith@email.com", "8478478478",expectedStud_courses);
//	
//		assertFalse(stud_fact.hasErrors());
//		assertEquals(0, stud_fact.getErrors().size());
//		assertEquals(u.getUsername(), "jSmith");
//		assertEquals(u.getPassword(), "jsmith");
//		assertEquals(u.getFullName(), "John Smith");
//		assertEquals(u.getEmail(), "jSmith@email.com");
//		
//		Set<Course> studentCourses = u.getCourses(); 
//		Iterator<Course> iterator = studentCourses.iterator();
//		Iterator<Course> expected_iterator = expectedStud_courses.iterator();
//		while(iterator.hasNext() && expected_iterator.hasNext()) {
//			assertEquals(iterator.next(), expected_iterator.next());
//		}
//	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
