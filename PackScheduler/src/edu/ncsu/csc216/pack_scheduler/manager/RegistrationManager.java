package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Utility class for handling interactions between the User objects (student and
 * registar) and the different directories (studentdirectory and course
 * catalog), including user login activity
 * 
 * @author Troy Boone
 *
 */
public class RegistrationManager {

	private static RegistrationManager instance;
	/** The list of classes */
	private CourseCatalog courseCatalog;
	/** The list of students */
	private StudentDirectory studentDirectory;
	/** The list of faculty */
	private FacultyDirectory facultyDirectory;
	/** User with registar status */
	private User registrar;
	/** User currently logged in */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Calls the createRegistrar to construct a registar once a RegistrationManager
	 * is created
	 */
	private RegistrationManager() {
		createRegistrar();
		facultyDirectory = new FacultyDirectory();
		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
		
	}

	/**
	 * Creates a registar object, which is a child class of user.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Hashes the plain text password using the predetermined HASH_ALGORITHM
	 * 
	 * @param pw the password to be hashed
	 * @return the hashed password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Makes an instance of RegistrationManager if there is none, otherwise returns
	 * instance
	 * 
	 * @return the current instance of RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Getter for CourseCatalog
	 * 
	 * @return sorted list CourseCatalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Getter for FacultyDirectory
	 * 
	 * @return linked list facultyDirectory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Getter for StudentDirectory
	 * 
	 * @return sorted list StudentDirectory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * 'Logs in the user' for the current session. If the id returns a student, log
	 * in the user as a student. Otherwise, it is checked to see if the user is a
	 * registar
	 * 
	 * @param id       the users ID
	 * @param password the users password unhashed
	 * @return true if the user logged in, false otherwise
	 * @throws IllegalArgumentException if the algorithm can't be handled
	 */
	public boolean login(String id, String password) {
		if (currentUser != null) {
			return false;
		}

		if (registrar.getId().equals(id)) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}

		Student s = studentDirectory.getStudentById(id);

		if (s != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (s.getPassword().equals(localHashPW)) {
					currentUser = s;
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}
		Faculty f = facultyDirectory.getFacultyById(id);

		if (f == null) {
			throw new IllegalArgumentException("User doesn't exist.");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			String localHashPW = new String(digest.digest());
			if (f.getPassword().equals(localHashPW)) {
				currentUser = f;
				return true;
			} else {
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Clears the current user variable
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Gets the current logged in user
	 * 
	 * @return the User object for the active user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clears both courseCatalog and studentDirectory
	 */
	public void clearData() {
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
	}

	/**
	 * Private inner class for RegistrationManager. Subclass of of user
	 *
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password in the
		 * registrar.properties file.
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * Have registar assign course to faculty
	 * 
	 * @param course  the course being assigned
	 * @param faculty the faculty being assigned too
	 * @return true if the course is assigned to the faculty's schedule
	 * @throws IllegalArgumentException if the current user is null or not the
	 *                                  registar
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser instanceof Registrar) {
			faculty.getSchedule().addCourseToSchedule(course);
			return true;
		} else {
			throw new IllegalArgumentException();

		}
	}

	/**
	 * Have registar remove a course from faculty's schedule
	 * 
	 * @param course  the course being removed
	 * @param faculty the faculty with the course being removed
	 * @return true if the course was removed from the faculty's schedule
	 * @throws IllegalArgumentException if the current user is null or not the
	 *                                  registar
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser instanceof Registrar) {
			return faculty.getSchedule().removeCourseFromSchedule(course);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Call facultyschedule.resetschedule to remove all items from the faculty's
	 * schedule
	 * 
	 * @param faculty the faculty with the schedule being reset
	 * @throws IllegalArgumentException if the current user is null or not the
	 *                                  registar
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser != null && currentUser instanceof Registrar) {
			faculty.getSchedule().resetSchedule();
		} else {
			throw new IllegalArgumentException();
		}
	}

}
