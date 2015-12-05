package com.example.paypro.dbhandler;

import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.paypro.core.DBValues;
import com.example.paypro.data.Group;

public class GroupDAO extends AbstractDAO<Group> {

	/**
	 * The table for the recent stations list.
	 */
	public static final String DB_TABLE_GROUPS = "t_groups";

	@Override
	protected String getTableName() {
		return DB_TABLE_GROUPS;
	}

	private static GroupDAO instance = null;

	public static GroupDAO getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new GroupDAO();
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
	public synchronized void addUser(Group user) {
		save(Collections.singletonList(user));
	}

	/**
	 * Read the last added station.
	 *
	 * @return the last station that has been added to the list.
	 */
	public synchronized Group getLastStation() {
		List<Group> stations = loadWithQualification("ORDER BY Timestamp DESC LIMIT 4");
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
	public synchronized List<Group> getRecentStations() {
		return loadWithQualification("ORDER BY Timestamp DESC LIMIT 10");
	}

	@Override
	protected synchronized Group buildObject(Cursor cursor) {
		Group group = new Group(cursor.getLong(0), cursor.getString(1),
				cursor.getString(2), cursor.getLong(3));
		return group;
	}

	@Override
	protected synchronized void fillContent(ContentValues content, Group group) {
		content.put("id", group.getId());
		content.put(DBValues.Groups.name.name(), group.getName());
		content.put(DBValues.Groups.description.name(), group.getDescription());
		content.put(DBValues.Groups.group_admin_id.name(),
				group.getGroupAdminId());
	}
}
