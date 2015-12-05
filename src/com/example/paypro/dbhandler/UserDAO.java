/**
 *
 */
package com.example.paypro.dbhandler;

import static com.example.paypro.core.DBValues.Users.contact_number;
import static com.example.paypro.core.DBValues.Users.email;
import static com.example.paypro.core.DBValues.Users.first_name;
import static com.example.paypro.core.DBValues.Users.last_name;
import static com.example.paypro.core.DBValues.Users.registration_status;

import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.paypro.data.User;
/**
 * @author Saurabh
 *
 */
public class UserDAO extends AbstractDAO<User> {

	/**
	 * The table for the recent stations list.
	 */
	public static final String DB_TABLE_USERS = "t_users";

	@Override
	protected String getTableName() {
		return DB_TABLE_USERS;
	}

	private static UserDAO instance = null;

	public static UserDAO getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new UserDAO();
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
	public synchronized void addUser(User user) {
		save(Collections.singletonList(user));
	}

	/**
	 * Read the last added station.
	 *
	 * @return the last station that has been added to the list.
	 */
	public synchronized User getLastStation() {
		List<User> stations = loadWithQualification("ORDER BY Timestamp DESC LIMIT 4");
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
	public synchronized List<User> getRecentStations() {
		return loadWithQualification("ORDER BY Timestamp DESC LIMIT 10");
	}

	@Override
	protected synchronized User buildObject(Cursor c) {
		User user = new User(c.getLong(0), c.getString(1),
				c.getString(2), c.getString(3), c.getString(4),
				c.getString(5));
		return user;
	}

	@Override
	protected synchronized void fillContent(ContentValues content, User data) {
		content.put("id", data.getId());
		content.put(first_name.name(), data.getFirstName());
		content.put(last_name.name(), data.getLastName());
		content.put(email.name(), data.getEmail());
		content.put(contact_number.name(), data.getContactNumber());
		content.put(registration_status.name(), data.getRegistrationStatus());
	}
}
