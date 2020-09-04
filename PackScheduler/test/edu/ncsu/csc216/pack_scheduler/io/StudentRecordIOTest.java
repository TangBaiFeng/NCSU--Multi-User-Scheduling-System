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

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;
/**
 * Tests StudentDirectory.
 * 
 * @author Troy Boone
 */
public class StudentRecordIOTest {
	/** Valid student records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	private final String actualOutput = "test-files/actual_student_records.txt";

	/** Expected results for valid students */
	private final String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	private final String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	private final String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	private final String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	private final String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	private final String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	private final String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	private final String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	private final String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	private final String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/** Array to hold expected results */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };

	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Resets course_records.txt for use in other tests and also handles of hashing
	 * password
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

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
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
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());
			boolean contained = false;
			for (int i = 0; i < validStudents.length; i++) {
				String test = students.get(i).toString();
				for (int j = 0; j < validStudents.length; j++) {
					if (validStudents[j].contains(test)) {
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
			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());

		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writeCourseRecords()
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

		try {
			StudentRecordIO.writeStudentRecords(actualOutput, students);
		} catch (IOException e) {
			fail("Unexpected error writing " + actualOutput);
		}
		checkFiles("test-files/expected_student_records.txt", actualOutput);
	}

	/**
	 * Tests writeCourseRecords(). Test individually to confirm the error handling
	 * when the file being written to does not exist
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		// Assumption that you are using a hash of "pw" stored in hashPW
		// T
		try {
			StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
			// The actual error message on Jenkins!
		}

	}
}
