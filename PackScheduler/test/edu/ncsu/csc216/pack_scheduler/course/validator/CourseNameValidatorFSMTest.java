package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the CourseNameValidator finite state machine
 * @author Troy Boone
 *
 */
public class CourseNameValidatorFSMTest {

	

	/**
	 * Test the FSM initial state
	 */
	@Test
	public void teststateInitial() {
		// Valid
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("CSC151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// invalid
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("@");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("1");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
	}
	/**
	 * Test the FSM state with 1 letter start
	 */
	@Test
	public void teststateL() {
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("C151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
	}
	/**
	 * Test the FSM state with 2 letter start
	 */
	@Test
	public void teststateLL() {
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("Cc151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}

	}
	/**
	 * Test the FSM state with 3 letter start
	 */
	@Test
	public void teststateLLL() {
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("CSC151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
	}
	/**
	 * Test the FSM state with 4 letter start
	 */
	@Test
	public void teststateLLLL() {
		// Valid
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("CSCc151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// Invalid
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("CSCSC151");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
	}
	/**
	 * Test the FSM state with 1 number start
	 */
	@Test
	public void teststateD() {
		// Valid
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("CSCc151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// Invalid
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("c1c");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}
	/**
	 * Test the FSM state with 2 number start
	 */
	@Test
	public void teststateDD() {
		// Invalid
				try {
					CourseNameValidatorFSM test = new CourseNameValidatorFSM();
					test.isValid("c11c");
				} catch (InvalidTransitionException e) {
					assertEquals("Course name must have 3 digits.", e.getMessage());
				}
			
	}
	/**
	 * Test the FSM state with 3 number start
	 */
	@Test
	public void teststateDDD() {
		// Valid
				try {
					CourseNameValidatorFSM test = new CourseNameValidatorFSM();
					test.isValid("c151c");
				} catch (InvalidTransitionException e) {
					fail("A InvalidTransitionException was thrown");
				}
				// Invalid
				try {
					CourseNameValidatorFSM test = new CourseNameValidatorFSM();
					test.isValid("c1511");
				} catch (InvalidTransitionException e) {
					assertEquals("Course name can only have 3 digits.", e.getMessage());
				}
	}
	/**
	 * Test the FSM state with a suffix
	 */
	@Test
	public void teststateSuffix() {
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("c151cc");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
		try {
			CourseNameValidatorFSM test = new CourseNameValidatorFSM();
			test.isValid("c151c1");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}

}
