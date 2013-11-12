package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.User;
import factories.InstructorFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InstructorFactoryTest {
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
		InstructorFactory instr_fact = new InstructorFactory();
		User u = instr_fact.createInstructor("","password","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});

		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Username is required."));
	}

	@Test
	public void testErrorOnBlankPassword() {
		InstructorFactory instr_fact = new InstructorFactory();
		User u = instr_fact.createInstructor("username","","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Password is required."));
	}
	
	@Test
	public void testErrorOnNonMatchingPasswords() {
		InstructorFactory instr_fact = new InstructorFactory();
		User u = instr_fact.createInstructor("username","password1","password2","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Passwords do not match."));
	}
	
	@Test
	public void testErrorOnBlankPhoneNumber() {
		InstructorFactory instr_fact = new InstructorFactory();
		User u = instr_fact.createInstructor("username","password1","password1","fname","lname", "email", "", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Phone Number is required."));
	}
	
	@Test
	public void testErrorOnBlankEmail() {
		InstructorFactory instr_fact = new InstructorFactory();
		User u = instr_fact.createInstructor("username","password1","password1","fname","lname", "", "8478478478", new String[] {"teacher1","teacher2"});
	
		assertNull(u);
		assertTrue(instr_fact.hasErrors());
		assertEquals(1, instr_fact.getErrors().size());
		assertTrue(instr_fact.getErrors().get(0).equals("Email is required."));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSuccess() {
		InstructorFactory instr_fact = new InstructorFactory();
		List<String> expectedInstr_types = Arrays.asList("teacher1", "teacher2");
		
		User u = instr_fact.createInstructor("jSmith","jsmith","jsmith","John","Smith",
				"jSmith@email.com", "8478478478",(String[])expectedInstr_types.toArray());
	
		assertFalse(instr_fact.hasErrors());
		assertEquals(0, instr_fact.getErrors().size());
		assertEquals(u.getUsername(), "jSmith");
		assertEquals(u.getPassword(), "jsmith");
		assertEquals(u.getFullName(), "John Smith");
		assertEquals(u.getEmail(), "jSmith@email.com");
		assertEquals(u.getPhoneNumber(), "8478478478");
		
		Set<String> instructorTypes = u.getInstructorTypes();
		Iterator<String> iterator = instructorTypes.iterator();
		Iterator<String> expected_iterator = expectedInstr_types.iterator();
		while(iterator.hasNext() && expected_iterator.hasNext()) {
			assertEquals(iterator.next(), expected_iterator.next());
		}
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
