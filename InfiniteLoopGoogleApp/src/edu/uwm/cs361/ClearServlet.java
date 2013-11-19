package edu.uwm.cs361;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course;
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.entities.old_User;

@SuppressWarnings("serial")
public class ClearServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersistenceManager pm = getPersistenceManager();

		try {
			deleteTeachers(pm);
			deleteCourses(pm);
			resp.sendRedirect("/display");
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void deleteTeachers(PersistenceManager pm) {
		for (Teacher teacher : (List<Teacher>) pm.newQuery(Teacher.class).execute()) {
			pm.deletePersistent(teacher);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void deleteCourses(PersistenceManager pm) {
		for (Course course : (List<Course>) pm.newQuery(Course.class).execute()) {
			pm.deletePersistent(course);
		}
	}


	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
