package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the InvalidTransitionException throw
 * @author Troy Boone
 *
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Test InvalidTransitionException with custom message
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException test = new InvalidTransitionException("Custom exception message");
		 assertEquals("Custom exception message", test.getMessage());
	}

	/**
	 * Test InvalidTransitionException with default message
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException test = new InvalidTransitionException();
		 assertEquals("Invalid FSM Transition.", test.getMessage());
	}

}
