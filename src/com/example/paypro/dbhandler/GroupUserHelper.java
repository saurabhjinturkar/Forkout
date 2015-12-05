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
import com.example.paypro.data.Group;
import com.example.paypro.data.User;

/**
 * @author jintu
 *
 */
public class GroupUserHelper extends SQLiteOpenHelper {

	private static final String TABLE_GROUP_USERS = "groups_users";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_GROUP_ID = DBValues.GroupMembers.group_id.name();
	private static final String COLUMN_USER_ID = DBValues.GroupMembers.user_id.name();
	private static final String COLUMN_CREATED_AT = DBValues.GroupMembers.created_at.name();
	private static final String COLUMN_UPDATED_AT = DBValues.GroupMembers.updated_at.name();
	private static final String DATABASE_NAME = "group_users.db";
	private static final int DATABASE_VERSION = 2;

	private static final String ID = "GroupUserHelper";

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GROUP_USERS + "(" + COLUMN_ID + " integer primary key , "
			+ COLUMN_GROUP_ID + " integer not null, " + COLUMN_USER_ID
			+ " integer not null);";

	private Context context;

	public GroupUserHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		Log.d(ID, "groups database created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(ID, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_USERS);
		onCreate(db);
	}

	public long createGroupUsers(Group group) {
		List<Long> members = group.getMembers();
		SQLiteDatabase database = this.getWritableDatabase();
		for (Long userId : members) {
			try {
				ContentValues values = new ContentValues();
				values.put(COLUMN_ID, group.getId());
				values.put(COLUMN_GROUP_ID, group.getId());
				values.put(COLUMN_USER_ID, userId);
				Log.d(ID, values.toString());

				long insertId = database
						.insert(TABLE_GROUP_USERS, null, values);
				Log.d(ID, group.getId() + "-->" + userId
						+ " added successfully");
				database.close();
				return insertId;
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(database!=null){
					database.close();
				}
			}
		}
		return -1;
	}

	public void dropTable() {
		SQLiteDatabase database = this.getWritableDatabase();
		database.execSQL("DELETE FROM  " + TABLE_GROUP_USERS);
		database.close();
	}

	public List<User> getAllMembers(long groupId) {
		SQLiteDatabase database =null;
		try {
			database = this.getWritableDatabase();
			// database.query(TABLE_COMMENTS, allColumns, COLUMN_NAME, null,
			// null,
			// null, COLUMN_NAME);
			Cursor cursor = database.rawQuery("select * from "
					+ TABLE_GROUP_USERS + " where " + COLUMN_GROUP_ID + " = "
					+ groupId + ";", null);

			List<User> users = new ArrayList<User>();
			UserHelper userHelper = new UserHelper(context);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				User user = userHelper.getUserById(cursor.getLong(2));
				users.add(user);
				cursor.moveToNext();
			}

			Log.d(ID, users.toString());
			cursor.close();
			database.close();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return null;
	}
	public void flush() {
		context.deleteDatabase(DATABASE_NAME);
	}
}
