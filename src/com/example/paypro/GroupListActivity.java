package com.example.paypro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.paypro.data.Group;
import com.example.paypro.dataprovider.GroupAdapter;
import com.example.paypro.dbhandler.GroupsHelper;
import com.example.paypro.tasks.SignOutTask;

public class GroupListActivity extends Activity {
	private ListView lv;
	private List<Group> groupsList;
	private static final String ID = "Group List";

	public GroupListActivity() {
		groupsList = new ArrayList<Group>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		lv = (ListView) findViewById(R.id.groups);

		Button addGroup1 = new Button(this);
		addGroup1.setText("Add Group");
		lv.addFooterView(addGroup1);
		
		GroupsHelper db = new GroupsHelper(this);
		groupsList = db.getallgroups();
		
		final GroupAdapter adapter = new GroupAdapter(this, groupsList);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Group group = groupsList.get(position);
				System.out.println("item selected " + position + " Group name"
						+ group);
				Intent i = new Intent();
				i.setClass(getApplicationContext(), ChatActivity.class);
				i.putExtra("group", group);
				i.setAction(Intent.ACTION_VIEW);
				startActivity(i);
				finish();
			}
		});
		
		addGroup1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.w(ID, "Add group clicked");
				Intent i = new Intent();
				i.setClass(getApplicationContext(), AddGroupActivity.class);
				i.setAction(Intent.ACTION_VIEW);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("Menu selected.. " + item);
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent();
			i.setClass(this, SettingsActivity.class);
			startActivity(i);
			return true;	

		case R.id.action_contact:
			Intent i1 = new Intent();
			i1.setClass(this, ContactsActivity.class);
			startActivity(i1);
			return true;
			
		case R.id.action_signout:
			new SignOutTask(this).execute();
			return true;
				
		default:
			return false;
		}
	}
}
