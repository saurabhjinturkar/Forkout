package com.example.paypro;

import com.example.paypro.data.Group;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AddGroupActivity extends Activity {

	private TextView groupNameText;
	private Group group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_group);
		groupNameText = (TextView) findViewById(R.id.groupName);
		group = new Group();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_group, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_group_next:
			if (isPageComplete()) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(), GroupMembersActivity.class);
				i.setAction(Intent.ACTION_VIEW);
				group.setName(groupNameText.getText().toString());
				i.putExtra("group", group);
				startActivity(i);
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean isPageComplete() {
		if (!TextUtils.isEmpty(groupNameText.getText().toString())) {
			return true;
		}
		groupNameText.setError("Please enter group name");
		return false;
	}
}
