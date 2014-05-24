package com.example.paypro;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.paypro.data.Group;
import com.example.paypro.data.Transaction;
import com.example.paypro.dataprovider.TransactionAdapter;
import com.example.paypro.dbhandler.TransactionHandler;
import com.example.paypro.manager.ApplicationManager;
import com.example.paypro.tasks.TransactionCreationTask;

public class ChatActivity extends Activity {
	private com.example.paypro.dataprovider.TransactionAdapter adapter;
	private ListView lv;
	private EditText description;
	private EditText amount;
	private Button submit;
	private Group group;
	private static String ID = "ChatActivity";

	@Override
	public void onContentChanged() {
		super.onContentChanged();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		group = (Group) getIntent().getSerializableExtra("group");
		
		Log.w(ID, group.print());
		setContentView(R.layout.activity_chat);
		
		lv = (ListView) findViewById(R.id.groups);
		adapter = new TransactionAdapter(this,
				R.layout.listitem_discuss);
		lv.setAdapter(adapter);
		TransactionHandler txHandler = new TransactionHandler(this);
		List<Transaction> transactions = txHandler.getTransactionsByGroupId(group.getId());
		Log.d(ID, "---" + transactions.toString());
		
		adapter.addAll(transactions);
		
		amount = (EditText) findViewById(R.id.groupamount);
		description = (EditText) findViewById(R.id.groupdescription);
		submit = (Button) findViewById(R.id.groupsubmit);

		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Transaction transaction = new Transaction();
				transaction.setGroupId(group.getId());
				transaction.setUserId(ApplicationManager.getInstance().getUser().getId());
				transaction.setDetails("");
				transaction.setGroupMemberId(ApplicationManager.getInstance().getUser().getId());
				transaction.setAmount(Double.parseDouble(amount.getText()
						.toString()));
				transaction.setDescription(description.getText().toString());
				transaction.setCreatedAt(new Date());
//				new ConnectionTask("transaction/insert").execute(new Transaction[]{transaction});
				Log.d("TRANSACTION ", transaction.toString());
				
				TransactionCreationTask task = new TransactionCreationTask(ChatActivity.this);
				task.execute(transaction);
				
//				TransactionHandler txHandler = new TransactionHandler(ChatActivity.this);
//				Log.d(ID, "Unique id"+txHandler.getMaxTransactionId()+1);
//				transaction.setId(txHandler.getMaxTransactionId()+1);
//				txHandler.createTransaction(transaction);
//				txHandler.close();
				
				adapter.add(transaction);
				adapter.notifyDataSetChanged();
				amount.setText("");
				description.setText("");
				lv.setSelection(adapter.getCount() - 1);
			}
		});
		
//		setTitle(group.getName());
//		View title = getWindow().findViewById(android.R.id.title);
//		View titleBar = (View) title.getParent();
//		
//		
//		MessageDigest md;
//		int colorCode = Color.LTGRAY;
//		try {
//			md = MessageDigest.getInstance("MD5");
//			byte[] mdbytes = md.digest(group.getName().getBytes());
////			String resultStr = new String(result);
//			 
//	        //convert the byte to hex format method 1
//	        StringBuffer sb = new StringBuffer();
//	        for (int i = 0; i < mdbytes.length; i++) {
//	          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//	        }
//	        String resultStr = sb.toString();
//	        Log.w(ID, "MD5" + resultStr);
//	        Log.w(ID, "COLOR CODE " + "#" + resultStr.substring(0,6));
//			colorCode = Color.parseColor("#" + resultStr.substring(0,6));
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		titleBar.setBackgroundColor(colorCode);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.report_generate:
			Intent i = new Intent();
			i.setClass(this, ReportActivity.class);
			i.setAction(Intent.ACTION_VIEW);
			i.putExtra("group", group);
			startActivity(i);
			return true;
		default:
				return super.onOptionsItemSelected(item);
		}
		
	}
}