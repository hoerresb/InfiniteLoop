package edu.uwm.cs361;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Course; 
import edu.uwm.cs361.entities.Teacher;
import edu.uwm.cs361.factories.CreateCourseFactory;
import edu.uwm.cs361.factories.PersistenceFactory;

import com.google.appengine.api.datastore.PropertyContainer;


@SuppressWarnings("serial")
public class EditClassServlet extends HttpServlet {
        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        	
        		req.setAttribute("courses", getCourses());
          		req.setAttribute("teachers", getTeachers());
          		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
          		String selectedCourse = req.getParameter("course_options");
          		String editCourse;
          		
          		try{
          		
          			
          			if(selectedCourse != null && !selectedCourse.equals("")) {
          				
                		
          				editCourse = selectedCourse;
                    	Course c = pm.getObjectById(Course.class, Long.parseLong(selectedCourse));
                		
                           
                        String classname = c.getName();
                        String startDate = c.getStartDateFormatted();
                        String endDate = c.getEndDateFormatted();
                          
                        Set<String> meetingDays = c.getMeetingDays();
                           
                        String time = c.getTime();
                        String place = c.getPlace();
                        String description = c.getDescription();

                        Double payment_val = c.getPayment_amount();
                        String payment_option = c.getPaymentOption();
                        Teacher teacher = c.getTeacher();
        				req.setAttribute("classname", classname);
        				req.setAttribute("classstart", startDate);
        				req.setAttribute("classend", endDate);
        				req.setAttribute("time", time);
        				req.setAttribute("place", place);
        				req.setAttribute("meeting_times", meetingDays);
        				req.setAttribute("class_description", description);
        				req.setAttribute("payment_value", payment_val);
        				req.setAttribute("payment_duration", payment_option);
        				req.setAttribute("editCourse", editCourse);
        				
        				req.getRequestDispatcher("/editClass.jsp").forward(req, resp);
          			} else {
          				req.getRequestDispatcher("/editClass.jsp").forward(req, resp);
          			}
                
          		} finally {
                	pm.close();
                }
        	 
                
                
        }

        @Override
        public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException        {
        		
                PersistenceManager pm = PersistenceFactory.getPersistenceManager();
                String selectedCourse = req.getParameter("editCourse");
                
                String[] daysOfWeek = new String[] {"M","T","W","Th","F","S","Su"};
				
				String classname = req.getParameter("classname");
				
				
				String startDate = req.getParameter("classstart");
				String endDate = req.getParameter("classend");	
				Date start = null;
				Date end = null;
				if(!startDate.isEmpty() &&  !endDate.isEmpty()) {
					String startStr = startDate.substring(5,7) +"/"+ startDate.substring(8,10) +"/"+ startDate.substring(0,4);
					start = new Date(startStr);
					String endStr = endDate.substring(5,7) +"/"+ endDate.substring(8,10) +"/"+ endDate.substring(0,4);
					end = new Date(endStr);
				
				}
				String[] meeting_time_days = req.getParameterValues("meeting_times");
				Set<String> meetingDays = null;
				if(meeting_time_days != null) {
					meetingDays = new HashSet<String>(Arrays.asList(meeting_time_days)); 
				
				}
				
				Map<String, Boolean> days = populateSelectedDays(meetingDays, daysOfWeek);
				
				String time = req.getParameter("time");
				
				String place = req.getParameter("place");
				
				String description = req.getParameter("class_description");
				
				String payment_value = req.getParameter("payment_value");
				String payment_duration = req.getParameter("payment_duration");
				String payment_option = payment_value + " per " + payment_duration;
				
				String teacherID = req.getParameter("instr_options");
				Teacher teacher = (Teacher) pm.getObjectById(Teacher.class,Long.parseLong(teacherID));
				
				
				CreateCourseFactory course_fact = new CreateCourseFactory();
				
	

                
                try
                {
                	
                		Course c = pm.getObjectById(Course.class, Long.parseLong(selectedCourse));
                		

        				Course course = course_fact.createCourse(pm, classname, startDate, endDate, meetingDays, time, place, payment_option, description,teacherID);
        				
        				if (course_fact.hasErrors()) {
        					req.setAttribute("course_options", selectedCourse);
        					req.setAttribute("editCourse", selectedCourse);
        					req.setAttribute("classname", classname);
        					req.setAttribute("classstart", startDate);
        					req.setAttribute("classend", endDate);
        					req.setAttribute("time", time);
        					req.setAttribute("place", place);
        					req.setAttribute("meeting_times", meetingDays);
        					req.setAttribute("class_description", description);
        					req.setAttribute("payment_value", payment_value);
        					req.setAttribute("payment_duration", payment_duration);
        					req.setAttribute("errors", course_fact.getErrors());
        					req.setAttribute("teachers", getTeachers());
        					req.setAttribute("days", days);
        					req.setAttribute("courses", getCourses());
                      		req.setAttribute("teachers", getTeachers());
        					req.getRequestDispatcher("/editClass.jsp").forward(req, resp);
        				} else {
        			
        					c.setName(classname);
            				c.setStartDate(start);
            				c.setMeetingDays(meetingDays);
            				c.setEndDate(end);
            				c.setTime(time);
            				c.setPlace(course.getPlace());
            				c.setDescription(course.getDescription());
            				c.setTeacher(course.getTeacher());
            				c.setPaymentOptions(course.getPaymentOption());
            				
            				
            				
            				
            				req.setAttribute("success", "Class edited successfully.");
            				req.setAttribute("courses", getCourses());
                      		req.setAttribute("teachers", getTeachers());
                      		pm.makePersistent(c);
              				req.getRequestDispatcher("/editClass.jsp").forward(req, resp);
        				}
        				
        				
        				

        				
                	
                
            	} finally {
                	pm.close();
                }
               
               
        }



    	private Map<String, Boolean> populateSelectedDays(Set<String> meetingDays, String[] daysOfWeek) {
    		Map<String, Boolean> days = new HashMap<String, Boolean>();
    		for(String day : daysOfWeek) {
    			if(wasSelected(day,meetingDays)) {
    				days.put(day, true);
    			} else {
    				days.put(day, false);
    			}
    		}
    		return days;
    	}

    	private boolean wasSelected(String day, Set<String> meetingDays) {
    		for(String selectedDay : meetingDays) {
    			if(selectedDay.equals(day)) {
    				return true;
    			}
    		}
    		return false;
    	}
        
        @SuppressWarnings("unchecked")
		private List<Course> getCourses()
    	{
    		PersistenceManager pm = getPersistenceManager();		
    		try {
    			return (List<Course>) (pm.newQuery(Course.class)).execute();
    		} finally {
    			pm.close();
    		}
    	}
        
    	@SuppressWarnings("unchecked")
		private List<Teacher> getTeachers() {
    		PersistenceManager pm = PersistenceFactory.getPersistenceManager();
    		try {
    			return (List<Teacher>) pm.newQuery(Teacher.class).execute();
    		} finally {
    			pm.close();
    		}
    	}
    	
  
        private PersistenceManager getPersistenceManager() {
                return JDOHelper.getPersistenceManagerFactory("transactions-optional")
                                .getPersistenceManager();
        }

}