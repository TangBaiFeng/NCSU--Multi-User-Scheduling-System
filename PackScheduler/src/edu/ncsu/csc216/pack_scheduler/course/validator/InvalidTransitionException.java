package edu.ncsu.csc216.pack_scheduler.course.validator;
/**
 * Constructor for the InvalidTransitionException error message. Will throw either a
 * default message or a message designated by the user
 * 
 * @author Troy Boone
 *
 */
public class InvalidTransitionException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Calls Exception superclass for transition  handling with a specific message
	 * 
	 * @param message the specific message for the Exception object
	 */
	public InvalidTransitionException(String message) {
		super(message);

	}

	/**
	 * Calls Exception superclass for transition handling with the default message
	 * "Schedule conflict"
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}

}