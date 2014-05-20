/**
 * 
 */
package com.example.paypro.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tarang
 * 
 */
public class Group implements Serializable{
	private long id;
	private String name;
	private String description;
	private long groupAdminId;
	private List<Long> members;
	private Date createdAt;
	private Date updatedAt;
	
	private static final long serialVersionUID = 1L;
	
	public Group() {
		this.members = new ArrayList<Long>();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param groupAdminId
	 */
	public Group(long id, String name, String description, long groupAdminId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.groupAdminId = groupAdminId;
		this.members = new ArrayList<Long>();
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the groupAdminId
	 */
	public long getGroupAdminId() {
		return groupAdminId;
	}
	/**
	 * @param groupAdminId the groupAdminId to set
	 */
	public void setGroupAdminId(long groupAdminId) {
		this.groupAdminId = groupAdminId;
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
	
	public List<Long> getMembers() {
		return members;
	}
	
	public void setMembers(List<Long> members) {
		this.members = members;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (groupAdminId ^ (groupAdminId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Group other = (Group) obj;
		if (groupAdminId != other.groupAdminId)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	public String print() {
		return "Group [id=" + id + ", name=" + name + ", description="
				+ description + ", groupAdminId=" + groupAdminId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ " Members " + members + " ]";
	}
}
