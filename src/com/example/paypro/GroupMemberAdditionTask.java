/**
 * 
 */
package com.example.paypro;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.paypro.core.Config;
import com.example.paypro.data.Group;

/**
 * @author jintu
 *
 */
public class GroupMemberAdditionTask extends AsyncTask<Group, Void, List<Integer>>{

	private static final String ID = "GroupMemberAdditionActivity";

	public GroupMemberAdditionTask(Activity activity) {
		this.activity=activity;
		this.context = activity.getApplicationContext();
		this.dialog = new ProgressDialog(context);
		this.context = context;
	}

	/** progress dialog to show user that the backup is processing. */
	private ProgressDialog dialog;
	
	private Activity activity;

	private Context context;

	protected void onPreExecute() {
		try {
			this.dialog.setMessage("Syncing group members.. ");
			this.dialog.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected List<Integer> doInBackground(Group... params) {
		Group group = params[0];
		
		Log.d(ID, group.toString());
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(
			Config.URL + "/services/add_group_members.json");

		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("group_id", String.valueOf(group.getId())));
		StringBuilder str = new StringBuilder();
		str.append("[");
		for (Long memberId : group.getMembers()) {
			str.append(memberId + ",");
		}
		str.replace(str.length() - 1, str.length(), "]");
		nameValuePair.add(new BasicNameValuePair("group_member_ids", str.toString()));
		
		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
			Log.d(ID, "Error");
		}

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity());
			Log.d(ID+" Http Response:", result);
			JSONObject obj = new JSONObject(result);

			if (obj.get("status").equals("success")) {
//				JSONObject groupJson = new JSONObject(obj.get("group").toString());
//				Log.d("ID", groupJson.toString());
//				group.setId(groupJson.getLong("id"));
//				Toast.makeText(context, "Group members added", Toast.LENGTH_LONG)
//				.show();
				Log.d(ID, "Successfully added group members");
				return new ArrayList<Integer>();

			} else {
				JSONObject errorJson = new JSONObject(obj.get("errors")
					.toString().toString());
				
//				Toast.makeText(context, "Group member addition failed" + errorJson.toString(), Toast.LENGTH_LONG)
//				.show();
				Log.d("ID", "Error :" + errorJson.toString());
			
			}
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
	protected void onPostExecute(final List<Integer> members) {
		
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (members != null) {
			Toast.makeText(context, "Members added", Toast.LENGTH_LONG)
				.show();
			Intent i = new Intent();
			i.setClass(context, GroupListActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
			i.setAction(Intent.ACTION_VIEW);
			context.startActivity(i);	
			
		} else {
			Toast.makeText(context, "Error in adding group members",
				Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onCancelled() {
		try {
			dialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG)
			.show();
	}
}
