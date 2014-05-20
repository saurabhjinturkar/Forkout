/**
 * 
 */
package com.example.paypro.dataprovider;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.paypro.R;
import com.example.paypro.data.User;

/**
 * @author jintu
 * 
 */
public class GroupMemberAdapter extends ArrayAdapter<User> {

	private List<User> users;
	private Context context;

	public GroupMemberAdapter(Context context, List<User> users) {
		super(context, R.layout.listitem_username);
		this.users = users;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (users.size() == 0) {
			Log.w("MEMBERS", "Empty users list");
			return null;
		}
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.listitem_username, null);

		TextView text = (TextView) view.findViewById(R.id.userName_textView);
		text.setText(users.get(position).getFirstName() + " "
			+ users.get(position).getLastName());

		return view;
	}

	@Override
	public void add(User object) {
		super.add(object);
		users.add(object);
	}

	@Override
	public void clear() {
		super.clear();
		users.clear();
	}

	@Override
	public void addAll(Collection<? extends User> collection) {
		super.addAll(collection);
		users.addAll(collection);
	}

	@Override
	public int getCount() {
		return users.size();
	}

	public List<User> getUsers() {
		return users;
	}
}
