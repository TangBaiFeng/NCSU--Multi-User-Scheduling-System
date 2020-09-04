package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Test the FacultyRecordIO class 
 * @author troy1
 *
 */
public class FacultyRecordIOTest {
	/** Valid student records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	private final String actualOutput = "test-files/actual_faculty_records.txt";

	/** Expected results for valid faculty */
	private final String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2           ";
	private final String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3                ";
	private final String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1            ";
	private final String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3  ";
	private final String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1                 ";
	private final String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3       ";
	private final String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1     ";
	private final String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2  ";

	private String[] validFaculty = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7 };

	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Resets course_records.txt for use in other tests and also handles of hashing
	 * password
	 * 
	 * @throws Exception if file can't be found
	 */
	@Before
	public void setUp() throws Exception {
		try {

			Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
			Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
			try {
				Files.deleteIfExists(destinationPath);
				Files.copy(sourcePath, destinationPath);
			} catch (IOException e) {
				fail("Unable to reset files");
			}

			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
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
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * Tests readValidCourseRecords().
	 */
	@Test
	public void testReadStudentRecords() {
		// test valid works
		try {
			LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculty.size());
			boolean contained = false;
			for (int i = 0; i < validFaculty.length; i++) {
				String test = faculty.get(i).toString();
				for (int j = 0; j < validFaculty.length; j++) {
					if (validFaculty[j].contains(test)) {
						contained = true;
					}

				}
				assertTrue(contained);
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
		// test invalid doesn't work

	}

	/**
	 * Tests readInvalidCourseRecords().
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		try {
			LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, faculty.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeCourseRecords()
	 */
	@Test
	public void testWriteStudentRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(faculty.size(), new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		faculty.add(faculty.size(), new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3));
		faculty.add(faculty.size(), new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1));

		try {
			FacultyRecordIO.writeFacultyRecords(actualOutput, faculty);
		} catch (IOException e) {
			fail("Unexpected error writing " + actualOutput);
		}
		checkFiles("test-files/expected_faculty_records.txt", actualOutput);
	}

	/**
	 * Tests writeCourseRecords(). Test individually to confirm the error handling
	 * when the file being written to does not exist
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		// Assumption that you are using a hash of "pw" stored in hashPW
		// T
		try {
			FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_student_records.txt", faculty);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
			// The actual error message on Jenkins!
		}

	}

}
