package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Test the CourseCatalog() class and methods
 * @author Troy Boone
 */
public class CourseCatalogTest {
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	/** Course capacity*/
	private static final int ENROLLMENTCAP = 10;

	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if files can't be reset
	 */
	@Before
	public void setUp() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests CourseCatalog().
	 */
	@Test
	public void testCourseCatalog() {
		//Test with invalid file.  Should have an empty catalog and schedule. 
		CourseCatalog cc1 = new CourseCatalog();
		cc1.loadCoursesFromFile(invalidTestFile);
		assertEquals(0, cc1.getCourseCatalog().length);
		
		//Test with valid file containing 9 courses.  Will test other methods in other tests.
		CourseCatalog cc2 = new CourseCatalog();
		cc2.loadCoursesFromFile(validTestFile);
		assertEquals(8, cc2.getCourseCatalog().length);		
	}
	
	//Already tested for loadCourseFromCatalog() in constructor test
	
	/**
	 * Tests addCourseToCatalog()
	 */
	@Test
	public void testAddCourseToCatalog(){
		CourseCatalog cc2 = new CourseCatalog();
		cc2.loadCoursesFromFile(validTestFile);
		//Attempt to add a course that is invalid--test that error is thrown through the course constructor 
		try {			
			cc2.addCourseToCatalog(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENTCAP, MEETING_DAYS, START_TIME,
					END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(8, cc2.getCourseCatalog().length);
		}
		//Attempt to add a course that is valid
		assertTrue(cc2.addCourseToCatalog("HUM110", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENTCAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(9, cc2.getCourseCatalog().length);
		//Attempt to add a course that is invalid-duplicate
		assertFalse(cc2.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENTCAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(9, cc2.getCourseCatalog().length);
	}
	/**
	 * Tests removeCourseFromCatalog()
	 */
	@Test
	public void testRemoveCourseFromCatalog(){
		CourseCatalog cc2 = new CourseCatalog();
		//Attempt to remove from empty catalog
		assertFalse(cc2.removeCourseFromCatalog(NAME, SECTION));
		
		cc2.loadCoursesFromFile(validTestFile);
		assertEquals(8, cc2.getCourseCatalog().length);
		//Test removing the right course
		Course courseName = cc2.getCourseFromCatalog("CSC116", "001");
		assertEquals(courseName.getName(), "CSC116");
		assertEquals(courseName.getSection(), "001");
		assertTrue(cc2.removeCourseFromCatalog("CSC116", "001"));
		assertNull(cc2.getCourseFromCatalog("CSC116", "001"));
		assertEquals(7, cc2.getCourseCatalog().length);
		//Test removing a non existent course
		assertFalse(cc2.removeCourseFromCatalog("HUM110", "001"));
		assertEquals(7, cc2.getCourseCatalog().length);
	}
	/**
	 * Tests getCourseFromCatalog()
	 */
	@Test
	public void testGetCourseFromCatalog(){
		CourseCatalog cc2 = new CourseCatalog();
		//Attempt to find a course from empty catalog
		assertFalse(cc2.removeCourseFromCatalog(NAME, SECTION));
		
		cc2.loadCoursesFromFile(validTestFile);
		assertEquals(8, cc2.getCourseCatalog().length);
		
		//Test getting valid courses 
		Course courseName = cc2.getCourseFromCatalog("CSC116", "001");
		assertEquals(courseName.getName(), "CSC116");
		assertEquals(courseName.getSection(), "001");
		courseName = cc2.getCourseFromCatalog("CSC216", "002");
		assertEquals(courseName.getName(), "CSC216");
		assertEquals(courseName.getSection(), "002");
		courseName = cc2.getCourseFromCatalog("CSC226", "001");
		assertEquals(courseName.getName(), "CSC226");
		assertEquals(courseName.getSection(), "001");
		//Test getting invalid courses
		assertNull(cc2.getCourseFromCatalog("HUM110", "001"));
		assertEquals(8, cc2.getCourseCatalog().length);
	}
	/**
	 * Tests getCourseCatalog()
	 */
	@Test
	public void testGetCourseCatalog(){
		CourseCatalog cc2 = new CourseCatalog();
		cc2.loadCoursesFromFile(validTestFile);
		assertEquals(8, cc2.getCourseCatalog().length);
		
		String [][] cCatalog = cc2.getCourseCatalog();
		//Row 0
				assertEquals("CSC116", cCatalog[0][0]);
				assertEquals("001", cCatalog[0][1]);
				assertEquals("Intro to Programming - Java", cCatalog[0][2]);
				assertEquals("MW 9:10AM-11:00AM", cCatalog[0][3]);
				//Row 1
				assertEquals("CSC116", cCatalog[1][0]);
				assertEquals("002", cCatalog[1][1]);
				assertEquals("Intro to Programming - Java", cCatalog[1][2]);
				assertEquals("MW 11:20AM-1:10PM", cCatalog[1][3]);
				//Row 2
				assertEquals("CSC116", cCatalog[2][0]);
				assertEquals("003", cCatalog[2][1]);
				assertEquals("Intro to Programming - Java", cCatalog[2][2]);
				assertEquals("TH 11:20AM-1:10PM", cCatalog[2][3]);
				//Row 3
				assertEquals("CSC216", cCatalog[3][0]);
				assertEquals("001", cCatalog[3][1]);
				assertEquals("Programming Concepts - Java", cCatalog[3][2]);
				assertEquals("TH 1:30PM-2:45PM", cCatalog[3][3]);
				//Row 4
				assertEquals("CSC216", cCatalog[4][0]);
				assertEquals("002", cCatalog[4][1]);
				assertEquals("Programming Concepts - Java", cCatalog[4][2]);
				assertEquals("MW 1:30PM-2:45PM", cCatalog[4][3]);
				//Row 5
				assertEquals("CSC216", cCatalog[5][0]);
				assertEquals("601", cCatalog[5][1]);
				assertEquals("Programming Concepts - Java", cCatalog[5][2]);
				assertEquals("Arranged", cCatalog[5][3]);
				//Row 6
				assertEquals("CSC226", cCatalog[6][0]);
				assertEquals("001", cCatalog[6][1]);
				assertEquals("Discrete Mathematics for Computer Scientists", cCatalog[6][2]);
				assertEquals("MWF 9:35AM-10:25AM", cCatalog[6][3]);
				//Row 7
				assertEquals("CSC230", cCatalog[7][0]);
				assertEquals("001", cCatalog[7][1]);
				assertEquals("C and Software Tools", cCatalog[7][2]);
				assertEquals("MW 11:45AM-1:00PM", cCatalog[7][3]);
	}
	/**
	 * Test exportCatalog().
	 */
	@Test
	public void testSaveCourseCatalog() {
		//Test that empty schedule exports correctly
		CourseCatalog cc2 = new CourseCatalog();
		cc2.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//Add courses and test that exports correctly
	
		cc2.addCourseToCatalog("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);         
		cc2.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);        
		cc2.addCourseToCatalog("CSC216", "Programming Concepts - Java", "601", 4, "jep", 10, "A", 0 , 0 );                          
		assertEquals(3, cc2.getCourseCatalog().length);
		cc2.saveCourseCatalog("test-files/actual_courses_record.txt");
		
		checkFiles("test-files/expected_course_records.txt", "test-files/actual_courses_record.txt");
	}
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
