package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test the course roll object
 * @author Troy Boone
 *
 */ 
public class CourseRollTest {
	private StudentDirectory students = new StudentDirectory();

	/**
	 * Test CourseRoll, setEnrollmentCap and getEnrollmentCap
	 */ 
	@Test
	public void testCourseRoll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll test = c.getCourseRoll();
		assertEquals(10, test.getEnrollmentCap());
		assertEquals(10, test.getOpenSeats());
		assertEquals(0, test.getNumberOnWaitlist());
		test.setEnrollmentCap(250);
		assertEquals(250, test.getEnrollmentCap());
		assertEquals(250, test.getOpenSeats());
		assertEquals(0, test.getNumberOnWaitlist());
		test.setEnrollmentCap(40);
		assertEquals(40, test.getEnrollmentCap());
		assertEquals(40, test.getOpenSeats());
		assertEquals(0, test.getNumberOnWaitlist());

		// Not within accepted range
		try {
			test.setEnrollmentCap(251);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		try {
			test.setEnrollmentCap(9);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		try {
			test = new CourseRoll(c, 9);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		try {
			test = new CourseRoll(c, 259);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
	}

	/**
	 * Test Enroll
	 */
	@Test
	public void testEnroll() {
		// Setup
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll testEnroll = c.getCourseRoll();
		assertEquals(10, testEnroll.getEnrollmentCap());
		assertEquals(10, testEnroll.getOpenSeats());
		students.loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, students.getStudentDirectory().length);

		// Valid Enrollment
		for (int i = 0; i < students.getStudentDirectory().length - 1; i++) {
			testEnroll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertEquals(1, testEnroll.getOpenSeats());

		// Duplicate Student
		try {
			testEnroll.enroll(students.getStudentById(students.getStudentDirectory()[7][2]));
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		// Null Student
		try {
			Student student10 = null;
			testEnroll.enroll(student10);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		// Last slot filled
		testEnroll.enroll(students.getStudentById(students.getStudentDirectory()[9][2]));
		assertEquals(0, testEnroll.getOpenSeats());

		

		// Check that setEnrollmentCap can't set the cap below the current size
		try {
			testEnroll.setEnrollmentCap(11);
			Student student10 = new Student("first", "last", "flast", "flast@ncsu.edu", "pw");
			testEnroll.enroll(student10);
			testEnroll.setEnrollmentCap(10);
			fail();
		} catch (Exception e) {
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		
		// Waitlist enrolling testing
		Student student10 = new Student("first", "dast", "fdast", "flast@ncsu.edu", "pw");
		Student student11 = new Student("first", "bast", "fbast", "flast@ncsu.edu", "pw");
		Student student12 = new Student("first", "cast", "fcast", "flast@ncsu.edu", "pw");
		Student student13 = new Student("first", "gast", "fgast", "flast@ncsu.edu", "pw");
		testEnroll.enroll(student10);
		testEnroll.enroll(student11);
		testEnroll.enroll(student12);                                                                                                  
		testEnroll.enroll(student13);
		
		assertEquals(4, testEnroll.getNumberOnWaitlist());
	}

	/**
	 * Test Drop
	 */
	@Test
	public void testDrop() {
		// Setup
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll testDrop = c.getCourseRoll();
		assertEquals(10, testDrop.getEnrollmentCap());
		assertEquals(10, testDrop.getOpenSeats());
		students.loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, students.getStudentDirectory().length);

		// Valid Enrollment
		for (int i = 0; i < students.getStudentDirectory().length; i++) {
			testDrop.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		assertEquals(0, testDrop.getOpenSeats());
		
		Student student10 = new Student("first", "dast", "fdast", "flast@ncsu.edu", "pw");
		Student student11 = new Student("first", "bast", "fbast", "flast@ncsu.edu", "pw");
		Student student12 = new Student("first", "cast", "fcast", "flast@ncsu.edu", "pw");
		Student student13 = new Student("first", "gast", "fgast", "flast@ncsu.edu", "pw");
		testDrop.enroll(student10);
		testDrop.enroll(student11);
		testDrop.enroll(student12);                                                                                                  
		testDrop.enroll(student13);
		
		assertEquals(4, testDrop.getNumberOnWaitlist());
		
		// Loops through
		for (int i = 0; i < 4; i++) {
			Student test = students.getStudentById(students.getStudentDirectory()[i][2]);
			
			testDrop.drop(test);
			assertEquals(0, testDrop.getOpenSeats());
			assertEquals(3 - i,  testDrop.getNumberOnWaitlist());
		}

	}

	/**
	 * Test canEnroll
	 */
	@Test
	public void testCanEnroll() {
		// Setup
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll testCEnroll = c.getCourseRoll();
		assertEquals(10, testCEnroll.getEnrollmentCap());
		assertEquals(10, testCEnroll.getOpenSeats());
		students.loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, students.getStudentDirectory().length);

		// Valid Enrollment
		for (int i = 0; i < students.getStudentDirectory().length - 1; i++) {
			assertTrue(testCEnroll.canEnroll(students.getStudentById(students.getStudentDirectory()[i][2])));
			testCEnroll.enroll(students.getStudentById(students.getStudentDirectory()[i][2]));
		}
		//Duplicate class
		assertFalse(testCEnroll.canEnroll(students.getStudentById(students.getStudentDirectory()[5][2])));
		//Now on waitlist
		testCEnroll.enroll(students.getStudentById(students.getStudentDirectory()[9][2]));
		
		Student student10 = new Student("first", "dast", "fdast", "flast@ncsu.edu", "pw");
		Student student11 = new Student("first", "bast", "fbast", "flast@ncsu.edu", "pw");
		Student student12 = new Student("first", "cast", "fcast", "flast@ncsu.edu", "pw");
		Student student13 = new Student("first", "gast", "fgast", "flast@ncsu.edu", "pw");
		Student student14 = new Student("first", "last", "flast", "flast@ncsu.edu", "pw");
		assertTrue(testCEnroll.canEnroll(student10));
		assertTrue(testCEnroll.canEnroll(student11));
		assertTrue(testCEnroll.canEnroll(student12));                                                                                                  
		assertTrue(testCEnroll.canEnroll(student13));
		assertTrue(testCEnroll.canEnroll(student14));
		
	}
}
