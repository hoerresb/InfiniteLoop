package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.*;
import edu.uwm.cs361.factories.*;

import java.util.*;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreateAwardFactoryTest {
        private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
                        new LocalDatastoreServiceTestConfig());

        private PersistenceManager pm;

        @Before
        @SuppressWarnings("unchecked")
        public void setUp() {
                helper.setUp();
                pm = PersistenceFactory.getPersistenceManager();

                List<Award> awards = (List<Award>) pm.newQuery(Award.class).execute();

                for (Award award : awards) {
                        pm.deletePersistent(award);
                }
                pm.flush();
        }

        @After
        public void tearDown() {
                helper.tearDown();
        }

        @Test
        public void testErrorOnBlankAwardName() {
                CreateAwardFactory aFact = new CreateAwardFactory();
                Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
                Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
                Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
                Award a = aFact.createAward(c, "", "congrats");

                assertNull(a);
                assertTrue(aFact.hasErrors());
                assertEquals(1, aFact.getErrors().size());
                assertTrue(aFact.getErrors().get(0).equals("Please enter an award name."));
        }

        @Test
        public void testErrorOnBlankAwardDescription() {
                CreateAwardFactory aFact = new CreateAwardFactory();
                Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
                Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
                Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
                Award a = aFact.createAward(c, "award0", "");

                assertNull(a);
                assertTrue(aFact.hasErrors());
                assertEquals(1, aFact.getErrors().size());
                assertTrue(aFact.getErrors().get(0).equals("Please enter an award description."));
        }

        @Test
        public void testErrorOnNullCourse() {
                CreateAwardFactory aFact = new CreateAwardFactory();
                Award a = aFact.createAward(null, "award0", "congrats");

                assertNull(a);
                assertTrue(aFact.hasErrors());
                assertEquals(1, aFact.getErrors().size());
                assertTrue(aFact.getErrors().get(0).equals("Please specify a course."));
        }

        @Test
        public void testSuccess() {
                CreateAwardFactory aFact = new CreateAwardFactory();
                Teacher teacher = new Teacher("john", "pw", "john", "john", "email", "8478478474", new String[] {"teacher1","teacher2"});
                Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
                Course c = new Course("learning101", new Date("10/15/2013"), new Date("10/16/2013"), meetingDays, "10:45", "EMS203", "gimme money", "its a good class", teacher);
                Award a = aFact.createAward(c, "award0", "congrats");

                assertFalse(aFact.hasErrors());
                assertEquals(0, aFact.getErrors().size());
                assertEquals(a.getAwardName(), "award0");
                assertEquals(a.getAwardDescription(), "congrats");
        }
}
