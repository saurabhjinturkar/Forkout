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

import com.example.paypro.ReportActivity;
import com.example.paypro.core.Config;
import com.example.paypro.data.Report;
import com.example.paypro.data.ReportData;

/**
 * @author jintu
 *
 */
public class ReportGenerationTask extends AsyncTask<Report, Void, Report> {

	private static final String ID = "ReportGenerationTask";
	private Context context;
	private ProgressDialog dialog;
	private ReportActivity reportActivity;
	private Activity activity;
	
	public ReportGenerationTask(Activity activity, ReportActivity reportActivity) {
		this.activity=activity;
		this.context = activity;
		this.reportActivity = reportActivity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		try {
			dialog.setMessage("Generating report");
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Report doInBackground(Report... params) {

		Report report = params[0];
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet(
			Config.URL + "/services/group_expenses_report.json?group_id=" + report.getGroupId());
		
		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String result = EntityUtils.toString(response.getEntity());
			Log.d("Http Response:", result);
			JSONObject obj = new JSONObject(result);

			if (obj.get("status").equals("success")) {
				System.out.println("REPORT VALUE" +  obj.get("report"));
				JSONObject reportJson = new JSONObject(obj.get("report").toString());
				Log.d("ID", reportJson.toString());
				
				return parseReport(obj.get("report").toString());

			} else {
				JSONObject errorJson = new JSONObject(obj.get("errors")
					.toString().toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public Report parseReport(String reportJson) throws JSONException {
		JSONObject json = new JSONObject(reportJson);
		Report report = new Report();
		report.setGroupId(json.getLong("group_id"));
		report.setExpensePerHead(json.getDouble("expences_per_head"));
		report.setTotalExpenses(json.getDouble("total_expences"));
		JSONArray details = json.getJSONArray("details");
		
		List<ReportData> reportDetails = new ArrayList<ReportData>();
		
		for(int iter=0; iter<details.length(); iter++) {
			ReportData data = new ReportData();
			JSONObject detailData = details.getJSONObject(iter);
			data.setUserId(detailData.getLong("user_id"));
			data.setAmountDues(detailData.getDouble("amount_dues"));
			data.setUserName(detailData.getString("user_name"));
			data.setAmountPaid(detailData.getDouble("amount_paid"));
			reportDetails.add(data);
		}
		
		report.setDetails(reportDetails);
		return report;
	}
	
	@Override
	protected void onPostExecute(Report result) {
		try {
			dialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null) {
			Log.d(ID, "Error while loading report");
		}
		Log.d(ID, "Generated report:" + result.toString());
		reportActivity.setReport(result);
	}
	
}
