package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * List of students for a specific class. The class can have a minimum of 10 and
 * maximum of 250 students enrolled. Contains methods for enrolling, dropping
 * and viewing the students in the course
 * 
 * @author Troy Boone
 *
 */
public class CourseRoll {
	/** The minimum number of students in a course */
	private static final int MIN_ENROLLMENT = 10;
	/** The maximum number of students in a course */
	private static final int MAX_ENROLLMENT = 250;
	/** The linked list of students in the course */
	private LinkedAbstractList<Student> roll;
	/** The number of students able to be enrolled in the course */
	private int enrollmentCap;
	/** The number of students able to be waitlisted in the course */
	private int waitlistCap = 10;
	/** The waitlist of students for the course */
	private LinkedQueue<Student> waitlist;
	/** The course */
	private Course course;

	/**
	 * Construct the CourseRoll object. Checks that enrollmentCap is valid and then
	 * initialized the LinkedAbstractList roll
	 * 
	 * @param enrollmentLimit the number of students allowed to enroll
	 * @param c               the course this roll is for
	 * @throws IllegalArgumentException if the if the enrollmentCap is less than
	 *                                  MIN_ENROLLMENT or greater than
	 *                                  MAX_ENROLLMENT
	 */
	public CourseRoll(Course c, int enrollmentLimit) {

		if (enrollmentLimit > MAX_ENROLLMENT || enrollmentLimit < MIN_ENROLLMENT || c == null) {
			throw new IllegalArgumentException();
		}
		roll = new LinkedAbstractList<Student>(enrollmentLimit);
		setEnrollmentCap(enrollmentLimit);
		waitlist = new LinkedQueue<Student>(waitlistCap);
		this.course = c;
	}

	/**
	 * Simple getter for enrollmentCap
	 * 
	 * @return the enrollmentCap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Setter for enrollmentCap
	 * 
	 * @param enrollmentLimit the number of students allowed to enroll
	 * @throws IllegalArgumentException if the if the enrollmentCap is less than
	 *                                  MIN_ENROLLMENT or greater than
	 *                                  MAX_ENROLLMENT or is less then the current
	 *                                  roll size
	 */
	public void setEnrollmentCap(int enrollmentLimit) {
		if (enrollmentLimit > MAX_ENROLLMENT || enrollmentLimit < MIN_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		if (enrollmentLimit < roll.size()) {
			throw new IllegalArgumentException();
		}
		this.enrollmentCap = enrollmentLimit;
		roll.setCapacity(enrollmentLimit);
	}

	/**
	 * Adds the student to the end of the course roll. If the course is full, it
	 * will attempt to add the student to the course waitlist
	 * 
	 * @param s the student being added
	 * @throws IllegalArgumentException if the waitlist is full, the student is
	 *                                  already in the course roll, or the student
	 *                                  is null
	 */
	public void enroll(Student s) {
		nullCheck(s);
		if (duplicateBoolean(s)) {
			throw new IllegalArgumentException();
		}

		try {
			roll.add(roll.size(), s);
		} catch (IllegalArgumentException e) {
			try {
				waitlist.enqueue(s);
			} catch (IllegalArgumentException e2) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Iterates through the course list and removes the student if it matches
	 * 
	 * @param s the student being checked
	 * @throws IllegalArgumentException if any error is thrown in the remove method
	 */
	public void drop(Student s) {
		nullCheck(s); 
		boolean removed = false;
		
		try {
			for (int i = 0; i < roll.size(); i++) {
				if (roll.get(i).equals(s)) {
					roll.remove(i);
					removed = true;
				}
			}
			if (removed && !waitlist.isEmpty()) {
				Student waitlistStudent = waitlist.dequeue();
				roll.add(roll.size(), waitlistStudent);
				waitlistStudent.getSchedule().addCourseToSchedule(course);
			} else if(!removed) {
				for (int i = 0; i < getNumberOnWaitlist(); i++) {
					Student waitlistStudent = waitlist.dequeue();
					if (!waitlistStudent.equals(s)) {
						waitlist.enqueue(waitlistStudent);
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Simple getter for open seats
	 * 
	 * @return the difference between the max allowed students and the current
	 *         students enrolled
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Checks if the student can enroll in the course
	 * 
	 * @param s the student being checked
	 * @return true if there are open seats in either the class or waitlist and the
	 *         student isn't already in the courselist or waitlist, false otherwise
	 */
	public boolean canEnroll(Student s) {
		if (waitlist.contains(s) || duplicateBoolean(s)) {
			return false;
		}
		if (getOpenSeats() == 0 && waitlistCap - getNumberOnWaitlist() == 0  ) {
			return false;
		}
		
		return true;
	}

	/**
	 * Simple getter for waitlist size
	 * 
	 * @return the number of students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

	/**
	 * Helper method. Confirms the element is not null
	 * 
	 * @param s the student being checked
	 * @throws IllegalArgumentException if the student is null
	 */
	private void nullCheck(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Helper method. Confirms the element is not null
	 * 
	 * @param s the student being checked
	 * @return true if the student is in the list
	 */
	private boolean duplicateBoolean(Student s) {
		return roll.contains(s);
	}
}
