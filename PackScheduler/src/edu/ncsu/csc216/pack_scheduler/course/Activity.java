package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

/**
 * This class consist of methods used to generate and interact with the activity
 * method. It will be used to get and set the title, meeting days, start time
 * and end time of events. It has 1 child class, Course
 * 
 * @author Troy Boone
 *
 */
public abstract class Activity implements Conflict {

	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Constructs a generic activity object with these parameters. The child classe
	 * course will add on to these with their own additional parameters
	 * 
	 * @param title       the activity title
	 * @param meetingDays the activity meeting days
	 * @param startTime   the start time for the activity
	 * @param endTime     the end time for the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);

	}

	/**
	 * Gets the title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's title. If the title is null an IllegalArgumentException is
	 * thrown.
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or empty
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid course title");
		}
		this.title = title;
	}

	/**
	 * Get the meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Activity's meetingDays. The method has specific conditions that are
	 * different for courses and the override in those classes specify
	 * the requirements
	 * 
	 * @param meetingDays the meetingDays to set
	 */
	public void setMeetingDays(String meetingDays) {

		this.meetingDays = meetingDays;
	}

	/**
	 * Get the starting time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Get the ending time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the start time and end time of the course. It will throw an
	 * IllegalArgumentException if the hour is not between 0 and 23, the minute is
	 * not between 0 and 59, if the time format is wrong, or if the meeting days is
	 * set to A. Will return if called with meetingDays are A and both start and end
	 * times are 0
	 * 
	 * @param startTime course start time
	 * @param endTime   course end time
	 *         time are both 0
	 * @throws IllegalArgumentException when there is a time listed (beside 0) when
	 *                                    meeting days is 'A'
	 * @throws IllegalArgumentException when the end time is before the start time
	 * @throws IllegalArgumentException when the time format is wrong (not 3 or 4
	 *                                    digits)
	 * @throws IllegalArgumentException when the 'hours' field is not between 0 and
	 *                                    23 (inclusive)
	 * @throws IllegalArgumentException when the 'minute' field is not between 0 and
	 *                                    59 (inclusive)
	 * 
	 *  
	 */
	public void setActivityTime(int startTime, int endTime) {
		String startString = String.valueOf(startTime);
		String endString = String.valueOf(endTime);
		if (meetingDays.equals("A") && startTime == 0 && endTime == 0) {
			return;
		}
		if (meetingDays.equals("A") && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid meeting times");
		}

		if (endTime < startTime || startString.length() < 3 || startString.length() > 4 || endString.length() < 3
				|| endString.length() > 4) {
			throw new IllegalArgumentException("Invalid meeting times"); // confirms that the time format is either 3 or 4 digits, and doesn't
													// end before it begins
		}
		int startH = Integer.parseInt(startString.substring(0, startString.length() - 2)); // hour
		int startM = Integer.parseInt(startString.substring(startString.length() - 2)); // minute
		int endH = Integer.parseInt(endString.substring(0, endString.length() - 2));
		int endM = Integer.parseInt(endString.substring(endString.length() - 2));

		if (startH > 23 || startH < 0 || endH > 23 || endH < 0 || startM > 59 || startM < 0 || endM > 59 || endM < 0) {
			throw new IllegalArgumentException("Invalid meeting times"); // hour should be between 0 and 23, while minute should be between 0
													// and 59
		}

		this.startTime = startTime;
		this.endTime = endTime;

	}

	/**
	 * Returns a string of the meeting days with the time with it in the format of
	 * [days] [starttime]-[endtime] with am/pm attached. If meeting days is A, then
	 * the string will read Arranged
	 * 
	 * @return either the meeting days with time, or "Arranged" if A
	 */
	public String getMeetingString() {

		if (!meetingDays.equals("A")) {
			String sTimeEdit = null;
			String eTimeEdit = null;
			String startString = String.valueOf(startTime);
			String endString = String.valueOf(endTime);
			int startH = Integer.parseInt(startString.substring(0, startString.length() - 2)); // hour
			int startM = Integer.parseInt(startString.substring(startString.length() - 2)); // minute
			int endH = Integer.parseInt(endString.substring(0, endString.length() - 2));
			int endM = Integer.parseInt(endString.substring(endString.length() - 2));
			
			if (startTime <= 1159) {
				sTimeEdit = startH + ":" + String.format("%02d", startM) + "AM";
			} else if (startTime <= 1259 && startTime >= 1200) {
				sTimeEdit = startH + ":" + String.format("%02d", startM) + "PM";
			} else if (startTime >= 1300) {
				sTimeEdit = (startH - 12) + ":" + String.format("%02d", startM) + "PM";
			}
			if (endTime <= 1159) {
				eTimeEdit = endH + ":" + String.format("%02d", endM) + "AM";
			} else if (endTime <= 1259 && endTime >= 1200) {
				eTimeEdit = endH + ":" + String.format("%02d", endM) + "PM";
			} else if (endTime >= 1300) {
				eTimeEdit = (endH - 12) + ":" + String.format("%02d", endM) + "PM";
			}
			return meetingDays + " " + sTimeEdit + "-" + eTimeEdit;
		} else {
			return "Arranged";
		}
	}

	/**
	 * Generates a hashCode for the object Activity that can be used help identify
	 * the specific object. Subclasses Course will override this method
	 * with their own implementation to add their own parameters
	 * 
	 * @return hashcode for this Activity
	 */
	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}

	/**
	 * Compares a given object to this object for equality on all fields. Subclasses
	 * Course will override this method with their own implementation to
	 * add their own parameters
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}

	/**
	 * Checks to see if an activity is a conflict with another due to duplicate
	 * attributes. Subclasses Course will implement this method to add
	 * their own parameter
	 * 
	 * @param activity the activity being checked
	 * @return true if duplicate, false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Short display of Activity class. Subclasses Course will implement
	 * this method to add their own parameter
	 * 
	 * @return a string array to be used in GUI
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Long display of Activity class. Subclasses Course will implement
	 * this method to add their own parameter
	 * 
	 * @return a string array to be used in GUI
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Compare this.object to another object to see if they have conflicting days
	 * unless they are arranged. If they do, the start and end times are then
	 * compared to confirm that there is no overlap in start or end times
	 * 
	 * @param possibleConflictingActivity the activity being compared to this.object
	 * @throws ConflictException when 'this' start time is after
	 *                             possibleConflictingActivity start time while
	 *                             before possibleConflictingActivity end time
	 * @throws ConflictException when 'this' end time is after
	 *                             possibleConflictingActivity start time while
	 *                             before possibleConflictingActivity end time
	 * @throws ConflictException when 'this' start time is before
	 *                             possibleConflictingActivity start time while
	 *                             'this' end time is after
	 *                             possibleConflictingActivity end time
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		int conflictDays = -1;
		if (this.getMeetingDays().contains("A") && possibleConflictingActivity.getMeetingDays().contains("A")) {
			return;
		}
		for (char a : this.getMeetingDays().toCharArray()) {
			conflictDays = possibleConflictingActivity.getMeetingDays().indexOf(a);

			if (conflictDays != -1 && ((this.startTime >= possibleConflictingActivity.getStartTime()
					&& this.startTime <= possibleConflictingActivity.getEndTime())
					|| (this.endTime >= possibleConflictingActivity.getStartTime()
							&& this.endTime <= possibleConflictingActivity.getEndTime())
					|| (this.startTime <= possibleConflictingActivity.getStartTime()
							&& this.endTime >= possibleConflictingActivity.getEndTime()))) {
				throw new ConflictException();
			}

		}

	}

}