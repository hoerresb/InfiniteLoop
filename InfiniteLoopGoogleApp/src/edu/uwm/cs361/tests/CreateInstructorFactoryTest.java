package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Admin;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateInstructorFactory;
import edu.uwm.cs361.factories.PersistenceFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateInstructorFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = PersistenceFactory.getPersistenceManager();

		List<Teacher> instructors = (List<Teacher>) pm.newQuery(Teacher.class).execute();

		for (Teacher user : instructors) {
			pm.deletePersistent(user);
		}
		pm.flush();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testErrorOnBlankUsername() {
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher u = instr_fact.createInstructor("","password","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});

		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Username is required."));
	}

	@Test
	public void testErrorOnBlankPassword() {
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher u = instr_fact.createInstructor("username","","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Password is required."));
	}
	
	@Test
	public void testErrorOnNonMatchingPasswords() {
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher u = instr_fact.createInstructor("username","password1","password2","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Passwords do not match."));
	}
	
	@Test
	public void testErrorOnBlankPhoneNumber() {
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher u = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Phone Number is required."));
	}
	
	@Test
	public void testErrorOnBlankEmail() {
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher u = instr_fact.createInstructor("username","password1","password1","fname","lname", "", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Email is required."));
	}
	
	@Test
	public void testErrorOnExistingUsername() {
		Admin admin = new Admin("username", "password", "firstname", "lastname", "email@emial.com");
		pm.makePersistent(admin);
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		Teacher u = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Username is already taken."));
	}
	
	@Test
	public void testSuccess() {
		CreateInstructorFactory instr_fact = new CreateInstructorFactory();
		List<String> expectedInstr_types = Arrays.asList("teacher1", "teacher2");
		
		Teacher u = instr_fact.createInstructor("jSmith","jsmith","jsmith","John","Smith",
				"jSmith@email.com", "8478478478",(String[])expectedInstr_types.toArray());
	
		assertFalse(instr_fact.hasErrors());
		assertEquals(0, instr_fact.getErrors().size());
		assertEquals(u.getUsername(), "jSmith");
		assertEquals(u.getPassword(), "jsmith");
		assertEquals(u.getFullName(), "John Smith");
		assertEquals(u.getEmail(), "jSmith@email.com");
		assertEquals(u.getPhoneNumber(), "8478478478");
		
		Set<String> instructorTypes = u.getInstructor_types();
		Iterator<String> iterator = instructorTypes.iterator();
		Iterator<String> expected_iterator = expectedInstr_types.iterator();
		while(iterator.hasNext() && expected_iterator.hasNext()) {
			assertEquals(iterator.next(), expected_iterator.next());
		}
	}
}
