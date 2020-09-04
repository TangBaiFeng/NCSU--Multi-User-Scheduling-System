package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;

/**
 * Utility class to manage the students course schedule. The schedule is a named
 * custom ArrayList of courses and can have courses added and removed by
 * specifying the course
 * 
 * @author Troy Boone
 *
 */
public class Schedule {
	/** The list of courses available to take */
	private ArrayList<Course> schedule;
	/** The title of the schedule */
	private String title;

	/**
	 * Constructs the schedule object by initializing the schedule ArrayList and
	 * setting the title to its default value of "My Schedule"
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Adds the course to the end of the schedule. If the course is a duplicate, an
	 * error message "You are already enrolled in this.courseName" is displayed. If
	 * there is a time conflict, an error message, "The course cannot be added due
	 * to a conflict.", is displayed
	 * 
	 * @param c the course being removed from the schedule
	 * @return true if the course was added to the schedule, false if null or failed
	 *         to be added
	 * @throws IllegalArgumentException if there is a time conflict with another
	 *                                  course
	 */
	public boolean addCourseToSchedule(Course c) {

		if (c == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < schedule.size(); i++) {
			if (c.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}

		if (schedule.add(c)) {
			return true;
		}

		return false;
	}

	/**
	 * Removes the course from the schedule. Relies on ArrayList custom
	 * implementation of remove
	 * 
	 * @param c the course being removed from the schedule
	 * @return true if the course was removed to the schedule, false otherwise
	 */
	public boolean removeCourseFromSchedule(Course c) {
		if (c == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).compareTo(c) == 0) {
				schedule.remove(i);
				return true;
			}
		}
		return false;

	}

	/**
	 * Generates an empty ArrayList that will overwrite the schedule to reset
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Iterates over the arraylist course to generate a 2D string array
	 * 
	 * @return a 2D string array with columns between name, section, title, and
	 *         meetingString, and open seats
	 */
	public String[][] getScheduledCourses() {
		String[][] catalog = new String[schedule.size()][5];

		for (int i = 0; i < schedule.size(); i++) {
			catalog[i][0] = schedule.get(i).getName();
			catalog[i][1] = schedule.get(i).getSection();
			catalog[i][2] = schedule.get(i).getTitle();
			catalog[i][3] = schedule.get(i).getMeetingString();
			catalog[i][4] = String.valueOf(schedule.get(i).getCourseRoll().getOpenSeats());
		}
		return catalog;

	}

	/**
	 * Simple getter for the title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the title. Allows the title to be any non null or empty string
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is null
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}

	/**
	 * Get the cumulative sum of all courses in schedule
	 * 
	 * @return total credits hours a student is taking
	 */
	public int getScheduleCredits() {
		int totalCredits = 0;
		for (Course c : schedule) {
			totalCredits += c.getCredits();
		}
		return totalCredits;

	}

	/**
	 * Determines if a course can be added to the schedule
	 * 
	 * @param c course being checked
	 * @return true if the course is not null, not in the schedule, and does not
	 *         conflict
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		try {
			for (Course scheduledCourse : schedule) {
				if (c.isDuplicate(scheduledCourse)) {
					throw new IllegalArgumentException("You are already enrolled in " + c.getName());
				}
				c.checkConflict(scheduledCourse);

			}
		} catch (Exception e) {
			return false;
		}
		return true;

	}
}
