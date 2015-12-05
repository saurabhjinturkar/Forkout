/**
 * 
 */
package com.example.paypro.dataprovider;

import java.util.Collection;
import java.util.List;

import com.example.paypro.R;
import com.example.paypro.data.Transaction;
import com.example.paypro.dbhandler.TransactionHandler;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * @author jintu
 *
 */
public class FriendTransactionAdapter extends ArrayAdapter<Transaction>{
	
	private List<Transaction> transactions;
	private Context context;
	
	public FriendTransactionAdapter(Context context, int resource) {
		super(context, R.layout.listitem_chat);
		this.context = context;
	}

	@Override
	public void add(Transaction object) {
		super.add(object);
		transactions.add(object);
	}
	
	@Override
	public void addAll(Collection<? extends Transaction> collection) {
		super.addAll(collection);
		transactions.addAll(collection);
	}
	
	@Override
	public void remove(Transaction object) {
		super.remove(object);
		transactions.remove(object);
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public void fetchTransactions(long userId1, long userId2) {
		TransactionHandler th = new TransactionHandler(context);
		th.getTransactionForUsers(userId1, userId2);
	}
}
