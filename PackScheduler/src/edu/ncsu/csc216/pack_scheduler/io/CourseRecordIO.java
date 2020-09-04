package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Sarah Heckman edited Troy Boone
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Course> courses = new SortedList<Course>();
		while (fileReader.hasNextLine()) {
			try {
				Course course = readCourse(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < courses.size(); i++) {
					Course c = courses.get(i);
					if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					courses.add(course);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return courses;
	}

	/**
	 * Reads the line that contains all the information to generate a course. If the
	 * string has the correct format, then it will get parsed. Otherwise it will
	 * throw an IllegalArgumentException
	 * 
	 * @param nextLine the string containing the course information
	 * @return a course made;
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	private static Course readCourse(String nextLine) {
		FacultyDirectory getInstructor = new FacultyDirectory();
		Scanner input = new Scanner(nextLine);
		input.useDelimiter(",");

		String name = null;
		String title = null;
		String section = null;
		int credits = 0;
		String instructorId = null;
		int enrollmentCap = 0;
		String meetingDays = null;
		int startTime = 0;
		int endTime = 0;
		Course course = null;

		int commas = 0;
		for (int i = 0; i < nextLine.length(); i++) {
			if (nextLine.charAt(i) == ',')
				commas++;
		}
		if (commas != 8 && commas != 6) {
			input.close();
			throw new IllegalArgumentException();
		}
		try {
			name = input.next();
			title = input.next();
			section = input.next();
			credits = input.nextInt();
			String newInstructorId = input.next();
			enrollmentCap = input.nextInt();
			meetingDays = input.next();

			if (commas == 8) {
				startTime = input.nextInt();
				endTime = input.nextInt();
			}
			if (commas == 8) {
				course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime,
						endTime);
			} else {
				course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays);
			}
			input.close();

			Faculty matchingFaculty = getInstructor.getFacultyById(newInstructorId);
			if (matchingFaculty != null) {
				matchingFaculty.getSchedule().addCourseToSchedule(course);
			}
			return course;
		} catch (NoSuchElementException e) {
			input.close();
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Writes the given list of Courses to the file named fileName.
	 * 
	 * @param fileName the string containing the location of the file name
	 * @param courses  the array list of courses needing to be written to
	 * @throws IOException if file is not found
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();

	}

}