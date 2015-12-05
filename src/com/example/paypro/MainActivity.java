package com.example.paypro;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.paypro.data.User;
import com.example.paypro.manager.ApplicationManager;
import com.example.paypro.manager.JSONHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {
	private static int SPLASH_TIME_OUT = 3000;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SharedPreferences userDetails = getBaseContext().getSharedPreferences(
			"user", MODE_PRIVATE);
		String userJson = userDetails.getString("user", "");

		// Splash
		String isSplashShown = userDetails.getString("isSplashShown",
			Boolean.FALSE.toString());
		if (isSplashShown != null && Boolean.parseBoolean(isSplashShown)) {
			callLogin();
		} else {
			Editor splash = userDetails.edit();
			splash.putString("isSplashShown", Boolean.TRUE.toString());
			splash.commit();
			ApplicationManager.getInstance().setDip(this);
			setContentView(R.layout.activity_main);
			Log.d("User JSON", userJson);
			User user;
			if (!userJson.isEmpty()) {
				user = (User) JSONHandler.getInstance().convertToJava(userJson,
					User.class);
				if (user.getId() > 0) {
					ApplicationManager.getInstance().setUser(user);
					Log.d("User", user.toString());
					Intent i = new Intent(MainActivity.this,
						GroupListActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(i);
					finish();
					return;
				}
			}
			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when
				 * you want to show case your app logo / company
				 */

				@Override
				public void run() {

					// This method will be executed once the timer is over
					// Start your app main activity

					callLogin();

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT);

//			new Handler().postDelayed(new Runnable() {
//
//				/*
//				 * Showing splash screen with a timer. This will be useful when
//				 * you want to show case your app logo / company
//				 */
//
//				public void run() {
//					// This method will be executed once the timer is over
//					// Start your app main activity
//					Intent i = new Intent(MainActivity.this,
//						LoginActivity.class);
//					startActivity(i);
//
//					// close this activity
//					finish();
//				}
//			}, SPLASH_TIME_OUT);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
			.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		Editor splash = getBaseContext().getSharedPreferences("user",
			MODE_PRIVATE).edit();
		splash.putString("isSplashShown", Boolean.FALSE.toString());
		splash.commit();
		super.onDestroy();
	}

	public void callLogin() {
		Intent i = new Intent(MainActivity.this, LoginActivity.class);
//		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//		i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		startActivity(i);
	}
}
