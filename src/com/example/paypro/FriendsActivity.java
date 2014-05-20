package com.example.paypro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.paypro.data.User;
import com.example.paypro.dataprovider.FriendsAdapter;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.ApplicationManager;

public class FriendsActivity extends Activity {

	private ListView listView;
	private FriendsAdapter friendsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		UserHelper uh = new UserHelper(this);
//		uh.createUser(new User(1, "Saurabh", "Jinturkar",
//				"saurabhjinturkar@gmail.com", "9420816679", "true"));
//		uh.createUser(new User(2, "Tejas", "Jinturkar",
//				"tejasjinturkar@gmail.com", "9850095852", "true"));
//		uh.createUser(new User(3, "Kaustubh", "Jinturkar",
//				"kkjinturkar@gmail.com", "8028006565", "true"));
//		uh.createUser(new User(4, "Himanshu", "Shewale",
//				"himanshushewale@gmail.com", "8875729523", "true"));
//		uh.createUser(new User(5, "Pratik", "Bhore", "ppb@gmail.com",
//				"9480265620", "true"));

		List<User> friends = uh.getAllFriends(ApplicationManager.getInstance()
			.getUser().getId());

		listView = (ListView) findViewById(R.id.listView1);
		friendsAdapter = new FriendsAdapter(this, friends);
		listView.setAdapter(friendsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.w("FRIENDS", "Selection is " + friendsAdapter.getSelection());

		Intent output = new Intent();
		output.putExtra("selection",
				(ArrayList<User>) friendsAdapter.getSelection());
		setResult(RESULT_OK, output);
		finish();
		return true;
	}
}
