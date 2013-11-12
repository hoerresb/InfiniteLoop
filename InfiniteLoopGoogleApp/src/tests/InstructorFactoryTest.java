package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.User;

import java.util.List;

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
	public void testErrorOnBlankName() {
//		InstructorFactory instr_fact = new InstructorFactory();
//		User e = instr_fact.create("", "some text");
//
//		assertNull(e);
//		assertTrue(instr_fact.hasErrors());
//		assertEquals(1, instr_fact.getErrors().size());
//		assertTrue(instr_fact.getErrors().get(0).contains("name"));
	}

//	@Test
//	public void testErrorOnBlankText() {
//		EntryFactory ec = new EntryFactory();
//		Entry e = ec.create("name", "");
//
//		assertNull(e);
//		assertTrue(ec.hasErrors());
//		assertEquals(1, ec.getErrors().size());
//		assertTrue(ec.getErrors().get(0).contains("text"));
//	}
//
//	@Test
//	public void testErrorOnWhitespaceText() {
//		EntryFactory ec = new EntryFactory();
//		Entry e = ec.create("name", "   ");
//
//		assertNull(e);
//		assertTrue(ec.hasErrors());
//		assertEquals(1, ec.getErrors().size());
//		assertTrue(ec.getErrors().get(0).contains("text"));
//	}
//
//	@Test
//	public void testSuccess() {
//		EntryFactory ec = new EntryFactory();
//		Entry e = ec.create("name", "text");
//
//		assertFalse(ec.hasErrors());
//		assertEquals(0, ec.getErrors().size());
//
//		assertEquals("name", e.getName());
//		assertEquals("text", e.getText());
//	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
