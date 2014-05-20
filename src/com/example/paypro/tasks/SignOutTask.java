/**
 * 
 */
package com.example.paypro.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.paypro.MainActivity;
import com.example.paypro.dbhandler.GroupUserHelper;
import com.example.paypro.dbhandler.GroupsHelper;
import com.example.paypro.dbhandler.TransactionHandler;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.ApplicationManager;

/**
 * @author jintu
 *
 */
public class SignOutTask extends AsyncTask<Void, Void, Void>{

	private Context context;
	private ProgressDialog dialog;
	private Activity activity;
	
	public SignOutTask(Activity activity) {
		this.context = activity;
		this.dialog = new ProgressDialog(context);
		this.activity=activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Signing out..");
		dialog.show();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		ApplicationManager.getInstance().clearSharedPreferences(context);
		new GroupsHelper(context).flush();
		new GroupUserHelper(context).flush();
		new TransactionHandler(context).flush();
		new UserHelper(context).flush();
		ApplicationManager.getInstance().setUser(null);
		
		Intent i = new Intent();
		i.setClass(context, MainActivity.class);
		i.setAction(Intent.ACTION_VIEW);
		context.startActivity(i);	
		
		return null;
	}
	
	protected void onPostExecute(Void result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		activity.finish();
	};
}
