/**
 * 
 */
package com.example.paypro.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.paypro.core.Config;
import com.example.paypro.data.Transaction;
import com.example.paypro.dbhandler.TransactionHandler;
import com.example.paypro.manager.ApplicationUtil;

/**
 * @author jintu
 *
 */
public class TransactionCreationTask extends
	AsyncTask<Transaction, Void, Transaction> {

	
	private Context context;
	private static final String ID = "TransactionCreationTask";
	private Activity activity;

	public TransactionCreationTask(Activity activity) {
		this.activity=activity;
		this.context = activity;
	}
	
	@Override
	protected Transaction doInBackground(Transaction... params) {

		Transaction transaction = params[0];
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(
			Config.URL + "/transactions.json");

		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
		nameValuePair.add(new BasicNameValuePair("[transaction][description]", transaction.getDescription()));
		nameValuePair.add(new BasicNameValuePair("[transaction][details]", transaction.getDetails()));
		nameValuePair.add(new BasicNameValuePair("[transaction][amount]", String.valueOf(transaction.getAmount())));
		nameValuePair.add(new BasicNameValuePair("[transaction][group_id]", String.valueOf(transaction.getGroupId())));
		nameValuePair.add(new BasicNameValuePair("[transaction][user_id]", String.valueOf(transaction.getUserId())));
		nameValuePair.add(new BasicNameValuePair("[transaction][group_member_id]", String.valueOf(transaction.getGroupMemberId())));

		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity());
			Log.d("Http Response:", result);
			JSONObject obj = new JSONObject(result);

			if (obj.get("status").equals("success")) {
				System.out.println("TRANSACTION VALUE" +  obj.get("transaction"));
				JSONObject transactionJson = new JSONObject(obj.get("transaction").toString());
				Log.d("ID", transactionJson.toString());
				transaction.setId(transactionJson.getLong("id"));
				try {
					transaction.setCreatedAt(ApplicationUtil.parseDate(transactionJson.getString("created_at")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Log.d(ID, transaction.toString());
				return transaction;

			} else {
				JSONObject errorJson = new JSONObject(obj.get("errors")
					.toString().toString());
			}

			// writing response to log
			Log.d("Http Response:", result);
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();

		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Transaction result) {
		super.onPostExecute(result);
		
		if(result == null) {
			Log.d(ID, "Transaction not created");
			return;
		}
		
		TransactionHandler th = new TransactionHandler(context);
		th.createTransaction(result);
		Log.d("Transaction created", result.toString());
		
	}
}
