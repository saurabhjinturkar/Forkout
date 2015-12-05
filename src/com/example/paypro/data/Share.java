/**
 * 
 */
package com.example.paypro.data;

/**
 * @author jintu
 * 
 */
public class Share {

	private long id;
	private double percentShare;
	private double amountShare;
	private long userId;
	private long groupId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the percentShare
	 */
	public double getPercentShare() {
		return percentShare;
	}

	/**
	 * @param percentShare
	 *            the percentShare to set
	 */
	public void setPercentShare(double percentShare) {
		this.percentShare = percentShare;
	}

	/**
	 * @return the amountShare
	 */
	public double getAmountShare() {
		return amountShare;
	}

	/**
	 * @param amountShare
	 *            the amountShare to set
	 */
	public void setAmountShare(double amountShare) {
		this.amountShare = amountShare;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
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
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Share other = (Share) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Share [id=" + id + ", percentShare=" + percentShare
				+ ", amountShare=" + amountShare + ", userId=" + userId
				+ ", groupId=" + groupId + "]";
	}

	
}
