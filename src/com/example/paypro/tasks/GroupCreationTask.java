/**
 * 
 */
package com.example.paypro.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.paypro.GroupMemberAdditionTask;
import com.example.paypro.core.Config;
import com.example.paypro.data.Group;
import com.example.paypro.dbhandler.GroupUserHelper;
import com.example.paypro.dbhandler.GroupsHelper;

/**
 * @author jintu
 *
 */
public class GroupCreationTask extends AsyncTask<Group, Void, Group> {

	public GroupCreationTask(Activity activity) {
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
	protected Group doInBackground(Group... params) {
		Group group = params[0];
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(
			Config.URL + "/groups.json");

		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("[group][name]", group.getName()));
		nameValuePair.add(new BasicNameValuePair("[group][description]", group.getDescription()));
		nameValuePair.add(new BasicNameValuePair("[group][group_admin_id]", Long.toString(group.getGroupAdminId())));
		
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
				JSONObject groupJson = new JSONObject(obj.get("group").toString());
				Log.d("ID", groupJson.toString());
				group.setId(groupJson.getLong("id"));
				return group;

			} else {
				JSONObject errorJson = new JSONObject(obj.get("errors")
					.toString().toString());


			}

			Log.d("Created Group", group.toString());

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
	protected void onPostExecute(final Group group) {
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		if (group != null) {
			Toast.makeText(context, "Contacts updated", Toast.LENGTH_LONG)
				.show();
			
			GroupsHelper groups = new GroupsHelper(context);
			groups.createGroup(group);
			GroupUserHelper groupUserHelper = new GroupUserHelper(context);
			groupUserHelper.createGroupUsers(group);
			
			GroupMemberAdditionTask task = new GroupMemberAdditionTask(activity); 
			task.execute(group);
			
		} else {
			Toast.makeText(context, "Error in updating contacts",
				Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onCancelled() {
		dialog.dismiss();
		Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG)
			.show();
	}
}