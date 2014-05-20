/**
 * 
 */
package com.example.paypro.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Tarang
 * 
 */
public class ConnectionTask extends AsyncTask<Object, String, Object> {

	private String url;

	// final private String baseURL = "199.79.63.83";
	final private String baseURL = "";

	public ConnectionTask(String url) {
		this.url = url;
	}

	@Override
	protected Object doInBackground(Object... params) {
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HTTP Post-
		HttpPost httpPost = new HttpPost(baseURL + url);

		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

		String message = JSONHandler.getInstance().converToJSON(params[0]);
		nameValuePair.add(new BasicNameValuePair("message", message));

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
			HttpEntity entity = response.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			StringBuilder responseStr = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				responseStr.append(line).append("\n");
			}

			reader.close();
			// writing response to log
			Log.d("Http Response:", response.toString());

			return responseStr;

		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();

		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();
		}
		return null;
	}
}
