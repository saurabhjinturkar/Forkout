/**
 *
 */
package com.example.paypro.dbhandler;

import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.paypro.core.DBValues;
import com.example.paypro.data.Transaction;
import com.example.paypro.manager.ApplicationUtil;

/**
 * @author Saurabh
 *
 */
public class TransactionDAO extends AbstractDAO<Transaction> {

	/**
	 * The table for the recent stations list.
	 */
	public static final String DB_TABLE_TRANSACTIONS = "t_transactions";

	@Override
	protected String getTableName() {
		return DB_TABLE_TRANSACTIONS;
	}

	private static TransactionDAO instance = null;

	public static TransactionDAO getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new TransactionDAO();
			return instance;
		}
	}

	/**
	 * Append a new station to the list
	 *
	 * @param url
	 *            the station's Last.fm URL
	 * @param name
	 *            the station's display name
	 */
	public synchronized void addUser(Transaction user) {
		save(Collections.singletonList(user));
	}

	/**
	 * Read the last added station.
	 *
	 * @return the last station that has been added to the list.
	 */
	public synchronized Transaction getLastStation() {
		List<Transaction> stations = loadWithQualification("ORDER BY Timestamp DESC LIMIT 4");
		if (stations != null && stations.size() > 0) {
			return stations.get(0);
		}
		return null;
	}

	/**
	 * Get the list of recent stations.
	 *
	 * @return all stations in the table.
	 */
	public synchronized List<Transaction> getRecentStations() {
		return loadWithQualification("ORDER BY Timestamp DESC LIMIT 10");
	}

	@Override
	protected synchronized Transaction buildObject(Cursor cursor) {
		Transaction transaction = new Transaction(cursor.getLong(0),
				cursor.getString(1), cursor.getString(2), cursor.getDouble(3),
				cursor.getLong(4), cursor.getLong(5), cursor.getLong(6));
		return transaction;
	}

	@Override
	protected synchronized void fillContent(ContentValues content, Transaction transaction) {
		content.put("id", transaction.getId());
		content.put(DBValues.Transactions.description.name(), transaction.getDescription());
		content.put(DBValues.Transactions.details.name(), transaction.getDetails());
		content.put(DBValues.Transactions.amount.name(), transaction.getAmount());
		content.put(DBValues.Transactions.group_id.name(), transaction.getGroupId());
		content.put(DBValues.Transactions.user_id.name(), transaction.getUserId());
		content.put(DBValues.Transactions.group_member_id.name(),
			transaction.getGroupMemberId());
		content.put(DBValues.Transactions.created_at.name(),
			ApplicationUtil.dateToString(transaction.getCreatedAt()));
	}
}
