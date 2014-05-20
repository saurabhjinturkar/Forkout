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

public class GroupsHelper extends SQLiteOpenHelper {

	private static final String TABLE_GROUPS = "groups";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = DBValues.Groups.name.name();
	private static final String COLUMN_DESCRIPTION = DBValues.Groups.description
		.name();
	private static final String COLUMN_ADMIN = DBValues.Groups.group_admin_id
		.name();
	private static final String DATABASE_NAME = "groups.db";
	private static final int DATABASE_VERSION = 2;

	private Context context;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
		+ TABLE_GROUPS + "(" + COLUMN_ID + " integer primary key , "
		+ COLUMN_NAME + " text not null, " + COLUMN_DESCRIPTION
		+ " text not null, " + COLUMN_ADMIN + " integer not null);";

	public GroupsHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		Log.d("groups database created", "groups database created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(GroupsHelper.class.getName(), "Upgrading database from version "
			+ oldVersion + " to " + newVersion
			+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
		onCreate(db);
	}

	public long createGroup(Group group) {
		SQLiteDatabase database = null;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, group.getId());
			values.put(COLUMN_NAME, group.getName());
			values.put(COLUMN_DESCRIPTION, group.getDescription());
			values.put(COLUMN_ADMIN, group.getGroupAdminId());
			Log.d("values", values.toString());
			database = this.getWritableDatabase();

			long insertId = database.insert(TABLE_GROUPS, null, values);
			Log.w("group added", group.getId() + " group added successfully");
			database.close();
			return insertId;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(database != null){
				database.close();
			}
		}
		return -1;
	}

	public void dropTable() {
		SQLiteDatabase database = this.getWritableDatabase();
		database.execSQL("DELETE FROM  " + TABLE_GROUPS);
		database.close();
	}

	public void flush() {
		context.deleteDatabase(DATABASE_NAME);
	}

	public List<Group> getallgroups() {
		SQLiteDatabase database = null;
		try {
			 database = this.getWritableDatabase();
			// database.query(TABLE_COMMENTS, allColumns, COLUMN_NAME, null,
			// null,
			// null, COLUMN_NAME);
			Cursor cursor = database.rawQuery("select * from " + TABLE_GROUPS,
				null);

			List<Group> groups = new ArrayList<Group>();

			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				Group group = new Group(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getLong(3));
				groups.add(group);
				cursor.moveToNext();
			}
			cursor.close();
			System.out.println("Groups: " + groups);
			database.close();
			return groups;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(database!=null){
				database.close();
			}
		}
		return null;
		}
	public Group getGroupById(long id) {

		try {
			SQLiteDatabase database = this.getWritableDatabase();
			// database.query(TABLE_COMMENTS, allColumns, COLUMN_NAME, null,
			// null,
			// null, COLUMN_NAME);
			Cursor cursor = database.rawQuery("select * from " + TABLE_GROUPS
				+ " where " + COLUMN_ID + " = " + id + ";", null);
			Group group = null;;
			if(cursor.getCount() == 1) {
		
			cursor.moveToFirst();
				group = new Group(cursor.getLong(0), cursor.getString(1),
					cursor.getString(2), cursor.getLong(3));
		}
			cursor.close();
			System.out.println("Groups: " + group);
			return group;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
