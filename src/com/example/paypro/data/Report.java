/**
 * 
 */
package com.example.paypro.data;

import java.util.List;

/**
 * @author jintu
 *
 */
public class Report {
	private long groupId;
	private double totalExpenses;
	private double expensePerHead;
	private List<ReportData> details;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public double getTotalExpenses() {
		return totalExpenses;
	}
	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
	public double getExpensePerHead() {
		return expensePerHead;
	}
	public void setExpensePerHead(double expensePerHead) {
		this.expensePerHead = expensePerHead;
	}
	public List<ReportData> getDetails() {
		return details;
	}
	public void setDetails(List<ReportData> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "Report [groupId=" + groupId + ", totalExpenses="
			+ totalExpenses + ", expensePerHead=" + expensePerHead
			+ ", details=" + details + "]";
	}
	
}

