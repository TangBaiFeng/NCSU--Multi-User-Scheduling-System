package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all faculty enrolled at NC State. All faculty have a
 * unique id.
 * 
 * @author Troy Boone
 */
public class FacultyDirectory {
	/** List of students in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates an empty faculty directory.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Creates a new faculty directory
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * Load the list of faculty
	 * 
	 * @param fileName the files name being read
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Add a faculty member to the faculty directory
	 * 
	 * @param firstName the faculty first name	 * 
	 * @param lastName       the faculty last name
	 * @param id             the faculty id
	 * @param email          the faculty email
	 * @param password       the faculty password hashed
	 * @param repeatPassword the faculty password hashed repeated
	 * @param maxCourses     the max courses this specific faculty can host
	 * @return true if the faculty was added, false otherwise
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		// Duplicate Check
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			if (s.getId().equals(faculty.getId())) {
				return false;
			}
		}

		// Linked list did not return a boolean, so I use a temp variable to store the
		// current length. If the length increases, then it can be assumed that the
		// Element was added successfully
		int sizeIncreased = facultyDirectory.size();
		facultyDirectory.add(facultyDirectory.size(), faculty);
		return sizeIncreased < facultyDirectory.size();

	}

	/**
	 * Remove the faculty with the matching id from the faculty directory
	 * 
	 * @param id the id of the faculty to remove
	 * @return true if the faculty was removed, false otherwise
	 */
	public boolean removeFaculty(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			if (s.getId().equals(id)) {
				facultyDirectory.remove(i); // TODO note about piazza
				return true;
			}
		}
		return false;

	}

	/**
	 * Return a string array split to show the faculty members first name, last
	 * name, and id
	 * 
	 * @return a 2d string array of all faculty members
	 */
	public String[][] getFacultyDirectory() {
		String[][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;

	}

	/**
	 * Calls the writeFacultyRecords method to write the faculty directory to a file
	 * 
	 * @param fileName the files name
	 * @throws IllegalArgumentException if a IOException occurs
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

	/**
	 * Loops through the faculty directory and returns the faculty member with the
	 * first matching ID
	 * 
	 * @param id the id of the user searching for
	 * @return the faculty if its matches or null
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;

	}
}
