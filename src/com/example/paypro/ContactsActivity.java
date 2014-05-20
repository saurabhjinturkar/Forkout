package com.example.paypro;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.example.paypro.dataprovider.ContactsAdapter;

public class ContactsActivity extends Activity {

	private EditText searchText;
	private ListView contactList;
	private ContactsAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		searchText = (EditText) findViewById(R.id.searchText);
		contactList = (ListView) findViewById(R.id.contactList);
		
		adapter = new ContactsAdapter(this);
		contactList.setAdapter(adapter);
		
		searchText.addTextChangedListener(new SearchTextClickListener());
		
		searchText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		setTitle("Contacts");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return false;
	}
	
	class SearchTextClickListener implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
			int count) {
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			adapter.setSearch(true);
			adapter.search(s.toString());
		}
	}
}
