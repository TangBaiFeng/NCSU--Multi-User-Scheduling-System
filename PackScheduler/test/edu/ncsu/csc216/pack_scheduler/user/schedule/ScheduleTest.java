package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test the Schedule Class
 * 
 * @author Troy Boone
 *
 */
public class ScheduleTest {
	private CourseCatalog courseListing = new CourseCatalog();

	/**
	 * Test Schedule constructor and getTitle method
	 */
	@Test
	public void testSchedule() {
		Schedule testC = new Schedule();
		assertEquals("My Schedule", testC.getTitle());
		assertEquals(0, testC.getScheduledCourses().length);
	}

	/**
	 * Test Schedule addCourseToSchedule and getScheduledCourses methods
	 */
	@Test
	public void testAddCourseToSchedule() {
		// Setup
		Schedule testAdd = new Schedule();
		courseListing.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, courseListing.getCourseCatalog().length);

		// Valid courses
		Course c1 = courseListing.getCourseFromCatalog("CSC116", "001");
		Course c2 = courseListing.getCourseFromCatalog("CSC216", "001");
		Course c3 = courseListing.getCourseFromCatalog("CSC230", "001");

		testAdd.addCourseToSchedule(c1);
		testAdd.addCourseToSchedule(c2);
		testAdd.addCourseToSchedule(c3);

		assertEquals(3, testAdd.getScheduledCourses().length);
		assertEquals("CSC116", testAdd.getScheduledCourses()[0][0]);
		assertEquals("001", testAdd.getScheduledCourses()[0][1]);
		assertEquals("10", testAdd.getScheduledCourses()[0][4]);
		assertEquals("CSC216", testAdd.getScheduledCourses()[1][0]);
		assertEquals("001", testAdd.getScheduledCourses()[1][1]);
		assertEquals("10", testAdd.getScheduledCourses()[1][4]);
		assertEquals("CSC230", testAdd.getScheduledCourses()[2][0]);
		assertEquals("001", testAdd.getScheduledCourses()[2][1]);
		assertEquals("10", testAdd.getScheduledCourses()[2][4]);

		// Invalid courses
		try {
			// Time conflict
			Course c4 = courseListing.getCourseFromCatalog("CSC226", "001");
			testAdd.addCourseToSchedule(c4);
			fail();
		} catch (Exception e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
		try {
			// Duplicate error
			Course c4 = courseListing.getCourseFromCatalog("CSC116", "002");
			testAdd.addCourseToSchedule(c4);
			fail();
		} catch (Exception e) {
			assertEquals("You are already enrolled in CSC116", e.getMessage());
		}
		
		try {
			//Null course error
			testAdd.addCourseToSchedule(null);
		} catch (Exception e) {
			assertEquals("java.lang.NullPointerException", e.toString());
		}

	}

	/**
	 * Test Schedule removeCourseFromSchedule method
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		// Setup
		Schedule testRemove = new Schedule();
		courseListing.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, courseListing.getCourseCatalog().length);

		// Valid courses
		Course c1 = courseListing.getCourseFromCatalog("CSC116", "001");
		Course c2 = courseListing.getCourseFromCatalog("CSC216", "001");
		Course c3 = courseListing.getCourseFromCatalog("CSC230", "001");

		testRemove.addCourseToSchedule(c1);
		testRemove.addCourseToSchedule(c2);
		testRemove.addCourseToSchedule(c3);

		assertEquals(3, testRemove.getScheduledCourses().length);
		assertEquals("CSC116", testRemove.getScheduledCourses()[0][0]);
		assertEquals("001", testRemove.getScheduledCourses()[0][1]);
		assertTrue(testRemove.removeCourseFromSchedule(c1));
		assertFalse(testRemove.removeCourseFromSchedule(c1)); // Test that removing an course not in the schedule returns
		// false

		assertEquals(2, testRemove.getScheduledCourses().length);
		assertEquals("CSC216", testRemove.getScheduledCourses()[0][0]);
		assertEquals("001", testRemove.getScheduledCourses()[0][1]);
		assertTrue(testRemove.removeCourseFromSchedule(c2));
		assertFalse(testRemove.removeCourseFromSchedule(c2));

		assertEquals(1, testRemove.getScheduledCourses().length);
		assertEquals("CSC230", testRemove.getScheduledCourses()[0][0]);
		assertEquals("001", testRemove.getScheduledCourses()[0][1]);
		assertTrue(testRemove.removeCourseFromSchedule(c3));
		assertEquals(0, testRemove.getScheduledCourses().length);

	}

	/**
	 * Test Schedule resetSchedule method
	 */
	@Test
	public void testResetSchedule() {
		// Setup
		Schedule testReset = new Schedule();
		courseListing.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, courseListing.getCourseCatalog().length);

		// Valid courses
		Course c1 = courseListing.getCourseFromCatalog("CSC116", "001");
		Course c2 = courseListing.getCourseFromCatalog("CSC216", "001");
		Course c3 = courseListing.getCourseFromCatalog("CSC230", "001");

		testReset.addCourseToSchedule(c1);
		testReset.addCourseToSchedule(c2);
		testReset.addCourseToSchedule(c3);

		assertEquals(3, testReset.getScheduledCourses().length);
		testReset.resetSchedule();
		assertEquals(0, testReset.getScheduledCourses().length);
	}

	/**
	 * Test Schedule setTitle and getTitle methods
	 */
	@Test
	public void testSetTitle() {
		Schedule testTitle = new Schedule();
		assertEquals("My Schedule", testTitle.getTitle());
		//Valid Title
		testTitle.setTitle("My Super Duper Schedule");
		assertEquals("My Super Duper Schedule", testTitle.getTitle());
		
		//Invalid Title
		try {
			//null title
			testTitle.setTitle(null);
			fail();
		} catch (Exception e) {
			assertEquals("Title cannot be null.", e.getMessage());
		}
		try {
			//empty title
			testTitle.setTitle("");
			fail();
		} catch (Exception e) {
			assertEquals("Title cannot be null.", e.getMessage());
		}
	}

	/**
	 * Test canAdd and getScheduleCredits
	 */
	@Test
	public void testCanAdd() {
		Schedule testUpdate = new Schedule();
		courseListing.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, courseListing.getCourseCatalog().length);
		
		Course c1 = courseListing.getCourseFromCatalog("CSC116", "001");
		Course c2 = courseListing.getCourseFromCatalog("CSC216", "001");
		Course c3 = courseListing.getCourseFromCatalog("CSC230", "001");
		Course c4 = courseListing.getCourseFromCatalog("CSC116", "001"); //Duplicate conflict
		Course c5 = courseListing.getCourseFromCatalog("CSC226", "001"); // time conflict
		
		assertTrue(testUpdate.canAdd(c1));
		assertTrue(testUpdate.canAdd(c2));
		assertTrue(testUpdate.canAdd(c3));
		assertTrue(testUpdate.canAdd(c4));
		assertTrue(testUpdate.canAdd(c5));
		
		assertEquals(0, testUpdate.getScheduleCredits());
		testUpdate.addCourseToSchedule(c1);
		testUpdate.addCourseToSchedule(c2);
		testUpdate.addCourseToSchedule(c3);
		assertEquals(10, testUpdate.getScheduleCredits());
		
		assertFalse(testUpdate.canAdd(c1));
		assertFalse(testUpdate.canAdd(c4));
		assertFalse(testUpdate.canAdd(c5));
		
	}
}
