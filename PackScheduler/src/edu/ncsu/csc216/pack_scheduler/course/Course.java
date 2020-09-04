package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Extends Activities to add fields for course name, section, credit hours, and
 * instructor ID associated with the class. Handles overrides for meetingdays,
 * duplicate event, and GUI display logic
 * 
 * @author Troy Boone
 *
 */
public class Course extends Activity implements Comparable<Course> {
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Mandatory length of a section number */
	private static final int SECTION_LENGTH = 3;
	/** Maximum number of credits a course can have */
	private static final int MAX_CREDITS = 5;
	/** Minimum number of credits a course can have */
	private static final int MIN_CREDITS = 1;
	/** Course roll object of students in the course */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields. It inherits title,
	 * meetingDays, startTime and endTime from Activity superclass
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param meetingDays   meeting days for Course as series of chars
	 * @param startTime     start time
	 * @param endTime       end time	 * 
	 * @param enrollmentCap the number of students that can enroll in the course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);

	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * and meetingDays for courses that are arranged. It inherits title,
	 * meetingDays, startTime and endTime from Activity superclass
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param meetingDays   meeting days for Course as series of chars
	 * @param enrollmentCap the number of students that can enroll in the course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		super(title, meetingDays, 0, 0);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);

	}

	/**
	 * Gets the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Gets the credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Gets the instructorID
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 4 or
	 * greater than 6, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null
	 * @throws IllegalArgumentException length is less than 4 or greater than 6
	 */
	private void setName(String name) {
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException("Invalid course name");
		}
		CourseNameValidator test = new CourseNameValidator();
		try {
			test.isValid(name);
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name");

		}
		this.name = name;
	}

	/**
	 * Sets the Course's section. If the section is null or not a set of 3 numbers
	 * other than 000, an IllegalArgumentException is thrown. I use the test
	 * variable to make sure that the string is a set of integers
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is null
	 * @throws IllegalArgumentException if section length is not 3 digits long
	 *                                  (other then 000)
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH || section.equals("000")) {
			throw new IllegalArgumentException("Invalid section number");
		}
		try {
			int test = Integer.parseInt(section); // PMD doesn't like the above line so I am using a
													// if statement so the variable is used.
			if (test < 0) {
				test = 0;
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid section number");
		}

		this.section = section;
	}

	/**
	 * Sets the Course's credits. If the title is not between the predetermined min
	 * and max credits, an IllegalArgumentException is thrown.
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if the credits are not between MIN_CREDITS
	 *                                  and MAX_CREDITS
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid course credits");
		}
		this.credits = credits;
	}

	/**
	 * Sets the Course's instructorId. If the title is null or empty, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if name is null or empty
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null) {
			this.instructorId = instructorId;
			return;
		}
		if (instructorId.equals("") || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor unity id");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Overrides the Activity setMeetingDays() method to check that the meeting days
	 * listed are a weekday acronym or every day(A)
	 * 
	 * @param meetingDays the days the course will be held
	 * @throws IllegalArgumentException if meetingDays is null
	 * @throws IllegalArgumentException if meetingDays contains the char 'A' with
	 *                                  other chars
	 * @throws IllegalArgumentException if meetingDays contains any other chars
	 *                                  other then "MTWHFA"
	 * @throws IllegalArgumentException if meetingDays is 'A' and has a start or end
	 *                                  time that is not 0
	 * 
	 * 
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		if (meetingDays.indexOf('A') != -1 && meetingDays.length() != 1) {
			throw new IllegalArgumentException("Invalid meeting days");
		}

		meetingDays = meetingDays.toUpperCase();

		for (char c : meetingDays.toCharArray()) {
			if ("MTWHFA".indexOf(c) == -1) {
				throw new IllegalArgumentException("Invalid meeting days");
			}
		}
		if (meetingDays.equals("A") && (getStartTime() != 0 || getEndTime() != 0)) {
			throw new IllegalArgumentException("Invalid meeting days");
		}

		super.setMeetingDays(meetingDays);
	}

	/**
	 * Overrides the default toString to include weekly repeat and event details
	 * 
	 * @return a string in the format
	 *         name,title,section,credits,insctructorId,meetingDays, then startTime
	 *         and endTime if applicable
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return getName() + "," + getTitle() + "," + getSection() + "," + getCredits() + "," + getInstructorId()
					+ "," + getCourseRoll().getEnrollmentCap() + "," + getMeetingDays();
		}
		return getName() + "," + getTitle() + "," + getSection() + "," + getCredits() + "," + getInstructorId() + ","
				+ getCourseRoll().getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + ","
				+ getEndTime();
	}

	

	/**
	 * Override of Activities getShortDisplayArray(). Generates an array of length 4
	 * that formats the object for the GUI to display in order of name, section,
	 * title, and meeting days and time in string format
	 * 
	 * @return an array formatted for the GUI
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] returnArray = new String[5];
		returnArray[0] = getName();
		returnArray[1] = getSection();
		returnArray[2] = getTitle();
		returnArray[3] = getMeetingString();
		returnArray[4] = String.valueOf(getCourseRoll().getOpenSeats());
		return returnArray;
	}

	/**
	 * Override of Activities getLongDisplayArray(). Generates an array of length 7
	 * that formats the object for the GUI to display in order of name, section,
	 * title, credits for the course, instructor id, and meeting days and time in
	 * string format.
	 * 
	 * @return an array formatted for the GUI
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] returnArray = new String[7];
		returnArray[0] = getName();
		returnArray[1] = getSection();
		returnArray[2] = getTitle();
		returnArray[3] = String.valueOf(getCredits());
		returnArray[4] = getInstructorId();
		returnArray[5] = getMeetingString();
		returnArray[6] = "";
		return returnArray;
	}

	/**
	 * Override of Activities isDuplicate(). Confirms if the class is considered a
	 * duplicate to another. The comparison used is the course name.
	 * 
	 * @param activity the event being compared
	 * @throws IllegalArgumentException if the course is already in the schedule
	 */
	@Override
	public boolean isDuplicate(Activity activity) {

		if (activity.getClass() == getClass()) {

			if (((Course) activity).getName().equals(getName())) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Overrides the default compareTo method. It will compare 'this' object to
	 * another in priority order of course name, to section
	 * 
	 * @return the student object as a string
	 */
	@Override
	public int compareTo(Course c) {
		int difference = 0;
		difference = this.getName().toLowerCase().compareTo(c.getName().toLowerCase());

		if (difference == 0) {
			difference = this.getSection().compareTo(c.getSection());
		}

		return difference;
	}

	/**
	 * Simple CourseRoll getter
	 * 
	 * @return roll the list of students enrolled in course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(credits, instructorId, name, roll.getEnrollmentCap(), section);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(name, other.name) && Objects.equals(roll.getEnrollmentCap(), other.roll.getEnrollmentCap())
				&& Objects.equals(section, other.section);
	}
}
