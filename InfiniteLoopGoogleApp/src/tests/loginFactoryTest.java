package tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.User;
import factories.loginFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class loginFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = getPersistenceManager();

		try {
			List<User> users = (List<User>) pm.newQuery(User.class).execute();

			for (User user : users) {
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
	public void testBlankUsername() {
		loginFactory login_fact = new loginFactory();
		int i= 0;
		User u = login_fact.createUser(i,"","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		assertTrue(login_fact.hasErrors());
		assertEquals(1 ,login_fact.getErrors().size());
		assertTrue(login_fact.getErrors().get(0).equals("Username is required"));
	}
	
	@Test
	public void testBlankPassword(){
		loginFactory login_fact = new loginFactory();
		int i = 0; 
		User u = login_fact.createUser(i,"username","","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		assertTrue(login_fact.hasErrors());
		assertEquals(1, login_fact.getErrors().size());
		assertTrue(login_fact.getErrors().get(0).equals("Password is required"));
	}
	
	@Test
	public void testBlankUserandPass(){
		loginFactory login_fact = new loginFactory();
		int i = 0;
		User u = login_fact.createUser(i,"","","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		
		assertTrue(login_fact.hasErrors());
		assertEquals(2, login_fact.getErrors().size());
		assertTrue(login_fact.getErrors().get(0).equals("Username is required"));
		assertTrue(login_fact.getErrors().get(1).equals("Password is required")); 
		
	}
	
	@Test
	public void testPassNotMatchPassword(){
		loginFactory login_fact = new loginFactory();
		int i = 0;
		User u = login_fact.createUser(i,"username","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		User u2 = login_fact.createUser(i,"noUser","noPass","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		login_fact.getUsers().add(u);
		login_fact.getUsers().add(u2);
		assertFalse(login_fact.hasErrors());
		
		assertEquals(2,login_fact.getUsers().size());
		assertFalse(login_fact.doLoginIncorrectPassword("username", "noPass"));
		assertFalse(login_fact.doLoginIncorrectPassword("noUser", "password"));
	
		
	}
	
	@Test
	public void testPassNotMatchUsername(){
		loginFactory login_fact = new loginFactory();
		int i = 0;
		User u = login_fact.createUser(i,"username","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		User u2 = login_fact.createUser(i,"noUser","noPass","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		login_fact.getUsers().add(u);
		login_fact.getUsers().add(u2);
		assertFalse(login_fact.hasErrors());
		
		assertEquals(2,login_fact.getUsers().size());
		assertFalse(login_fact.doLoginIncorrectUsername("hi", "noPass"));
		assertFalse(login_fact.doLoginIncorrectUsername("bye", "password"));
	
		
	}
	
	@Test
	public void testSuccess(){
		loginFactory login_fact = new loginFactory();
		int i = 0;
		User u = login_fact.createUser(i,"username","password","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		User u2 = login_fact.createUser(i,"noUser","noPass","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});
		login_fact.getUsers().add(u);
		login_fact.getUsers().add(u2);
		assertFalse(login_fact.hasErrors());
		
		assertEquals(2,login_fact.getUsers().size());
		assertTrue(login_fact.doLoginCorrectLogin("username", "password"));
		assertTrue(login_fact.doLoginCorrectLogin("noUser", "noPass"));
	
		
	}
	
	
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
	
}