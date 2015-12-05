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
import com.example.paypro.data.QueueObject;
import com.example.paypro.manager.JSONHandler;

/**
 * @author jintu
 * 
 */
public class QueueHelper extends SQLiteOpenHelper {

	private static final String TABLE_QUEUE = "queue";
	private static final String COLUMN_ID = DBValues.Queue.id.name();
	private static final String COLUMN_TYPE = DBValues.Queue.type.name();
	private static final String COLUMN_OBJECT = DBValues.Queue.object.name();
	private static final String COLUMN_STATUS = DBValues.Queue.status.name();
	private static final String DATABASE_NAME = "queue.db";
	private static final int DATABASE_VERSION = 1;

	private static final String ID = "QueueHelper";

	private static final String DATABASE_CREATE = "create table " + TABLE_QUEUE
			+ "(" + COLUMN_ID + " integer primary key , " + COLUMN_TYPE
			+ " text not null, " + COLUMN_OBJECT + " text not null, "
			+ COLUMN_STATUS + " integer);";

	private Context context;

	public QueueHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		Log.d(ID, "groups database created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(ID, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEUE);
		onCreate(db);
	}
	
	public void addToQueue(QueueObject object) {

		SQLiteDatabase database =null;
		try {
			ContentValues values = new ContentValues();
			values.put(COLUMN_ID, object.getId());
			values.put(COLUMN_TYPE, object.getType().getSimpleName());
			values.put(COLUMN_OBJECT, JSONHandler.getInstance().converToJSON(object.getObject()));
			values.put(COLUMN_STATUS, object.getStatus());
			Log.d("Transaction values", values.toString());

			database = getWritableDatabase();

			long insertId = database.insert(TABLE_QUEUE, null, values);
			Log.d("TRANSACTION SUCCESS", "Inserted id " + insertId);
			database.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ID, e.getLocalizedMessage());
		}finally{
			if(database!=null){
				database.close();
			}
		}
	}
	
	public QueueObject getTopQueue() {
		
		return null;
	}
	
	public List<QueueObject> getQueue() {

		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(
			"select * from " + TABLE_QUEUE + " ;", null);

		List<QueueObject> queue = new ArrayList<QueueObject>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			QueueObject object = new QueueObject();
			object.setId(cursor.getLong(0));
			try {
				object.setType(Class.forName(cursor.getString(1)));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				object.setObject(JSONHandler.getInstance().convertToJava(
						cursor.getString(2), Class.forName(cursor.getString(1))));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			object.setStatus(cursor.getInt(3));
			queue.add(object);
			cursor.moveToNext();
		}
		cursor.close();
		Log.d(ID, queue.toString());
		database.close();
		return queue;
	}
	
	public void removeQueue(long id) {

		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(
			"delete * from " + TABLE_QUEUE + " where " + COLUMN_ID + " = " + id + ";", null);
		cursor.close();
		database.close();
	}
	

}
