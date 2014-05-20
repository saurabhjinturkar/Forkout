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
public class GroupMember implements Serializable{

	private long id;
	private long userId;
	private long groupId;
	private Date createdAt;
	private Date updatedAt;

	private static final long serialVersionUID = 1L;

	public GroupMember() {

	}
	
	/**
	 * @param id
	 * @param userId
	 * @param groupId
	 */
	public GroupMember(long id, long userId, long groupId) {
		super();
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the groupId
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		GroupMember other = (GroupMember) obj;
		if (groupId != other.groupId)
			return false;
		if (id != other.id)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupMember [id=" + id + ", userId=" + userId + ", groupId="
				+ groupId + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
}
