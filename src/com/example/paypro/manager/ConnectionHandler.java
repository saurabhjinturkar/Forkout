/**
 * 
 */
package com.example.paypro.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author jintu
 * 
 */
public class ConnectionHandler extends BroadcastReceiver {
	private Context context;

	public ConnectionHandler(Context context) {
		this.context = context;
	}

	public ConnectionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		ConnectivityManager connectivityManager = (ConnectivityManager) arg0
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		NetworkInfo mobNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (activeNetInfo != null) {
			Toast.makeText(arg0,
					"Active Network Type : " + activeNetInfo.getTypeName(),
					Toast.LENGTH_SHORT).show();
		}
		if (mobNetInfo != null) {
			Toast.makeText(arg0,
					"Mobile Network Type : " + mobNetInfo.getTypeName(),
					Toast.LENGTH_SHORT).show();
		}
	}
}
