package edu.uwm.cs361;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import edu.uwm.cs361.entities.*;

	@SuppressWarnings("unchecked")
	private Teacher getTeacher(String username) {
		PersistenceManager pm = getPersistenceManager();
		List<Teacher> teachers = new ArrayList<Teacher>();
		Teacher teacher = null;
		try {
			teachers = (List<Teacher>) pm.newQuery(Teacher.class).execute();
			for (Teacher t : teachers) {
				if (t.getUsername().equals(username)) {
					teacher = t;
					teacher.getCourses();
				}
			}
		} finally {
			pm.close();
		}
		return teacher;
	}

	private Set<Course> getCourses(Teacher teacher) {
		PersistenceManager pm = getPersistenceManager();
		Set<Course> courses = new HashSet<Course>();
		try {
			if (teacher != null) {
				if (teacher.getCourses() != null) {
					courses = teacher.getCourses();
				}
			}
		} finally {
			pm.close();
		}
		return courses;
	}

	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
}
