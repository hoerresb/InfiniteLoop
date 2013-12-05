package edu.uwm.cs361.factories;

import javax.jdo.PersistenceManager;

import edu.uwm.cs361.entities.Course;

public class SpecificCoursePageFactory {
	public Course getCourse(PersistenceManager pm, long id) {
		return (Course) pm.getObjectById(Course.class, id);
	}
}
