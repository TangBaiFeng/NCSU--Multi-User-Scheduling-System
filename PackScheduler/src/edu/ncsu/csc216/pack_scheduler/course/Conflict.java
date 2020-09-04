/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface for conflict handling for the Activity Object
 * 
 * @author Troy Boone
 *
 */
public interface Conflict {

	/**
	 * Interface method for handling conflicts between activities.
	 * 
	 * @param possibleConflictingActivity The activity being compared to 'this'
	 *                                    object
	 * @throws ConflictException if a conflict in times is found
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
