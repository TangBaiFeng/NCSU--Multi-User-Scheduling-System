package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Maintains a catalog of all classes available for the student. Also handles
 * the add, get, remove methods for other classes to interact with courses.
 * 
 * @author Troy Boone
 */
public class CourseCatalog {
	/** List of courses in the catalog sorted */
	private SortedList<Course> catalog;

	/**
	 * Course catalog constructor which calls the newCourseCatalog method
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Initializes the catalog as an empty sortedlist of courses
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Constructs the course catalog by reading in course information from the given
	 * file.
	 * 
	 * @param fileName file containing list of students
	 * @throws IllegalArgumentException if file cannot be found
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Add a course to the catalog if it is able to meet the requirements of being
	 * initialized as a course and is not already contained in the catalog (same
	 * name and section)
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param meetingDays   meeting days for Course as series of chars
	 * @param startTime     start time
	 * @param endTime       end time
	 * @param enrollmentCap the number of students that can enroll in the course
	 * 
	 * @return true if the course is not a duplicate and was successfully added,
	 *         false if either the course is already in the catalog or the course
	 *         fails at being added to the catalog
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime,
				endTime);

		for (int i = 0; i < catalog.size(); i++) {
			if (course.getName().equals(catalog.get(i).getName())
					&& course.getSection().equals(catalog.get(i).getSection())) {

				return false;

			}
		}

		if (catalog.add(course)) {
			return true;
		}

		return false;
	}

	/**
	 * Removes a course if it can be found in the catalog (same name and section)
	 * 
	 * @param name    name of Course
	 * @param section section of Course
	 * 
	 * @return true if removed, false otherwise
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;

	}

	/**
	 * Searches through course catalog to find a course with matching name and
	 * section
	 * 
	 * @param name    the course name
	 * @param section the course section
	 * @return course if found in catalog, null if course not found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * Iterates over the arraylist course to generate a 2D string array as a visual
	 * representation of each course in the catalog
	 * 
	 * @return a 2D string array split between name, section, title, and
	 *         meetingString
	 */
	public String[][] getCourseCatalog() {
		String[][] courseCatalog = new String[catalog.size()][5];

		for (int i = 0; i < catalog.size(); i++) {
			courseCatalog[i][0] = catalog.get(i).getName();
			courseCatalog[i][1] = catalog.get(i).getSection();
			courseCatalog[i][2] = catalog.get(i).getTitle();
			courseCatalog[i][3] = catalog.get(i).getMeetingString();
			courseCatalog[i][4] = String.valueOf(catalog.get(i).getCourseRoll().getOpenSeats());
		}
		return courseCatalog;
	}

	/**
	 * Calls the exportCatalog method to write the schedule to file
	 * 
	 * @param fileName the file where the schedule will be printed
	 * @throws IllegalArgumentException if the file can't be saved
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved");
		}

	}
}
