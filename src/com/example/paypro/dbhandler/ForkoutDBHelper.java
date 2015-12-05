/**
 *
 */
package com.example.paypro.dbhandler;

import com.example.paypro.core.DBValues;
import com.example.paypro.core.ForkoutApplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.paypro.core.DBValues.*;

/**
 * @author Saurabh
 *
 */
public class ForkoutDBHelper extends SQLiteOpenHelper {
	/**
	 * The name of the Last.fm database.
	 */
	public static final String DB_NAME = "forkout_db";

	/**
	 * The DB's version number. This needs to be increased on schema changes.
	 */
	public static final int DB_VERSION = 1;

	/**
	 * Singleton instance of {@link ScrobblerQueueDao}.
	 */
	private static ForkoutDBHelper instance = null;

	/**
	 * @return the {@link ScrobblerQueueDao} singleton.
	 */
	public static ForkoutDBHelper getInstance() {
		if (instance != null) {
			return instance;
		} else {
			return new ForkoutDBHelper();
		}
	}

	public void clearDatabase() {
		UserDAO.getInstance().clearTable();
		GroupDAO.getInstance().clearTable();
		TransactionDAO.getInstance().clearTable();
		GroupUserDAO.getInstance().clearTable();
	}

	private ForkoutDBHelper() {
		super(ForkoutApplication.getInstance().getApplicationContext(),
				DB_NAME, null, DB_VERSION);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// create the table for the recent stations
		db.execSQL("CREATE TABLE IF NOT EXISTS " + UserDAO.DB_TABLE_USERS
				+ "(id integer primary key , " + Users.first_name.name()
				+ " text not null, " + Users.last_name.name()
				+ " text not null, " + Users.email.name() + " text not null, "
				+ Users.contact_number.name() + " text not null, "
				+ Users.registration_status.name() + " text, "
				+ Users.created_at.name() + " text, " + Users.updated_at.name()
				+ " text);");

		// create table for scrobbling queue
		// the start time is used as PK because there can be only one track at a
		// time
		db.execSQL("CREATE TABLE IF NOT EXISTS " + GroupDAO.DB_TABLE_GROUPS
				+ "(id " + "integer primary key , "
				+ DBValues.Groups.name.name() + " text not null, "
				+ DBValues.Groups.description.name() + " text not null, "
				+ DBValues.Groups.group_admin_id.name() + " integer not null);");

		// create the table for caching track durations
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TransactionDAO.DB_TABLE_TRANSACTIONS
				+"(id"
				+ " integer primary key , " + DBValues.Transactions.description.name()
				+ " text not null, " + DBValues.Transactions.details.name() + " text not null, "
				+ DBValues.Transactions.amount.name() + " real, " + DBValues.Transactions.group_id.name() + " integer, "
				+ DBValues.Transactions.user_id.name() + " integer, " + DBValues.Transactions.group_member_id.name()
				+ " integer, " + DBValues.Transactions.created_at.name()+ " text);");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// for now we just drop everything and create it again
		db.execSQL("DROP TABLE IF EXISTS " + UserDAO.DB_TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS "
				+ GroupDAO.DB_TABLE_GROUPS);
		db.execSQL("DROP TABLE IF EXISTS "
				+ TransactionDAO.DB_TABLE_TRANSACTIONS);
		db.execSQL("DROP TABLE IF EXISTS "
				+ GroupUserDAO.DB_TABLE_GROUPS);
		onCreate(db);
	}
}
