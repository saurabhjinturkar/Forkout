/**
 *
 */
package com.example.paypro.dataprovider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.example.paypro.R;
import com.example.paypro.data.Group;
import com.example.paypro.dbhandler.GroupsHelper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author jintu
 *
 */
public class GroupAdapter extends ArrayAdapter<Group> {

	private static final String ID = "GroupAdapter";
	private Context context;
	private List<Group> groups;

	public GroupAdapter(Context context) {
		super(context, R.layout.listitem_group);
		this.context = context;
		GroupsHelper gh = new GroupsHelper(context);
		this.groups = gh.getallgroups();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = null;
		if (convertView == null) {
			view = inflater.inflate(R.layout.listitem_group, null);
		} else {
			view = convertView;
		}

		TextView groupName = (TextView) view.findViewById(R.id.group_name);
		TextView lastChat = (TextView) view.findViewById(R.id.last_chat);
		ImageView image = (ImageView) view.findViewById(R.id.group_pic);

		groupName.setText(groups.get(position).getName());
		lastChat.setText("Last chat here");

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] mdbytes = md.digest(groups.get(position).getName().getBytes());
//			String resultStr = new String(result);

	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < mdbytes.length; i++) {
	          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        String resultStr = sb.toString();
	        Log.w(ID, "MD5" + resultStr);
	        Log.w(ID, "COLOR CODE " + "#" + resultStr.substring(0,6));
			int colorCode = Color.parseColor("#" + resultStr.substring(0,6));

			image.setBackgroundColor(colorCode);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			Log.e("EXception in group adapter", e.getMessage());
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public int getCount() {
		return groups.size();
	}

	@Override
	public void add(Group object) {
		super.add(object);
		groups.add(object);
	}

	public Group get(int position) {
		return groups.get(position);
	}
}
