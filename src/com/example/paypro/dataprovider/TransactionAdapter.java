package com.example.paypro.dataprovider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.paypro.R;
import com.example.paypro.data.Transaction;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.ApplicationManager;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

	private List<Transaction> transactions = new ArrayList<Transaction>();

	@Override
	public void add(Transaction transaction) {
		transactions.add(transaction);
		// chat.add(object);
		// super.add(object);
	}

	public TransactionAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return transactions.size();
	}

	public Transaction getItem(int index) {
		return transactions.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_chat, parent, false);
		}
		
		RelativeLayout wrapper = (RelativeLayout) row.findViewById(R.id.wrapper);
		
		RelativeLayout relWrapper = (RelativeLayout) row
			.findViewById(R.id.relWrapper);
		Transaction transaction1 = getItem(position);

		TextView name = (TextView) row.findViewById(R.id.name);
		TextView chat = (TextView) row.findViewById(R.id.desc);
		TextView date = (TextView) row.findViewById(R.id.date);

		UserHelper userHelper = new UserHelper(getContext());
		if (userHelper.getUserById(transaction1.getUserId()) != null) {
			name.setText(userHelper.getUserById(transaction1.getUserId())
				.getDisplayName());
		} else {
			name.setText("user id" + transaction1.getUserId());
		}
		chat.setText(transaction1.display());

		System.out.println("****TRANSACTION****" + transaction1);
		String dateStr = new SimpleDateFormat("dd-MM-yyyy HH:mm")
			.format(transaction1.getCreatedAt());

		date.setText(dateStr);

		boolean isChatFromUser = (transaction1.getUserId() == ApplicationManager
			.getInstance().getUser().getId());

		RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		int left = isChatFromUser ? 0:30 * (int)ApplicationManager.getInstance().getDip();
		int right = isChatFromUser ? 30 * (int)ApplicationManager.getInstance().getDip():0;
		
		params.setMargins(left, 0, right, 0);
		relWrapper.setLayoutParams(params);
		relWrapper.setBackgroundResource(isChatFromUser ? R.drawable.chat_bg1
			: R.drawable.chat_bg1);
		// chatDesciption.setBackgroundResource(isChatFromUser
		// ? R.drawable.chat_yellow : R.drawable.chat_green);
		System.out.println(isChatFromUser);
		wrapper.setGravity(isChatFromUser ? Gravity.LEFT : Gravity.RIGHT);
		
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
			.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	@Override
	public void addAll(Collection<? extends Transaction> collection) {
		// super.addAll(collection);
		transactions.addAll(collection);
	}
}
