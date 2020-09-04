package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * A user object that contains the first name, last name, id, email address,
 *  and password for a user. It is a superclas with subclasses being student and registar
 * @author Troy Boone
 *
 */
public abstract class User {

	/** The students first name */
	private String firstName;
	/** The students last name */
	private String lastName;
	/** The students id */
	private String id;
	/** The students email */
	private String email;
	/** The students password hashed */
	private String password;

	
	/**
	 * Constructs a User object with values for all fields
	 * 
	 * @param firstName  the User first name
	 * @param lastName   the User last name
	 * @param id         the User id
	 * @param email      the User email
	 * @param password   the User password hashed
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Simple getter for firstName
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstname with a non-null, non-empty string
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if firstName is null
	 * @throws IllegalArgumentException if firstName is empty string
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Simple getter for lastName
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName with a non-null, non-empty string
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if lastName is null
	 * @throws IllegalArgumentException if lastName is empty string
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.equals("")) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Simple getter for id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id with a non-null, non-empty string
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException if id is null
	 * @throws IllegalArgumentException if id is empty string
	 */
	protected void setId(String id) {
		if (id == null || id.equals("")) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Simple getter for email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email with a non-null, non-empty string that contains both a '@' and
	 * '.' character, with the '@' coming before the '.'
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException if email doesn’t contain an ‘@’ character
	 * @throws IllegalArgumentException if email doesn’t contain a ‘.’ character
	 * @throws IllegalArgumentException if the '.' character is before the '@'
	 *                                  character
	 * @throws IllegalArgumentException if email is null
	 * @throws IllegalArgumentException if email is empty string
	 */
	public void setEmail(String email) {
		if (email == null || email.equals("") || email.indexOf('@') == -1 || email.indexOf('.') == -1
				|| email.lastIndexOf('.') < email.indexOf('@')) {
			throw new IllegalArgumentException("Invalid email");
		}
	
		this.email = email;
	}

	/**
	 * Simple getter for hashPW
	 * 
	 * @return the hashPW
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the hashPW with a non-null, non-empty string
	 * 
	 * @param hashPW the hashPW to set
	 * @throws IllegalArgumentException if hashPW is null
	 * @throws IllegalArgumentException if hashPW is empty string
	 */
	public void setPassword(String hashPW) {
		if (hashPW == null || hashPW.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = hashPW;
	}
	/**
	 * Generates a hash code that can be used to identify the object
	 * 
	 * @return Returns a hash code value for the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}
	/**
	 * Compares if this student object is equal to this student object
	 * 
	 * @param obj the student being compared to
	 * @return true if this student is the same as student obj, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}

}