package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Constructor for the ConflictException error message. Will throw either a
 * default message or a message designated by the user
 * 
 * @author Troy Boone
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Calls Exception superclass for conflict handling with a speicifc message
	 * 
	 * @param message the specific message for the Exception object
	 */
	public ConflictException(String message) {
		super(message);

	}

	/**
	 * Calls Exception superclass for conflict handling with the default message
	 * "Schedule conflict"
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}

}
