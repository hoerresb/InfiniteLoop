package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.User;
import edu.uwm.cs361.util.UserConstants;
import factories.CreateInstructorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentChargesFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;
	private List<User> students = new ArrayList<User>();
	
	private int numStudents;

	@Before
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void setUp() {
		helper.setUp();
		pm = getPersistenceManager();
		List<User> users = (List<User>) pm.newQuery(User.class).execute();
		try {
			numStudents = 0;
			for (User user : users) {
				System.out.print("User found.");
				if (user.getUser_type() == UserConstants.STUDENT_NUM) {
					students.add(user);
					numStudents++;
					System.out.print("Student found: "+ user.getFullName());
				}
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
	public void testNoStudents() {
		assertEquals(0, students.size());
	}
	
	@Test
	public void testOneStudents() {
		Charge[] charges = {new Charge(12, new Date(2013,11,31), ""), new Charge(15, new Date(2013,11,31), "") , new Charge(18, new Date(2013,11,31), "")};
		User user = new User(UserConstants.STUDENT_NUM, "student", "student", "Student_fn", "Student_ln", "student@student.com", "111-111-1111", charges);
		if(user.getUser_type() == UserConstants.STUDENT_NUM);
			students.add(user);
		assertEquals(1, students.size());
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
