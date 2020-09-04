package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * A student object that contains the first name, last name, id, email address,
 * password, and max number of credits they can take in a given semester.
 * Subclass of user
 * 
 * @author troy1
 *
 */
public class Student extends User implements Comparable<Student> {
	/** The max credits this specific student can take */
	private int maxCredits;
	/** The maximum possible credits any student can have */
	public static final int MAX_CREDITS = 18;
	/** The students course schedule */
	public Schedule studentSchedule;

	/**
	 * Constructs a Student object(child class of User) with values for all fields
	 * 
	 * @param firstName  the students first name
	 * @param lastName   the students last name
	 * @param id         the students id
	 * @param email      the students email
	 * @param password   the students password hashed
	 * @param maxCredits the max credits this specific student can take
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		studentSchedule = new Schedule();
	}

	/**
	 * Constructs a Student object with values for all fields. The maxCredits is
	 * defaulted to 18
	 * 
	 * @param firstName the students first name
	 * @param lastName  the students last name
	 * @param id        the students id
	 * @param email     the students email
	 * @param password  the students password hashed
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Simple maxCredits for firstName
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maxCredits with a number between 3 and 18
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if maxCredits are less then 3
	 * @throws IllegalArgumentException if maxCredits are more then 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Visualizes the object as a string in the format of in the format of
	 * firstName,lastName,id,email,hashedPassword,maxCredits
	 * 
	 * @return the student object as a string
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCredits;
	}

	/**
	 * Overrides the default compareTo method. It will compare 'this' object to
	 * another in priority order of last name, first name, then id
	 * 
	 * @return the student object as a string
	 */
	@Override
	public int compareTo(Student o) {
		int difference = 0;
		difference = this.getLastName().toLowerCase().compareTo(o.getLastName().toLowerCase());

		if (difference == 0) {
			difference = this.getFirstName().toLowerCase().compareTo(o.getFirstName().toLowerCase());
		}
		if (difference == 0) {
			difference = this.getId().toLowerCase().compareTo(o.getId().toLowerCase());
		}

		return difference;
	}

	/*
	 * Overrides the User hashcode. In addition to the User hash, the field
	 * maxCredits is added
	 * 
	 * @return Returns a hash code value for the object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}
	/*
	 * Overrides the User equals. In addition to the User equals, the field
	 * maxCredits is added
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
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}

	/**
	 * Simple Getter for the studentSchedule
	 * 
	 * @return the students schedule
	 */
	public Schedule getSchedule() {
		return studentSchedule;

	}

	/**
	 * Determines if a course can be added to the schedule
	 * 
	 * @param c course being checked
	 * @return true if the credits of the course being added is not more the
	 *         remaining credits available to the student
	 */
	public boolean canAdd(Course c) {
		studentSchedule.canAdd(c);
		int remainingCredits = getMaxCredits() - studentSchedule.getScheduleCredits();
		if (c.getCredits() > remainingCredits) {
			return false;
		}
		return true;
	}

}
