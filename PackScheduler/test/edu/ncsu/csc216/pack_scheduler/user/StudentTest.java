package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
/**
 * Tests StudentDirectory.
 * 
 * @author Troy Boone
 */
public class StudentTest {
	/** Student first name */
	private static final String FIRST = "Troy";
	/** Student last name */
	private static final String LAST = "Boone";
	/** Student id string */
	private static final String ID = "tboone";
	/** Student email */
	private static final String EMAIL = "tboone@ncsu.edu";
	/** Student password */
	private static final String PASSWORD = "hashedpassword";
	/** Student Course Limit */
	private static final int CREDITS = 15;
	
	/**
	 * Tests Student() with credit modifier
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		// initialize Student
		Student s = null;

		// Test null first name

		try {
			s = new Student(null, LAST, ID, EMAIL, PASSWORD, CREDITS);

			fail();
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid first name", e.getMessage());
		}
		// Test null last name
		s = null;
		try {
			s = new Student(FIRST, null, ID, EMAIL, PASSWORD, CREDITS);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid last name", e.getMessage());
		}
		// Test null id
		s = null;
		try {
			s = new Student(FIRST, LAST, null, EMAIL, PASSWORD, CREDITS);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid id", e.getMessage());
		}
		// Test null email
		s = null;
		try {
			s = new Student(FIRST, LAST, ID, null, PASSWORD, CREDITS);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid email", e.getMessage());
		}
		// Test null password
		s = null;
		try {
			s = new Student(FIRST, LAST, ID, EMAIL, null, CREDITS);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid password", e.getMessage());
		}
		// Test 0 credits
		s = null;
		try {
			s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, 0);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid max credits", e.getMessage());
			
		}
		// check valid constructor
		s = null;
			s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(15, s.getMaxCredits());

	}
	/**
	 * Tests Student() without credit modifier
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		// initialize Student
		Student s = null;

		// Test null first name

		try {
			s = new Student(null, LAST, ID, EMAIL, PASSWORD);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid first name", e.getMessage());
		}
		// Test null last name
		s = null;
		try {
			s = new Student(FIRST, null, ID, EMAIL, PASSWORD);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid last name", e.getMessage());
		}
		// Test null id
		s = null;
		try {
			s = new Student(FIRST, LAST, null, EMAIL, PASSWORD);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid id", e.getMessage());
		}
		// Test null email
		s = null;
		try {
			s = new Student(FIRST, LAST, ID, null, PASSWORD);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid email", e.getMessage());
		}
		// Test null password
		s = null;
		try {
			s = new Student(FIRST, LAST, ID, EMAIL, null);

			fail(); 
		} catch (IllegalArgumentException e) {

			assertNull(s);
			assertEquals("Invalid password", e.getMessage());
		}

		// check valid constructor
		s = null;
			s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD);
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(18, s.getMaxCredits());

		
	} 
	/**
	 * Tests setFirstName()
	 */
	@Test
	public void testSetFirstName() {
		// Construct a valid Student
		Student s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);

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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
		}
		//Test valid setter
		s.setFirstName("Tang");
		assertEquals("Tang", s.getFirstName());

	}
	
	/**
	 * Tests setLastName()
	 */
	@Test
	public void testSetLastName() {
		// Construct a valid Student
		Student s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);

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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
		}
		//Test valid setter
				s.setLastName("Feng");
				assertEquals("Feng", s.getLastName());


	}
	/**
	 * Tests setEmail()
	 */
	@Test
	public void testSetEmail() {
		// Construct a valid Student
		Student s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);

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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
		}
		//Test valid setter
				s.setEmail("tfeng@ncsu.edu");
				assertEquals("tfeng@ncsu.edu", s.getEmail());

	}
	/**
	 * Tests setPassword()
	 */
	@Test
	public void testSetPassword() {
		// Construct a valid Student
		Student s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);

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
			assertEquals(15, s.getMaxCredits());
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
			assertEquals(15, s.getMaxCredits());
		}
		//Test valid setter
		s.setPassword("tangsect");
		assertEquals("tangsect", s.getPassword());
	}
	/**
	 * Tests setMaxCredits()
	 */
	@Test
	public void testSetMaxCredits() {
		// Construct a valid Student
		Student s = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);

		// Checks that value is unchanged when trying to set to more then 18
		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max credits", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(15, s.getMaxCredits());
		}
		// Checks that value is unchanged when trying to set to less then 3
		try {
			s.setMaxCredits(1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max credits", e.getMessage());
			assertEquals("Troy", s.getFirstName());
			assertEquals("Boone", s.getLastName());
			assertEquals("tboone", s.getId());
			assertEquals("tboone@ncsu.edu", s.getEmail());
			assertEquals("hashedpassword", s.getPassword());
			assertEquals(15, s.getMaxCredits());
		}
		//Test valid setter
		s.setMaxCredits(9);
		assertEquals(9, s.getMaxCredits());
	}
	/**
	 * Tests hashCode()
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s3 = new Student("mcdonald", LAST, ID, EMAIL, PASSWORD, CREDITS);

		assertEquals(s1.hashCode(), s2.hashCode());
		assertNotEquals(s1.hashCode(), s3.hashCode());
	}
	/**
	 * Tests equalsObject()
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s2 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		User s3 = new Student("mcdonald", LAST, ID, EMAIL, PASSWORD, CREDITS);

		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
	}
	/**
	 * Tests toString() 
	 */
	@Test
	public void testToString() {
		User s1 = new Student(FIRST, LAST, ID, EMAIL, PASSWORD, CREDITS);
		String test = "Troy,Boone,tboone,tboone@ncsu.edu,hashedpassword,15";
		assertEquals(test, s1.toString());
	}
	
	/**
	 * Tests toString() 
	 */
	@Test
	public void testCompareTo() {
		//Base Students
		Student s1 = new Student("abby", "boone", "aboone", EMAIL, PASSWORD, CREDITS);
		Student s2 = new Student("barbara", "raleigh", "braleigh", EMAIL, PASSWORD, CREDITS);
		Student s3 = new Student("cindy", "durham", "Cdurham", EMAIL, PASSWORD, CREDITS);
		//Same last name as base students
		Student s11 = new Student("abigale", "boone", "aboone", EMAIL, PASSWORD, CREDITS);       
		Student s22 = new Student("barbs", "raleigh", "braleigh", EMAIL, PASSWORD, CREDITS);
		Student s33 = new Student("cinthia", "durham", "Cdurham", EMAIL, PASSWORD, CREDITS);    
		//Same first and last name as base students
		Student s111 = new Student("abby", "boone", "aboone2", EMAIL, PASSWORD, CREDITS);       
		Student s222 = new Student("barbara", "raleigh", "braleigh2", EMAIL, PASSWORD, CREDITS);
	    Student s333 = new Student("cindy", "durham", "Cdurham2", EMAIL, PASSWORD, CREDITS);
	    
	   assertTrue(s1.compareTo(s2) < 0);
	   assertTrue(s3.compareTo(s2) < 0);
	   
	   assertTrue(s11.compareTo(s1) > 0);
	   assertTrue(s22.compareTo(s2) > 0);
	   assertTrue(s33.compareTo(s3) > 0);
	   
	   assertTrue(s111.compareTo(s1) > 0);
	   assertTrue(s222.compareTo(s2) > 0);
	   assertTrue(s333.compareTo(s3) > 0);
	}
	/**
	 * Test the canAdd and getSchedule method
	 */
	@Test
	public void testCanAdd() {
		CourseCatalog courseListing = new CourseCatalog();
		courseListing.loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, courseListing.getCourseCatalog().length);
		
		Course c1 = courseListing.getCourseFromCatalog("CSC116", "001");
		Course c2 = courseListing.getCourseFromCatalog("CSC216", "001");
		Course c3 = courseListing.getCourseFromCatalog("CSC230", "001");
		Course c4 = courseListing.getCourseFromCatalog("CSC116", "001"); //Duplicate conflict
		Course c5 = courseListing.getCourseFromCatalog("CSC226", "001"); // time conflict
		
		Student s1 = new Student("abby", "boone", "aboone", EMAIL, PASSWORD, 7);
		
		assertEquals(7, s1.getMaxCredits());
		assertEquals(0, s1.getSchedule().getScheduleCredits());
		
		assertTrue(s1.canAdd(c1));
		assertTrue(s1.canAdd(c2));
		assertTrue(s1.canAdd(c3));
		assertTrue(s1.canAdd(c4));
		assertTrue(s1.canAdd(c5));
		
		assertTrue(s1.getSchedule().addCourseToSchedule(c1));
		assertTrue(s1.getSchedule().addCourseToSchedule(c2));		
		assertEquals(7, s1.getSchedule().getScheduleCredits());
		
		assertFalse(s1.canAdd(c3)); //CREDIT CONFLICT
		assertFalse(s1.canAdd(c4)); //DUPLICATE CONFLICT
		assertFalse(s1.canAdd(c5)); //TIME CONFLICT
		
	}
}
