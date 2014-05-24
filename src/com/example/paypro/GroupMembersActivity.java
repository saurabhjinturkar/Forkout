package com.example.paypro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.paypro.data.Group;
import com.example.paypro.data.User;
import com.example.paypro.dataprovider.GroupMemberAdapter;
import com.example.paypro.manager.ApplicationManager;
import com.example.paypro.tasks.GroupCreationTask;

public class GroupMembersActivity extends Activity {

	private Button addGroupMembers;
	private ListView selectionList;
	private GroupMemberAdapter adapter;
	private Group group;
	
	private static final String ID = "GroupMembersActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		group = (Group) intent.getExtras().get("group");
		group.setGroupAdminId(ApplicationManager.getInstance().getUser().getId());
		Log.d(ID, group.print());
		
		setContentView(R.layout.activity_group_members);
		addGroupMembers = (Button) findViewById(R.id.add_group_members);
		addGroupMembers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.w("GROUPMEMBER", "Add group member clicked");
				Intent i = new Intent();
				i.setClass(GroupMembersActivity.this, FriendsActivity.class);
				// i.setAction(Intent.ACTION_VIEW);
				System.out.println(i);
				startActivityForResult(i, 1);
			}
		});

		selectionList = (ListView) findViewById(R.id.member_selection_list);
		adapter = new GroupMemberAdapter(this, new ArrayList<User>());
		selectionList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.group_members, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		List<User> users = adapter.getUsers();
		if (users.size() < 2) {
			addGroupMembers.setError("Please select two or more members");
			return false;
		}
		for (User user : users) {
			group.getMembers().add(user.getId());
		}
		group.getMembers().add(ApplicationManager.getInstance().getUser().getId());
		Log.d(ID, group.print());
		addGroup();
		
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), GroupListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.setAction(Intent.ACTION_VIEW);

		intent.putExtra("group", group);
		startActivity(intent);
		return true;
	}

	private void addGroup() {
		group.setDescription("");
		group.setCreatedAt(Calendar.getInstance().getTime());
		group.setUpdatedAt(Calendar.getInstance().getTime());
		
		GroupCreationTask task = new GroupCreationTask(this);
		task.execute(group);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		ArrayList<User> selection = (ArrayList<User>) data
				.getSerializableExtra("selection");
		Log.w("Group selection", "" + selection);
		adapter.clear();
		adapter.addAll(selection);
		adapter.notifyDataSetChanged();

	}
}
