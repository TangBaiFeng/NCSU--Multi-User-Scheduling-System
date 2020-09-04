package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test the CourseNameValidator finite state machine with
 * 
 * @author Troy Boone
 *
 */
public class CourseNameValidatorTest {

	/**
	 * Test the FSM initial state
	 */
	@Test
	public void teststateInitial() {
		// Valid
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("CSC151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// invalid
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("@");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		try {
			CourseNameValidator test = new CourseNameValidator();
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
			CourseNameValidator test = new CourseNameValidator();
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
			CourseNameValidator test = new CourseNameValidator();
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
			CourseNameValidator test = new CourseNameValidator();
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
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("CSCc151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// Invalid
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("CSCSC151");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
	}

	/**
	 * Test the FSM state with 3 number start
	 */
	@Test
	public void teststateD() {
		// Valid
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("CSCc151");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// Invalid
		try {
			CourseNameValidator test = new CourseNameValidator();
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
			CourseNameValidator test = new CourseNameValidator();
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
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("c151c");
		} catch (InvalidTransitionException e) {
			fail("A InvalidTransitionException was thrown");
		}
		// Invalid
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("CSC1167");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
	}

	/**
	 * Test valid suffix transition
	 */
	@Test
	public void teststateSuffix() {
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("c151cc");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
		try {
			CourseNameValidator test = new CourseNameValidator();
			test.isValid("c151c1");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}

	/**
	 * Testing every single valid coursename format
	 */
	@Test
	public void testMassTest() {
		CourseNameValidator test = new CourseNameValidator();
		try {
			test.isValid("CSC151");
			assertTrue(test.isValid("C151"));
			assertTrue(test.isValid("CS151"));
			assertTrue(test.isValid("CSC151"));
			assertTrue(test.isValid("CSCS151"));
			assertTrue(test.isValid("C151C"));
			assertTrue(test.isValid("CS151C"));
			assertTrue(test.isValid("CSC151C"));
			assertTrue(test.isValid("CSCS151C"));

		} catch (InvalidTransitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
