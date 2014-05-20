/**
 * 
 */
package com.example.paypro.data;

/**
 * @author jintu
 * 
 */
public class ReportData {
	private long userId;
	private String userName;
	private double amountPaid;
	private double amountDues;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getAmountDues() {
		return amountDues;
	}

	public void setAmountDues(double amountDues) {
		this.amountDues = amountDues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountDues);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(amountPaid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result
			+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportData other = (ReportData) obj;
		if (Double.doubleToLongBits(amountDues) != Double
			.doubleToLongBits(other.amountDues))
			return false;
		if (Double.doubleToLongBits(amountPaid) != Double
			.doubleToLongBits(other.amountPaid))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportData [userId=" + userId + ", userName=" + userName
			+ ", amountPaid=" + amountPaid + ", amountDues=" + amountDues + "]";
	}

}
