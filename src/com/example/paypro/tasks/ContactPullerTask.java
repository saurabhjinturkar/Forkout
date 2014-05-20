/**
 * 
 */
package com.example.paypro.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.paypro.AddGroupActivity;
import com.example.paypro.core.Config;
import com.example.paypro.data.User;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.ContactHandler;
import com.example.paypro.manager.JSONHandler;
import com.google.gson.reflect.TypeToken;

/**
 * @author jintu
 * 
 */
public class ContactPullerTask extends AsyncTask<Void, Void, Boolean> {

	public ContactPullerTask(Activity activity) {
		this.context = activity;
		this.dialog = new ProgressDialog(context);
		this.activity = activity;
	}

	/** progress dialog to show user that the backup is processing. */
	protected ProgressDialog dialog;

	protected Context context;

	protected Activity activity;

	protected void onPreExecute() {
		try {
			this.dialog.setMessage("Fetching contacts..");
			this.dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (success) {
			Toast.makeText(context, "Contacts updated", Toast.LENGTH_LONG)
				.show();
			Intent i = new Intent();
			i.setClass(context, AddGroupActivity.class);
			i.setAction(Intent.ACTION_VIEW);
			context.startActivity(i);	
			
		} else {
			Toast.makeText(context, "Error in updating contacts",
				Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ContactHandler handler = new ContactHandler(context);
		List<String> contacts = handler.fetchContacts();

		Log.d("Contact", contacts.toString());
		String contactStr = JSONHandler.getInstance().converToJSON(contacts);

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(
			Config.URL + "/services/sync_contacts");
		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("contacts", contactStr));

		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			Log.d("Exceotion Response:", e.getMessage());
			e.printStackTrace();
		}

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity());

			JSONObject obj = new JSONObject(result);

			System.out.println(obj.getString("status"));

			Type listType = new TypeToken<List<User>>() {
			}.getType();
			List<User> users = (List<User>) JSONHandler.getInstance()
				.convertToJavaUnderscoreCase(obj.getString("users"), listType);

			UserHelper userHelper = new UserHelper(context);

			for (User user : users) {
				System.out.println("HERE--->" + user.toString());
				user.setRegistrationStatus("");
				user.setCreatedAt(new Date());
				user.setUpdatedAt(new Date());
				userHelper.createUser(user);
			}

			System.out.println(users);

			Log.d("HTTP RESPONSE ", result);
			// writing response to log
			Log.d("Http Response:", response.toString());
			return true;
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return false;
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return false;
		}
	}
}
