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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.paypro.core.Config;
import com.example.paypro.data.User;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.gcm.RegisterGCM;
import com.example.paypro.manager.ApplicationManager;
import com.example.paypro.manager.JSONHandler;

/**
 * @author jintu
 *
 */
public class UserRegisterTask extends AsyncTask<User, Void, User> {

	private Context context;
	private ProgressDialog progressDialog;
	private Activity activity;
	
	public UserRegisterTask(Activity activity) {
		this.activity=activity;
		this.context = activity;
		this.progressDialog = new ProgressDialog(context);
		this.progressDialog.setCancelable(false);
		this.progressDialog.setMessage("Registering user");
		
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.progressDialog.show();
	}
	
	@Override
	protected User doInBackground(User... params) {
		User user = params[0];
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(
			Config.URL + "/users.json");

		RegisterGCM gcm = new RegisterGCM(context);
		String regId = gcm.registerGCM();
		
		System.out.println("GCM REGISTERED::" + regId);
		
		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("[user][first_name]", user
			.getFirstName()));
		nameValuePair.add(new BasicNameValuePair("[user][last_name]", user
			.getLastName()));
		nameValuePair.add(new BasicNameValuePair("[user][email]", user
			.getEmail()));
		nameValuePair.add(new BasicNameValuePair("[user][contact_number]",
			user.getContactNumber()));
		nameValuePair.add(new BasicNameValuePair("[user][password]", user.getPassword()));
		nameValuePair.add(new BasicNameValuePair("[user][device_registration_id]", regId));

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
				JSONObject userJson = new JSONObject(obj.get("user")
					.toString());
				Log.d("ID", userJson.toString());
				user.setId(userJson.getLong("id"));

				return user;

			} else {
				JSONObject errorJson = new JSONObject(obj.get("errors")
					.toString().toString());

			}

			Log.d("Created User", user.toString());

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
	protected void onPostExecute(final User user) {

		if (user != null) {
			
			String userJson = JSONHandler.getInstance().converToJSON(user);
			SharedPreferences userDetails =context
				.getSharedPreferences("user", Context.MODE_PRIVATE);
			Editor edit = userDetails.edit();
			edit.clear();
			edit.putString("user", userJson); 
			edit.commit();
			ApplicationManager.getInstance().setUser(user);
			UserHelper uh = new UserHelper(context);
			uh.createUser(user);
			
			Toast.makeText(context, "Login details are saved..",
				Toast.LENGTH_LONG).show();
			if(progressDialog.isShowing())  {
				progressDialog.dismiss();
			}
		ContactPullerTask contactTask = new ContactPullerTask(activity);
			contactTask.execute();
		} else {
			if(progressDialog.isShowing())  {
				progressDialog.dismiss();
			}
			Toast.makeText(context, "Error", Toast.LENGTH_LONG)
				.show();
		}
	}
}