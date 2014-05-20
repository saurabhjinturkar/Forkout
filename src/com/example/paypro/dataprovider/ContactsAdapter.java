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
public class ContactsAdapter extends ArrayAdapter<User> {

	private Context context;
	private List<User> contacts;
	private boolean isSearch = false;
	private List<User> searchResult;
	final private List<User> tempContacts;

	public ContactsAdapter(Context context) {
		super(context, R.layout.listitem_contacts);
		this.context = context;
		UserHelper uh = new UserHelper(context);
		contacts = uh.getAllFriends(ApplicationManager.getInstance().getUser()
			.getId());
		this.searchResult = new ArrayList<User>();
		tempContacts = new ArrayList<User>(this.contacts);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

//		if (convertView == null) {
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
				byte[] mdbytes = md.digest(contacts.get(position)
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
			contactName.setText(contacts.get(position).getDisplayName());

			return rowView;
//		}
//		return convertView;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public void add(User object) {
		super.add(object);
		contacts.add(object);
	}
	
	@Override
	public void addAll(Collection<? extends User> collection) {
		super.addAll(collection);
		contacts.addAll(collection);
	}
	
	@Override
	public void clear() {
		super.clear();
		contacts.clear();
	}
	
	@Override
	public void remove(User object) {
		super.remove(object);
		contacts.remove(object);
	}
	
	public void search(String hint) {
		if(hint == null || hint.trim().isEmpty()) {
			setSearch(false);
			return;
		}
		
		System.out.println("HINT-->" + hint);
		searchResult.clear();
		for (User user: contacts) {
			System.out.println(user.getDisplayName() + "-->" + user.getDisplayName().trim().toLowerCase().contains(hint.toLowerCase()) );
			if (user.getDisplayName().trim().toLowerCase().contains(hint.trim().toLowerCase())) {
				searchResult.add(user);
			}
		}
		clear();
		addAll(searchResult);
		notifyDataSetChanged();
	}
	
	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
		if(!isSearch) {
			clear();
			addAll(tempContacts);
			notifyDataSetChanged();
		}
	}
}
