package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * The general IO for the student aspect of NCSU Scheduler. It handles the
 * reading of student records, parsing it into student objects for other classes
 * to use, then to write the results to a file
 * 
 * @author troy1
 *
 */
public class StudentRecordIO {

	/**
	 * Reads the students information contains in the file and sends the string of
	 * information to processStudent(). Once returned, it is checked to see if this
	 * is a duplicate record. All students in the record will be sorted in order of lastname, firstname, ID
	 * 
	 * @param fileName The location of the student data as a string
	 * @return an sortedListlist of valid students records
	 * @throws FileNotFoundException if the file can not be found
	 * @throws FileNotFoundException if the file can not be read
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		while (fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());
				students.add(student);

//				Confirm that we don't need to check for duplicates
//				boolean duplicate = false;
//				for (int i = 0; i < students.size(); i++) {
//					Student c = students.get(i);
//					if (student.getFirstName().equals(c.getFirstName())
//							&& student.getLastName().equals(c.getLastName())) {
//						// it's a duplicate
//						duplicate = true;
//					}
//				}
//				if (!duplicate) {
//					students.add(student);
//				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return students;
	}

	/**
	 * Takes in the studentData string and parses into individual elements, which is
	 * then made into a student object to be returned
	 * 
	 * @param studentData a string containing student information needing to be
	 *                    parsed
	 * @return a valid student object
	 * @throws IllegalArgumentException if all elements of a student record is not
	 *                                  in the string
	 */
	private static Student processStudent(String studentData) {
		Scanner input = new Scanner(studentData);
		input.useDelimiter(",");

		String firstName = null;
		String lastName = null;
		String id = null;
		String email = null;
		String hashPassword = null;
		int maxCredits = 0;

		Student student = null;

// 		Assuming all student data is in the format of firstName, lastName, id, email, hashPassword, maxCredits
//
//		int commas = 0;
//		for (int i = 0; i < studentData.length(); i++) {
//			if (studentData.charAt(i) == ',')
//				commas++;
//		}
//		if (commas != 4 && commas != 5) {
//			input.close();
//			throw new IllegalArgumentException();
//		}		

		try {
			firstName = input.next();
			lastName = input.next();
			id = input.next();
			email = input.next();
			hashPassword = input.next();
			maxCredits = input.nextInt();
			student = new Student(firstName, lastName, id, email, hashPassword, maxCredits);

//	 		Assuming all student data is in the format of firstName, lastName, id, email, hashPassword, maxCredits
//
//				if (commas == 4) {
//					student = new Student(firstName, lastName, id, email, hashPassword);
//				} else {
//					student = new Student(firstName, lastName, id, email, hashPassword, maxCredits);
//				}

			input.close();
			return student;
		} catch (NoSuchElementException e) {
			input.close();
			throw new IllegalArgumentException("Course record is missing information");
		}

	}

	/**
	 * Takes the full student roster and writes their data to an output file
	 * 
	 * @param fileName         the location of the file being written to
	 * @param studentDirectory an sortedlist containing the full roster of student ordered by lastname, firstname, then id
	 * @throws IOException if output file can't be found
	 * @throws IOException if output file can't be written to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {

		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();

	}

}
