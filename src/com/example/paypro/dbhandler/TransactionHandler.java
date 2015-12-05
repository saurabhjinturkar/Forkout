/**
 * 
 */
package com.example.paypro.dbhandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.paypro.core.DBValues;
import com.example.paypro.data.Transaction;
import com.example.paypro.manager.ApplicationUtil;

/**
 * @author Tarang
 * 
 */
public class TransactionHandler extends SQLiteOpenHelper {
	private static final String TABLE_TRANSACTIONS = "transactions";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_DESCRIPTION = DBValues.Transactions.description.name();
	private static final String COLUMN_DETAILS = DBValues.Transactions.details.name();
	private static final String COLUMN_AMOUNT = DBValues.Transactions.amount.name();
	private static final String COLUMN_USER_ID = DBValues.Transactions.user_id.name();
	private static final String COLUMN_GROUP_ID = DBValues.Transactions.group_id.name();
	private static final String COLUMN_GROUP_ID_MEMBER_ID = DBValues.Transactions.group_member_id.name();
	private static final String COLUMN_CREATED_ON = DBValues.Transactions.created_at.name();
	private static final String DATABASE_NAME = "transactions.db";
	private static final int DATABASE_VERSION = 7;
	
	private static final String ID = "TransactionHandler";
	
	private Context context;

	public TransactionHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query = "create table " + TABLE_TRANSACTIONS + "(" + COLUMN_ID
				+ " integer primary key , " + COLUMN_DESCRIPTION
				+ " text not null, " + COLUMN_DETAILS + " text not null, "
				+ COLUMN_AMOUNT + " real, " + COLUMN_GROUP_ID + " integer, "
				+ COLUMN_USER_ID + " integer, " + COLUMN_GROUP_ID_MEMBER_ID
				+ " integer, " + COLUMN_CREATED_ON+ " text);";
		Log.d(ID, query);
		database.execSQL(query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.d(ID, "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
		onCreate(database);
	}

	public List<Transaction> getTransactionsByGroupId(long id) {
		
		Log.d(ID, "Group id--> " + id);
		
		SQLiteDatabase database = getWritableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_TRANSACTIONS
				+ " where " + COLUMN_GROUP_ID + " = " + id, null);

		List<Transaction> transactions = new ArrayList<Transaction>();
		cursor.moveToFirst();
		Log.d(ID, cursor.getCount() +"");
		while (!cursor.isAfterLast()) {
			Date creationDate = null;
			try {
				creationDate = ApplicationUtil.parseDate(cursor.getString(7));
			} catch (ParseException e) {
				e.printStackTrace();
				Log.e(ID, e.getLocalizedMessage());
				return null;
			}
			Transaction transaction = new Transaction(cursor.getLong(0),
					cursor.getString(1), cursor.getString(2),
					cursor.getDouble(3), cursor.getLong(5), cursor.getLong(4),
					cursor.getLong(6));
			transaction.setCreatedAt(creationDate);
			transactions.add(transaction);
			cursor.moveToNext();
			Log.d("TRANSACTION", transaction.display());
		}
		cursor.close();
		database.close();
		return transactions;
	}
	
	public Transaction getTransactionById(long id) {
		Log.d(ID, "Group id--> " + id);

		SQLiteDatabase database = getWritableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_TRANSACTIONS
				+ " where " + COLUMN_ID + " = " + id, null);

		cursor.moveToFirst();

		if (cursor.getCount() > 1) {
			Log.e(ID, "Count is greater than one for id " + id);
			return null;
		}
		Log.d(ID, cursor.getCount() + "");
		Date creationDate = null;
		try {
			creationDate = ApplicationUtil.parseDate(cursor.getString(7));
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e(ID, e.getLocalizedMessage());
			return null;
		}
		Transaction transaction = new Transaction(cursor.getLong(0),
				cursor.getString(1), cursor.getString(2), cursor.getDouble(3),
				cursor.getLong(4), cursor.getLong(5), cursor.getLong(6));
		transaction.setCreatedAt(creationDate);
		cursor.close();
		database.close();
		return transaction;
	}

	public long createTransaction(Transaction transaction) {
		SQLiteDatabase database =null;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, transaction.getId());
			values.put(COLUMN_DESCRIPTION, transaction.getDescription());
			values.put(COLUMN_DETAILS, transaction.getDetails());
			values.put(COLUMN_AMOUNT, transaction.getAmount());
			values.put(COLUMN_GROUP_ID, transaction.getGroupId());
			values.put(COLUMN_USER_ID, transaction.getUserId());
			values.put(COLUMN_GROUP_ID_MEMBER_ID,
				transaction.getGroupMemberId());
			values.put(COLUMN_CREATED_ON,
				ApplicationUtil.dateToString(transaction.getCreatedAt()));
			Log.d("Transaction values", values.toString());

			database = getWritableDatabase();

			long insertId = database.insert(TABLE_TRANSACTIONS, null, values);
			Log.d("TRANSACTION SUCCESS", "Inserted id " + insertId);
			database.close();
			return insertId;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ID, e.getLocalizedMessage());
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return -1;
	}
	
	public long getMaxTransactionId() {
		SQLiteDatabase database = getWritableDatabase();
		Cursor cursor = database.rawQuery("select max(" + COLUMN_ID + ") FROM "
			+ TABLE_TRANSACTIONS + ";", null);
		long id = 0;
		Log.d("QUERY", "select max(" + COLUMN_ID + ") FROM "
			+ TABLE_TRANSACTIONS + ";");
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			id = cursor.getLong(0);
			Log.d(ID, "ID  " + id);
		}
		cursor.close();
		database.close();
		return id;
	}

	public void dropTable() {
		SQLiteDatabase database = this.getWritableDatabase();
		database.execSQL("DELETE FROM  " + TABLE_TRANSACTIONS);
		database.close();
	}
	public void flush() {
		context.deleteDatabase(DATABASE_NAME);
	}

	public List<Transaction> getTransactionForUsers(long userId1, long userId2) {
		return null;
	}
}
