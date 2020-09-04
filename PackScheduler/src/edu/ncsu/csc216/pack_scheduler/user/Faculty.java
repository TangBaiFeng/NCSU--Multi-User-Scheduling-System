package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * A faculty object that contains the first name, last name, id, email address,
 * password, and max number of courses they can teach in a given semester.
 * Subclass of user
 * 
 * @author troy1
 *
 */
public class Faculty extends User {
	/** The max courses this specific faculty can host */
	private int maxCourses;
	/** The teaching schedule for the faculty member */
	private FacultySchedule schedule;
	/** The minimum possible courses any faculty can teach */
	private static final int MIN_COURSES = 1;
	/** The maximum possible courses any faculty can teach */
	private static final int MAX_COURSES = 3;

	/**
	 * Constructs a Faculty object with values for all fields. The maxCredits is
	 * defaulted to 18
	 * 
	 * @param firstName  the faculty first name
	 * @param lastName   the faculty last name
	 * @param id         the faculty id
	 * @param email      the faculty email
	 * @param password   the faculty password hashed
	 * @param maxCourses the max courses this specific faculty can host
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Simple getter for maxCourses
	 * 
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the maxCourses with a number between 1 and 3
	 * 
	 * @param maxCourses the maxCourses to set
	 * @throws IllegalArgumentException if maxCourses are less then 1 or more then 3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Visualizes the object as a string in the format of in the format of
	 * firstName,lastName,id,email,hashedPassword,maxCourses
	 * 
	 * @return the student object as a string
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ getMaxCourses();
	}

	/*
	 * Overrides the User hashcode. In addition to the User hash, the field
	 * maxCourses is added
	 * 
	 * @return Returns a hash code value for the object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCourses);
		return result;
	}
	/*
	 * Overrides the User equals. In addition to the User equals, the field
	 * maxCourses is added 
	 * 
	 * @return Returns a hash code value for the object
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}

	/**
	 * Simple getter for schedule
	 * 
	 * @return the schedule
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Checks if the faculty has filled
	 * 
	 * @return the schedule
	 */
	public boolean isOverloaded() {
		return (getSchedule().getNumScheduledCourses() > getMaxCourses()) ? true : false;
	}
}
