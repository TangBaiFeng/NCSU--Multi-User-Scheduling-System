/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the Activity class for implementation of the ConflictException class.
 * 
 * @author troy1
 *
 */
public class ActivityTest {

	/**
	 * Test CheckConflict() to confirm it can differentiate different days Test
	 * method for
	 * {@link edu.ncsu.csc216.wolf_scheduler.course.Activity#checkConflict(edu.ncsu.csc216.wolf_scheduler.course.Activity)}.
	 */
	@Test
	public void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "TH", 1330, 1445);
		// Try a valid Test
		try {
			a1.checkConflict(a2);
			assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM",
					a2.getMeetingString());
		} catch (ConflictException e) {
			fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
		}

	}

	/**
	 * Test CheckConflict() to confirm an actual conflict does get indicated Test
	 * method for
	 * {@link edu.ncsu.csc216.wolf_scheduler.course.Activity#checkConflict(edu.ncsu.csc216.wolf_scheduler.course.Activity)}.
	 */
	@Test
	public void testValidCheckConflict() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "TH", 1330, 1445);
		a1.setMeetingDays("TH");
		a1.setActivityTime(1445, 1530);
		try {
			a1.checkConflict(a2);
			fail(); // ConflictException should have been thrown, but was not.
		} catch (ConflictException e) {
			// Check that the internal state didn't change during method call.
			assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
			assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
		}
	}

}
