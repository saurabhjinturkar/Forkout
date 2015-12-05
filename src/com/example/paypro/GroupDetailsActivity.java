package com.example.paypro;

import java.security.MessageDigest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paypro.data.Group;

public class GroupDetailsActivity extends ActionBarActivity {

	private TextView groupName;
	private ImageView image;
	private View fragment;
	private Group group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details);
		groupName = (TextView) findViewById(R.id.group_name);
		image = (ImageView) findViewById(R.id.group_icon);
		fragment = findViewById(R.id.titles);
		group = (Group) getIntent().getSerializableExtra("group");
		groupName.setText(group.getName());
		
		ListView list = (ListView) fragment.findViewById(R.id.group_memberList);
		Button button = new Button(GroupDetailsActivity.this);
		button.setText("Add group member");
		list.addFooterView(button);
		
		int colorCode = Color.LTGRAY;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] mdbytes = md.digest(group.getName().getBytes());

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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_detail_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
