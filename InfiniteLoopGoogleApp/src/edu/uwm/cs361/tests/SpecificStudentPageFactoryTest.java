package edu.uwm.cs361.tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import edu.uwm.cs361.entities.Award;
import edu.uwm.cs361.entities.Charge;
import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Student;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.PersistanceFactory;
import edu.uwm.cs361.factories.SpecificStudentPageFactory;

public class SpecificStudentPageFactoryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private PersistenceManager pm;
	
	private Student student;
	private Award award;
	private Teacher teacher;
	private Course course;
	private Charge charge;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() {
		helper.setUp();
		pm = PersistanceFactory.getPersistenceManager();

		List<Teacher> teachers = (List<Teacher>) pm.newQuery(Teacher.class).execute();
		for (Teacher teacher : teachers) {
			pm.deletePersistent(teacher);
		}
		
		List<Course> courses = (List<Course>) pm.newQuery(Course.class).execute();
		for (Course course : courses) {
			pm.deletePersistent(course);
		}
		
		List<Charge> charges = (List<Charge>) pm.newQuery(Charge.class).execute();
		for (Charge charge : charges) {
			pm.deletePersistent(charge);
		}
		
		List<Award> awards = (List<Award>) pm.newQuery(Award.class).execute();
		for (Award a : awards) {
			pm.deletePersistent(a);
		}
		
		List<Student> students = (List<Student>) pm.newQuery(Student.class).execute();
		for (Student student : students) {
			pm.deletePersistent(student);
		}
		
		charge = new Charge(30, new Date(), "Because you owe me");
		award = new Award("Living Champion", "You lived past the age of two, congratulations.");
		
		teacher = new Teacher("username","password1","fname","lname", "email", "8478478478", new String[] {"teacher1","teacher2"});		
		
		Set<String> meetingDays = new HashSet<String>(Arrays.asList(new String[] { "M", "T", "W" }));
		course = new Course("Breathing", "10/14/2013", "10/15/2013", meetingDays, "10:30", "EMS145", "30 per session", "breathe in, breathe out", teacher);
		teacher.getCourses().add(course);
		
		Set<Award> awardsList = new HashSet<Award>(Arrays.asList(new Award[] {award}));
		Set<Course> coursesList = new HashSet<Course>(Arrays.asList(new Course[] {course}));
		Set<Charge> chargesList = new HashSet<Charge>(Arrays.asList(new Charge[] {charge}));
		Set<Teacher> teachersList = new HashSet<Teacher>(Arrays.asList(new Teacher[] {teacher}));
		student = new Student("student", "student", "student", "student", "student@student.edu", coursesList, teachersList, awardsList, chargesList);
		teacher.getStudents().add(student);
		
		pm.makePersistent(charge);
		pm.makePersistent(award);
		pm.makePersistent(teacher);
		pm.makePersistent(course);
		pm.makePersistent(student);
		
		Student student1 = new Student("student1", "student1", "student1", "student1", "student@student.edu", coursesList, teachersList, awardsList, chargesList);
		Student student2 = new Student("student2", "student2", "student2", "student2", "student@student.edu", coursesList, teachersList, awardsList, chargesList);
		pm.makePersistent(student1);
		pm.makePersistent(student2);
		pm.flush();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testGetStudent() {
		SpecificStudentPageFactory specFact = new SpecificStudentPageFactory();
		Student result = specFact.getStudent(pm, student.getUser_id().getId());
		
		assertEquals(student.getFullName(), result.getFullName());
		assertEquals(student.getUsername(), result.getUsername());
		assertEquals(student.getPassword(), result.getPassword());
		assertEquals(student.getUser_id().getId(), result.getUser_id().getId());
	}
	
	@Test
	public void testGetAwards() {
		SpecificStudentPageFactory specFact = new SpecificStudentPageFactory();
		Set<Award> awards = specFact.getAwards(pm, student);
		
		assertEquals(1, awards.size());
		for(Award a : awards) {
			assertEquals(a.getAwardDescription(), award.getAwardDescription());
			assertEquals(a.getAwardName(), award.getAwardName());
			assertEquals(a.getAward_id().getId(), award.getAward_id().getId());
		}
	}
	
	@Test
	public void testGetTeachers() {
		SpecificStudentPageFactory specFact = new SpecificStudentPageFactory();
		Set<Teacher> teachers = specFact.getTeachers(pm, student);
		
		assertEquals(1, teachers.size());
		for(Teacher t : teachers) {
			assertEquals(t.getUser_id().getId(), teacher.getUser_id().getId());
			assertEquals(t.getFullName(), teacher.getFullName());
			assertEquals(t.getEmail(), teacher.getEmail());
		}
	}
	
	@Test
	public void testGetCourses() {
		SpecificStudentPageFactory specFact = new SpecificStudentPageFactory();
		Set<Course> courses = specFact.getCourses(pm, student);
		
		assertEquals(1, courses.size());
		for(Course c : courses) {
			assertEquals(c.getCourse_id().getId(), course.getCourse_id().getId());
			assertEquals(c.getDescription(), course.getDescription());
			assertEquals(c.getEndDate(), course.getEndDate());
		}
	}
	
	@Test
	public void testGetBalance() {
		SpecificStudentPageFactory specFact = new SpecificStudentPageFactory();
		Double amount = specFact.getBalance(pm, student);
		assertEquals((new Double(-30)).toString(), amount.toString());
	}
	
}
