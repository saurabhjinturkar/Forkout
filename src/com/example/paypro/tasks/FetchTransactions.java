/**
 * 
 */
package com.example.paypro.tasks;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.paypro.GroupListActivity;
import com.example.paypro.core.Config;
import com.example.paypro.data.Transaction;
import com.example.paypro.data.User;
import com.example.paypro.dbhandler.TransactionHandler;
import com.example.paypro.manager.JSONHandler;

/**
 * @author jintu
 *
 */
public class FetchTransactions extends AsyncTask<User, Void, Void> {

	public FetchTransactions(Activity activity) {
		this.activity=activity;
		this.context = activity;
		this.dialog = new ProgressDialog(context);
	}

	/** progress dialog to show user that the backup is processing. */
	private ProgressDialog dialog;
	
	private Activity activity;

	private Context context;

	protected void onPreExecute() {
		try {
			this.dialog.setMessage("Syncing group.. ");
			this.dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Void doInBackground(User... params) {
		User user = params[0];
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpGet httpGet = new HttpGet(
			Config.URL + "/services/fetch_group_transactions.json?user_id=" + user.getId());

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity());
			Log.d("Http Response:", result);
			JSONObject obj = new JSONObject(result);

			if (obj.get("status").equals("success")) {
				JSONArray groups = obj.getJSONArray("transactions");
				TransactionHandler th = new TransactionHandler(context);
				for(int iter=0; iter < groups.length(); iter++) {
					JSONObject object = groups.getJSONObject(iter);
					Transaction transaction = (Transaction) JSONHandler.getInstance().convertToJavaUnderscoreCase(object.toString(), Transaction.class);
					th.createTransaction(transaction);
				}
				th.close();
			} else {
				JSONObject errorJson = new JSONObject(obj.get("errors")
					.toString().toString());
			}
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
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (result != null) {
			Intent i = new Intent();
			i.setClass(context, GroupListActivity.class);
			context.startActivity(i);
		}
	}

	@Override
	protected void onCancelled() {
		dialog.dismiss();
		Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG)
			.show();
	}
}