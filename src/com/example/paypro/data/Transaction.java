/**
 * 
 */
package com.example.paypro.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Saurabh
 * 
 */
public class Transaction implements Serializable{

	private long id;
	private String description;
	private String details;
	private double amount;
	private long userId;
	private long groupId;
	private long groupMemberId;
	private Date createdAt;
	private Date updatedAt;
	
	private List<Share> shares;
	
	private static final long serialVersionUID = 1L;
	
	public Transaction() {
		shares = new ArrayList<Share>();
	}

	/**
	 * @param id
	 * @param description
	 * @param details
	 * @param amount
	 * @param userId
	 * @param groupId
	 * @param groupMemberId
	 */
	public Transaction(long id, String description, String details,
			double amount, long userId, long groupId, long groupMemberId) {
		super();
		this.id = id;
		this.description = description;
		this.details = details;
		this.amount = amount;
		this.userId = userId;
		this.groupId = groupId;
		this.groupMemberId = groupMemberId;
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
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
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
	 * @return the groupMemberId
	 */
	public long getGroupMemberId() {
		return groupMemberId;
	}
	/**
	 * @param groupMemberId the groupMemberId to set
	 */
	public void setGroupMemberId(long groupMemberId) {
		this.groupMemberId = groupMemberId;
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
	
	public List<Share> getShares() {
		return shares;
	}
	
	public void setShares(List<Share> shares) {
		this.shares = shares;
	}
	 
	public void addShare(Share share) {
		this.shares.add(share);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		result = prime * result
				+ (int) (groupMemberId ^ (groupMemberId >>> 32));
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
		Transaction other = (Transaction) obj;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		if (groupId != other.groupId)
			return false;
		if (groupMemberId != other.groupMemberId)
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
		return "Transaction [id=" + id + ", description=" + description
				+ ", details=" + details + ", amount=" + amount + ", userId="
				+ userId + ", groupId=" + groupId + ", groupMemberId="
				+ groupMemberId + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	public String display() {
		return this.amount + "/- " + this.description ;
	}
}
