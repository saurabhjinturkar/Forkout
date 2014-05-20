/**
 * 
 */
package com.example.paypro.dbhandler;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.paypro.core.DBValues;
import com.example.paypro.data.User;

/**
 * @author Tarang
 * 
 */
public class UserHelper extends SQLiteOpenHelper {

	private static final String TABLE_USERS = "users";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_FIRST_NAME = DBValues.Users.first_name.name();
	private static final String COLUMN_LAST_NAME = DBValues.Users.last_name.name();
	private static final String COLUMN_EMAIL = DBValues.Users.email.name();
	private static final String COLUMN_CONTACTNO = DBValues.Users.contact_number.name();
	private static final String COLUMN_REGISTRATION_STATUS = DBValues.Users.registration_status.name();
	private static final String COLUMN_CREATED_AT = DBValues.Users.created_at.name();
	private static final String COLUMN_UPDATED_AT = DBValues.Users.updated_at.name();
	private static final String DATABASE_NAME = "users.db";
	private static final int DATABASE_VERSION = 7;

	private static final String ID = "UserHelper";
	
	private Context context;
	
	public UserHelper(Context context) {
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
		String query = "create table " + TABLE_USERS + "(" + COLUMN_ID
			+ " integer primary key , " + COLUMN_FIRST_NAME
			+ " text not null, " + COLUMN_LAST_NAME + " text not null, "
			+ COLUMN_EMAIL + " text not null, " + COLUMN_CONTACTNO
			+ " text not null, " + COLUMN_REGISTRATION_STATUS
			+ " text, " + COLUMN_CREATED_AT + " text, "
			+ COLUMN_UPDATED_AT + " text);";
		Log.w("User table create query", query);
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
		Log.w("Upgrading user table", "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(database);
	}

	public User getUserById(long id) {
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery("select * from " + TABLE_USERS
				+ " where " + COLUMN_ID + " = " + id, null);
		if (cursor.getCount() != 1) {
			Log.d("More user records found", cursor.getCount()
					+ " user records found for id " + id);
			System.out.println("All Users: " + getAllUsers());
			return null;
		}

		cursor.moveToFirst();
		User user = new User(cursor.getLong(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5));

		cursor.close();
		Log.d(ID, user.toString());
		database.close();
		return user;
	}

	public List<User> getAllFriends(long id) {
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(
			"select * from " + TABLE_USERS + " ;", null);

		List<User> users = new ArrayList<User>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if (cursor.getLong(0) != id) {
				User user = new User(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5));
				users.add(user);
			}
			cursor.moveToNext();
		}
		cursor.close();
		Log.d(ID, users.toString());
		database.close();
		return users;
	}
	
	public List<User> getAllUsers() {

		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(
				"select * from " + TABLE_USERS + " ;", null);

		List<User> users = new ArrayList<User>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = new User(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5));
			users.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		Log.d(ID, users.toString());
		database.close();
		return users;
	
	}

	public long createUser(User user) {
		SQLiteDatabase database = null;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, user.getId());
			values.put(COLUMN_FIRST_NAME, user.getFirstName());
			values.put(COLUMN_LAST_NAME, user.getLastName());
			values.put(COLUMN_EMAIL, user.getEmail());
			values.put(COLUMN_CONTACTNO, user.getContactNumber());
			values.put(COLUMN_REGISTRATION_STATUS, user.getRegistrationStatus());
			
			Log.d(ID, values.toString());
			Log.d(ID, user.toString());
			database = getWritableDatabase();

			long insertId = database.insert(TABLE_USERS, null, values);
			database.close();
			return insertId;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return -1;
	}

	public void dropTable() {
		SQLiteDatabase database = this.getWritableDatabase();
		database.execSQL("DELETE FROM  " + TABLE_USERS);
		database.close();
	}
	public void flush() {
		context.deleteDatabase(DATABASE_NAME);
	}
}