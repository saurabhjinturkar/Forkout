/**
 * 
 */
package com.example.paypro.tasks;

import java.io.IOException;
import java.util.ArrayList;
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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.paypro.core.Config;
import com.example.paypro.data.Group;
import com.example.paypro.data.User;
import com.example.paypro.dbhandler.GroupsHelper;
import com.example.paypro.manager.JSONHandler;

/**
 * @author jintu
 *
 */
public class FetchGroupTask extends AsyncTask<User, Void, Void> {

	public FetchGroupTask(Activity activity) {
		this.activity = activity;
		this.context = activity;
		this.dialog = new ProgressDialog(context);
	}

	/** progress dialog to show user that the backup is processing. */
	private ProgressDialog dialog;
	
	private Activity activity; 

	private Context context;
	
	private List<Group> groups = new ArrayList<Group>();

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
			Config.URL + "/services/get_groups.json?user_id=" + user.getId());

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity());
			Log.d("Http Response:", result);
			JSONObject obj = new JSONObject(result);

			if (obj.get("status").equals("success")) {
				JSONArray groups = obj.getJSONArray("groups");
				GroupsHelper gh = new GroupsHelper(context);
				for(int iter=0; iter < groups.length(); iter++) {
					JSONObject object = groups.getJSONObject(iter);
					Group group = (Group) JSONHandler.getInstance().convertToJavaUnderscoreCase(object.toString(), Group.class);
					gh.createGroup(group);
					this.groups.add(group);
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
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		
			Toast.makeText(context, "Contacts updated", Toast.LENGTH_LONG)
				.show();
			FetchGroupMembersTask task = new FetchGroupMembersTask(activity, groups);
			task.execute();
	}

	@Override
	protected void onCancelled() {
		dialog.dismiss();
		Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG)
			.show();
	}
}