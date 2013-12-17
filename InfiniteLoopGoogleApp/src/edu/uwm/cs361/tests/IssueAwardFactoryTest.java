package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.factories.IssueAwardFactory;
import edu.uwm.cs361.factories.PersistanceFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IssueAwardFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();

	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testErrorOnEmptyAward() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Student s = new Student("","p4ssword","p4ssword","firstName","lastName", "em4il", null);
		boolean issued = aFact.issueAward(s, null);

		assertFalse(issued);
		assertTrue(aFact.hasErrors());
		assertEquals(1, aFact.getErrors().size());
		assertTrue(aFact.getErrors().get(0).equals("Please select an award."));
	}

	@Test
	public void testErrorOnEmptyStudent() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Award a = new Award("award0", "congrats");
		boolean issued = aFact.issueAward(null, a);

		assertFalse(issued);
		assertTrue(aFact.hasErrors());
		assertEquals(1, aFact.getErrors().size());
		assertTrue(aFact.getErrors().get(0).equals("No student selected."));
	}
	@Test
	public void testSuccess() {
		IssueAwardFactory aFact = new IssueAwardFactory();
		Student s = new Student("","p4ssword","p4ssword","firstName","lastName", "em4il", null);
		Award a = new Award("award0", "congrats");
		boolean issued = aFact.issueAward(s,a);

		assertFalse(aFact.hasErrors());
		assertEquals(0, aFact.getErrors().size());
		assertEquals(s.getAwards().get(0).getAwardName(), "award0");
		assertEquals(s.getAwards().get(0).getAwardDescription(), "congrats");
	}
