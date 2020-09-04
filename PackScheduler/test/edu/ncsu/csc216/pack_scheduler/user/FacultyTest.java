package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the faculty object
 * @author troy1
 *
 */
public class FacultyTest {

	/** Faculty first name */
	private static final String FIRST = "Troy";
	/** Faculty last name */
	private static final String LAST = "Boone";
	/** Faculty id string */
	private static final String ID = "tboone";
	/** Faculty email */
	private static final String EMAIL = "tboone@ncsu.edu";
	/** Faculty password */
	private static final String PASSWORD = "hashedpassword";
	/** Faculty Course Limit */
	private static final int COURSES = 2;

	/**
	 * Tests Faculty() with credit modifier
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		// initialize Faculty
		Faculty s = null;

		// Test null first name

		try {
			s = new Faculty(null, LAST, ID, EMAIL, PASSWORD, COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid first name", e.getMessage());
		}
		// Test null last name
		s = null;
		try {
			s = new Faculty(FIRST, null, ID, EMAIL, PASSWORD, COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid last name", e.getMessage());
		}
		// Test null id
		s = null;
		try {
			s = new Faculty(FIRST, LAST, null, EMAIL, PASSWORD, COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid id", e.getMessage());
		}
		// Test null email
		s = null;
		try {
			s = new Faculty(FIRST, LAST, ID, null, PASSWORD, COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid email", e.getMessage());
		}
		// Test null password
		s = null;
		try {
			s = new Faculty(FIRST, LAST, ID, EMAIL, null, COURSES);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid password", e.getMessage());
		}
		// Test 0 credits
		s = null;
		try {
			s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, 0);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid max courses", e.getMessage());

		}
		// check valid constructor
		s = null;
		s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);
		assertEquals("Troy", s.getFirstName());
		assertEquals("Boone", s.getLastName());
		assertEquals("tboone", s.getId());
		assertEquals("tboone@ncsu.edu", s.getEmail());
		assertEquals("hashedpassword", s.getPassword());
		assertEquals(2, s.getMaxCourses());

	}

	/**
	 * Tests setFirstName()
	 */
	@Test
	public void testSetFirstName() {
		// Construct a valid Faculty
		Faculty s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);

		// Checks that value is unchanged when trying to set to null
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid first name", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to an empty string
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid first name", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Test valid setter
		s.setFirstName("Tang");
		assertEquals("Tang", s.getFirstName());

	}

	/**
	 * Tests setLastName()
	 */
	@Test
	public void testSetLastName() {
		// Construct a valid Faculty
		Faculty s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);

		// Checks that value is unchanged when trying to set to null
		try {
			s.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid last name", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to an empty string
		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid last name", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Test valid setter
		s.setLastName("Feng");
		assertEquals("Feng", s.getLastName());

	}

	/**
	 * Tests setEmail()
	 */
	@Test
	public void testSetEmail() {
		// Construct a valid Faculty
		Faculty s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);

		// Checks that value is unchanged when trying to set to null
		try {
			s.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to an empty string
		try {
			s.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to a string that doesn't
		// contain the '@' char
		try {
			s.setEmail("testing(at)gmail.com");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to a string that doesn't
		// contain the '.' char
		try {
			s.setEmail("testing@gmail(dot)com");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to a string that has the
		// last '.' char infront of the '@' char
		try {
			s.setEmail("testing.this@gmail(dot)com");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Test valid setter
		s.setEmail("tfeng@ncsu.edu");
		assertEquals("tfeng@ncsu.edu", s.getEmail());

	}

	/**
	 * Tests setPassword()
	 */
	@Test
	public void testSetPassword() {
		// Construct a valid Faculty
		Faculty s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);

		// Checks that value is unchanged when trying to set to null
		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to an empty string
		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid password", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Test valid setter
		s.setPassword("tangsect");
		assertEquals("tangsect", s.getPassword());
	}

	/**
	 * Tests setMaxCourses()
	 */
	@Test
	public void testSetMaxCourses() {
		// Construct a valid Faculty
		Faculty s = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);

		// Checks that value is unchanged when trying to set to more then 3
		try {
			s.setMaxCourses(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max courses", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Checks that value is unchanged when trying to set to less then 1
		try {
			s.setMaxCourses(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max courses", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(2, s.getMaxCourses());
		}
		// Test valid setter
		s.setMaxCourses(3);
		assertEquals(3, s.getMaxCourses());
	}

	/**
	 * Tests hashCode()
	 */
	@Test
	public void testHashCode() {
		User s1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);
		User s2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);
		User s3 = new Faculty("mcdonald", LAST, ID, EMAIL, PASSWORD, COURSES);

		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
	}

	/**
	 * Tests equalsObject()
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);
		User s2 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);
		User s3 = new Faculty("mcdonald", LAST, ID, EMAIL, PASSWORD, COURSES);

		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
	}

	/**
	 * Tests toString()
	 */
	@Test
	public void testToString() {
		User s1 = new Faculty(FIRST, LAST, ID, EMAIL, PASSWORD, COURSES);
		String test = "Troy,Boone,tboone,tboone@ncsu.edu,hashedpassword,2";
		assertEquals(test, s1.toString());
	}
}
