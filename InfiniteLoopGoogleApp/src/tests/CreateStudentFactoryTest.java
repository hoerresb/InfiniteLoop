package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.User;
import factories.CreateInstructorFactory;
import factories.CreateStudentFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateStudentFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = getPersistenceManager();

		try {
			List<User> students = (List<User>) pm.newQuery(User.class).execute();

			for (User user : students) {
				pm.deletePersistent(user);
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
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		User u = stud_fact.createStudent("","password","password","fname","lname", "email", "8478478478", new String[] {"class1","class2"});

		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Username is required."));
	}

	@Test
	public void testErrorOnBlankPassword() {
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		User u = stud_fact.createStudent("","password","password","fname","lname", "email", "8478478478", new String[] {"class1","class2"});
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Password is required."));
	}
	
	@Test
	public void testErrorOnNonMatchingPasswords() {
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		User u = stud_fact.createStudent("","password","password","fname","lname", "email", "8478478478", new String[] {"class1","class2"});
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Passwords do not match."));
	}
	
	@Test
	public void testErrorOnBlankPhoneNumber() {
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		User u = stud_fact.createStudent("","password","password","fname","lname", "email", "8478478478", new String[] {"class1","class2"});
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Phone Number is required."));
	}
	
	@Test
	public void testErrorOnBlankEmail() {
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		User u = stud_fact.createStudent("","password","password","fname","lname", "email", "8478478478", new String[] {"class1","class2"});
	
		assertNull(u);
		assertTrue(stud_fact.hasErrors());
		assertEquals(1, stud_fact.getErrors().size());
		assertTrue(stud_fact.getErrors().get(0).equals("Email is required."));
	}
	
	@Test
	public void testSuccess() {
		CreateStudentFactory stud_fact = new CreateStudentFactory();
		List<String> expectedStud_courses = Arrays.asList("class1", "class2");
		
		User u = stud_fact.createStudent("jSmith","jsmith","jsmith","John","Smith",
				"jSmith@email.com", "8478478478",(String[])expectedStud_courses.toArray());
	
		assertFalse(stud_fact.hasErrors());
		assertEquals(0, stud_fact.getErrors().size());
		assertEquals(u.getUsername(), "jSmith");
		assertEquals(u.getPassword(), "jsmith");
		assertEquals(u.getFullName(), "John Smith");
		assertEquals(u.getEmail(), "jSmith@email.com");
		assertEquals(u.getPhoneNumber(), "8478478478");
		
		Set<String> studentCourses = u.getInstructorTypes(); // courses should be a list of courses not strings
		Iterator<String> iterator = studentCourses.iterator();
		Iterator<String> expected_iterator = expectedStud_courses.iterator();
		while(iterator.hasNext() && expected_iterator.hasNext()) {
			assertEquals(iterator.next(), expected_iterator.next());
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
