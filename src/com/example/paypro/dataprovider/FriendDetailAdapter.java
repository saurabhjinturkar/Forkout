/**
 * 
 */
package com.example.paypro.dataprovider;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paypro.R;
import com.example.paypro.data.User;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.ApplicationManager;

/**
 * @author jintu
 *
 */
public class FriendDetailAdapter extends ArrayAdapter<User>{

	private List<User> users;
	private Context context;
	
	public FriendDetailAdapter(Context context) {
		super(context, R.layout.listitem_contacts);
		this.context = context;
		this.users = new ArrayList<User>();
	}

	@Override
	public void add(User object) {
		super.add(object);
		users.add(object);
	}
	
	@Override
	public void addAll(Collection<? extends User> collection) {
		super.addAll(collection);
		users.addAll(collection);
	}
	
	@Override
	public void remove(User object) {
		super.remove(object);
		users.remove(object);
	}
	
	@Override
	public void clear() {
		super.clear();
		users.clear();
	}
	
	public void fetchData() {
		UserHelper uh = new UserHelper(context);
		List<User> friends = uh.getAllFriends(ApplicationManager.getInstance().getUser().getId());
		addAll(friends);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.listitem_contacts, null,
				false);

			TextView contactName = (TextView) rowView
				.findViewById(R.id.contact_name);
			ImageView image = (ImageView) rowView
				.findViewById(R.id.contact_pic);
			int colorCode = Color.LTGRAY;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] mdbytes = md.digest(users.get(position)
					.getDisplayName().getBytes());

				// convert the byte to hex format method 1
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < mdbytes.length; i++) {
					sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
				}
				String resultStr = sb.toString();
				colorCode = Color.parseColor("#" + resultStr.substring(0, 6));
			} catch (Exception e) {
				Log.e("Exception", e.getMessage());
				e.printStackTrace();
			}

			image.setBackgroundColor(colorCode);
			contactName.setText(users.get(position).getDisplayName());
			return rowView;
	}
}
