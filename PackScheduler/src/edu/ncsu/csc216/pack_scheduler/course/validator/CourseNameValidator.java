package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Finite state machine class which intent is to filter course names to confirm
 * if they are of a valid format or not. A valid format is 1-4 letters, followed
 * by exactly 3 numbers, followed by an optional letter.
 * 
 * @author Troy Boone
 *
 */
public class CourseNameValidator {
	/** The truth value of if the course name is valid */
	private boolean validEndState;
	/** The total count of letters before the suffix */
	private int letterCount;
	/** The total count of digits */
	private int digitCount;
	/** The current state of the FSM */
	private State state;

	/**
	 * Method for course name validity checking. It will run through each character
	 * of the course name and upon reading it, call the method for the current state
	 * the validity checker is at
	 * 
	 * @param courseName the course name checking for validity
	 * @return true if the course name is valid, false otherwise
	 * @throws InvalidTransitionException if there are not 3 digits
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		state = new InitialState();

		// Create a variable to track the current character index
		int charIndex = 0;

		// Variable to keep track of the current input character being examined
		char c;

		// Iterate through the ID, examining one character at a time
		while (charIndex < courseName.length()) {
			// Set the current character being examined
			c = courseName.charAt(charIndex);

			// Throw an exception if the string contains non alphanumeric characters
			if (Character.isLetter(c)) {
				state.onLetter();
			} else if (Character.isDigit(c)) {
				state.onDigit();
			} else {
				state.onOther();
			}
			charIndex++;
		}
		if (digitCount != 3) {
			throw new InvalidTransitionException("Course name must have 3 digits.");
		}

		validEndState = true;
		letterCount = 0;
		digitCount = 0;
		state = null;

		return validEndState;
	}

	/**
	 * Format for all of the states of the finite state machine
	 * 
	 * @author Troy Boone
	 */
	public abstract class State {
		/**
		 * Method called when the input character is a letter
		 * @throws InvalidTransitionException if during letter state there are more then 4 letters, if there is a letter before 3 digits during number state, or called more then once during suffix state
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		/**
		 * Method called when the input character is a digit
		 * @throws InvalidTransitionException if this method is called during initial state, suffix state, or during number state when there are more then 3 digits
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Method called when the input character is neither a letter or digit
		 * @throws InvalidTransitionException if this method is called
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}

	/**
	 * Initial state for the finite state machine
	 * 
	 * @author Troy Boone
	 */
	public class InitialState extends State {

		@Override
		public void onLetter() {
			letterCount++;
			state = new LetterState();
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");

		}

	}

	/**
	 * Second state for the finite state machine
	 * 
	 * @author Troy Boone
	 * 
	 */
	public class LetterState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < 4) {
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}

		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			state = new NumberState();
			digitCount++;
		}

	}

	/**
	 * Third state for the finite state machine
	 * 
	 * @author Troy Boone
	 */
	public class NumberState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == 3) {
				state = new SuffixState();
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}

		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount < 3) {
				digitCount++;
			} else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}

		}

	}

	/**
	 * State for the optional final suffix
	 * 
	 * @author Troy Boone
	 */
	public class SuffixState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");

		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

		}

	}

}
