package com.example.paypro.gcm;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.paypro.GroupListActivity;
import com.example.paypro.R;
import com.example.paypro.data.Group;
import com.example.paypro.data.Transaction;
import com.example.paypro.dbhandler.GroupsHelper;
import com.example.paypro.dbhandler.TransactionHandler;
import com.example.paypro.dbhandler.UserHelper;
import com.example.paypro.manager.JSONHandler;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		System.out.println("GCM Called intent..");
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
				.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
				.equals(messageType)) {
				sendNotification("Deleted messages on server: "
					+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
				.equals(messageType)) {

				Log.i(TAG, "Received: " + extras.toString());

				Set<String> keys = extras.keySet();

				for (String key : keys) {

					if (key.equals("transaction")) {

						try {
							JSONObject json = new JSONObject(
								extras.getString("transaction"));

							Transaction transaction = (Transaction) JSONHandler
								.getInstance().convertToJavaUnderscoreCase(
									json.toString(), Transaction.class);

							TransactionHandler th = new TransactionHandler(
								getApplicationContext());
							th.createTransaction(transaction);
							th.close();

							UserHelper uh = new UserHelper(
								getApplicationContext());
							String str = uh
								.getUserById(transaction.getUserId())
								.getDisplayName();
							uh.close();

							GroupsHelper gh = new GroupsHelper(
								getApplicationContext());
							String title = gh.getGroupById(
								transaction.getGroupId()).getName();
							gh.close();

							sendNotification(str + ":" + transaction.display(),
								title);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if (key.equals("group")) {
						try {
							JSONObject json = new JSONObject(
								extras.getString("group"));

							Group group = (Group) JSONHandler.getInstance()
								.convertToJavaUnderscoreCase(json.toString(),
									Group.class);

							GroupsHelper gh = new GroupsHelper(
								getApplicationContext());
							gh.createGroup(group);
							gh.close();

							sendNotification(
								"You have been added to " + group.getName()
									+ " by " + group.getGroupAdminId(),
								group.getName());

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg, String title) {
		Log.d(TAG, "Preparing to send notification...: " + msg);
		mNotificationManager = (NotificationManager) this
			.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
			new Intent(this, GroupListActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
			this).setSmallIcon(R.drawable.ic_launcher).setContentTitle(title)
			.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
			.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		Log.d(TAG, "Notification sent successfully.");
	}

	private void sendNotification(String msg) {
		sendNotification(msg, "GCM Notification");
	}
}
