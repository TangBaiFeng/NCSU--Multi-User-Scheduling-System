package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 */


public class StudentDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	private final String actualOutput = "test-files/actual_student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();

		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
	}
	/**
	 * Tests StudentDirectory.loadStudentsFromFile() with invalid students.
	 */
	@Test
	public void testLoadInvalidStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();

		// File with no valid students
		sd.loadStudentsFromFile(invalidTestFile);
		assertEquals(0, sd.getStudentDirectory().length);

		// File that can't be found
		try {
			sd.loadStudentsFromFile("testf-files/nonexistant-file");
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
	}

	/**
	 * Tests StudentDirectory.addStudent() with invalid params.
	 */
	@Test
	public void testAddInvalidStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test student with different passwords
		String[][] studentDirectory;
		try {
			sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "notapassword", PASSWORD, MAX_CREDITS);
			studentDirectory = sd.getStudentDirectory();
			assertEquals(0, studentDirectory.length);
		} catch (Exception e) {
			
			e.getMessage();
			}
		// Add a student already in the directory
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);

	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
	}

	/**
	 * Tests StudentDirectory.removeStudent() to confirm it will not remove or
	 * affect the directory by removing a non existant student.
	 */
	@Test
	public void testRemoveInvalidStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertFalse(sd.removeStudent("begger"));
		assertEquals(10, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();

		// Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory(actualOutput);
		checkFiles("test-files/expected_student_records.txt", actualOutput);
	}
	
/**
 * Test getStudentById
 */
@Test
public void testgetStudentById() {
	StudentDirectory sd = new StudentDirectory();
	sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
	sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
	assertEquals(2, sd.getStudentDirectory().length);
	assertEquals(sd.getStudentDirectory()[0][0] , sd.getStudentById(ID).getFirstName());
}
	

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
