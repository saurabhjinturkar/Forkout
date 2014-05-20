/**
 * 
 */
package com.example.paypro.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Saurabh
 *
 */
public class User implements Serializable{
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String contactNumber;
	private String registrationStatus;
	private Date createdAt;
	private Date updatedAt;
	
	private static final long serialVersionUID = 1L;
	
	public User() {
	}
	
	/**
	 * Copy constructor
	 * @param user 
	 */
	public User(User user) {
		super();
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.email = user.email;
		this.contactNumber = user.contactNumber;
		this.registrationStatus = user.registrationStatus;
	}
	
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param contactNumber
	 * @param registrationStatus
	 */
	public User(long id, String firstName, String lastName, String email,
			String contactNumber, String registrationStatus) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.registrationStatus = registrationStatus;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", contactNumber="
				+ contactNumber + ", registrationStatus=" + registrationStatus
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	public String getDisplayName() {
		return firstName + " " + lastName;
	}
}
