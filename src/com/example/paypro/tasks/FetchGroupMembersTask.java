/**
 * 
 */
package com.example.paypro.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.example.paypro.GroupListActivity;
import com.example.paypro.core.Config;
import com.example.paypro.data.Group;
import com.example.paypro.data.User;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.JSONHandler;

/**
 * @author jintu
 * 
 */
public class FetchGroupMembersTask extends AsyncTask<Void, Void, Void> {

	List<Group> groups;
	private Activity activity;

	public FetchGroupMembersTask(Activity activity, List<Group> groups) {
		this.activity = activity;
		this.context = activity;
		this.dialog = new ProgressDialog(context);

		this.groups = new ArrayList<Group>(groups);
	}

	/** progress dialog to show user that the backup is processing. */
	private ProgressDialog dialog;

	private Context context;

	protected void onPreExecute() {
		try {
			this.dialog.setMessage("Syncing group members.. ");
			this.dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post

		StringBuilder groupIds = new StringBuilder().append("[");
		for (Group group2 : groups) {
			groupIds.append(group2.getId() + ",");
		}

		if (groupIds.length() != 0) {
			groupIds.replace(groupIds.length() - 1, groupIds.length(), "]");
		} else {
			groupIds.append("]");
		}
		System.out.println("Group ids for fetching members"
			+ groupIds.toString());
		HttpGet httpGet = new HttpGet(Config.URL
			+ "/services/get_all_group_members.json?group_ids="
			+ groupIds.toString());

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity());
			Log.d("Http Response:", result);
			JSONObject obj = new JSONObject(result);

			if (obj.get("status").equals("success")) {
				JSONArray hashs = obj.getJSONArray("group_members");

				UserHelper uh = new UserHelper(context);
				for (int iter = 0; iter < hashs.length(); iter++) {
					JSONObject hash = (JSONObject) hashs.get(iter);
					Iterator<String> keys = hash.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						JSONArray valueArray = hash.getJSONArray(key);
						for (int iter2 = 0; iter2 < valueArray.length(); iter2++) {
							JSONObject value = (JSONObject) valueArray
								.get(iter2);
							User user = (User) JSONHandler.getInstance()
								.convertToJavaUnderscoreCase(value.toString(),
									User.class);
							uh.createUser(user);
						}
					}
				}
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
		Intent i = new Intent(context, GroupListActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		context.startActivity(i);
	}
}