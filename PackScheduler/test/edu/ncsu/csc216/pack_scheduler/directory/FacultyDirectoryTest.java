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
 * Test the FacultyDirectory
 * 
 * @author Troy Boone
 *
 */
public class FacultyDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	private final String actualOutput = "test-files/actual_faculty_records.txt";
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
	/** Test max course */
	private static final int MAX_COURSES = 2;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		// Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory sd = new FacultyDirectory();
		assertFalse(sd.removeFaculty("sesmith5"));
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newFacultyDirectory().
		FacultyDirectory sd = new FacultyDirectory();

		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);

		sd.newFacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile().
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid file
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
	}
	/**
	 * Tests FacultyDirectory.loadFacultysFromFile() with invalid students.
	 */
	@Test
	public void testLoadInvalidFacultysFromFile() {
		FacultyDirectory sd = new FacultyDirectory();

		// File with no valid students
		sd.loadFacultyFromFile(invalidTestFile);
		assertEquals(0, sd.getFacultyDirectory().length);

		// File that can't be found
		try {
			sd.loadFacultyFromFile("testf-files/nonexistant-file");
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test valid Faculty
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String[][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
	}

	/**
	 * Tests FacultyDirectory.addFaculty() with invalid params.
	 */
	@Test
	public void testAddInvalidFaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Test student with different passwords
		String[][] facultyDirectory;
		try {
			sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "notapassword", PASSWORD, MAX_COURSES);
			facultyDirectory = sd.getFacultyDirectory();
			assertEquals(0, facultyDirectory.length);
		} catch (Exception e) {
			
			e.getMessage();
			}
		// Add a student already in the directory
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);

	}

	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add students and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertTrue(sd.removeFaculty("awitt"));
		String[][] studentDirectory = sd.getFacultyDirectory();
		assertEquals(7, studentDirectory.length);
	}

	/**
	 * Tests FacultyDirectory.removeFaculty() to confirm it will not remove or
	 * affect the directory by removing a non existant student.
	 */
	@Test
	public void testRemoveInvalidFaculty() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add students and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertFalse(sd.removeFaculty("begger"));
		assertEquals(8, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory sd = new FacultyDirectory();

		// Add a student
		sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		assertEquals(1, sd.getFacultyDirectory().length);
		sd.saveFacultyDirectory(actualOutput);
		checkFiles("test-files/expected_faculty_records.txt", actualOutput);
	}
	
/**
 * Test getFacultyById
 */
@Test
public void testgetFacultyById() {
	FacultyDirectory sd = new FacultyDirectory();
	sd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 2);
	sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
	assertEquals(2, sd.getFacultyDirectory().length);
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