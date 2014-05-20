/**
 * 
 */
package com.example.paypro.dataprovider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.paypro.R;
import com.example.paypro.data.User;

/**
 * @author jintu
 * 
 */
public class FriendsAdapter extends ArrayAdapter<User> {

	private Context context;
	private List<User> friends;
	private List<User> selection;

	public FriendsAdapter(Context context, List<User> friends) {
		super(context, R.layout.listitem_friends);
		this.friends = friends;
		this.context = context;
		this.selection = new ArrayList<User>();
		Log.w("FRIENDS",
				"Friends list " + friends.size() + " == " + friends.toString());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listitem_friends, null, false);
		Log.w("FRIENDS", "getView Called.. " + position);

		TextView friend_name = (TextView) rowView.findViewById(R.id.friend_name);
		CheckBox selectionBox = (CheckBox) rowView.findViewById(R.id.friend_selection);
		selectionBox.setTag(position);
		selectionBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.w("FRIENDS", "Button clicked " + isChecked);
				Log.w("FRIENDS", "Button location:" + buttonView.getTag());
				if (isChecked) {
					selection.add(new User(friends.get((Integer) buttonView
							.getTag())));
				} else {
					selection.remove(friends.get((Integer) buttonView.getTag()));
				}
			}
		});
		friend_name.setText(friends.get(position).getDisplayName());

		return rowView;
	}

	@Override
	public int getCount() {
		return friends.size();
	}

	public List<User> getSelection() {
		return selection;
	}
}
