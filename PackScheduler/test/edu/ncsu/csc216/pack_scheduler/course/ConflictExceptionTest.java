/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the ConflictException class.
 * @author Troy Boone
 *
 */
public class ConflictExceptionTest {


	/**
	 * Testing ConflictException(string) method with custom exception message
	 * Test method for {@link edu.ncsu.csc216.wolf_scheduler.course.ConflictException#ConflictException(java.lang.String)}.
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}


	/**
	 * Testing ConflictException() method with default exception message
	 * Test method for {@link edu.ncsu.csc216.wolf_scheduler.course.ConflictException#ConflictException()}.
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
	    assertEquals("Schedule conflict.", ce.getMessage());
	}

}
