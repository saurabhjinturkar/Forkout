/**
 * 
 */
package com.example.paypro.tasks;

import com.example.paypro.AddGroupActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author jintu
 *
 */
public class ContactPullerSignInTask extends ContactPullerTask {

	public ContactPullerSignInTask(Activity activity) {
		super(activity);
	}

	@Override
	protected void onPostExecute(Boolean success) {

		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		if (success) {
			Toast.makeText(context, "Contacts updated", Toast.LENGTH_LONG)
				.show();
//			Intent i = new Intent();
//			i.setClass(context, AddGroupActivity.class);
//			i.setAction(Intent.ACTION_VIEW);
//			context.startActivity(i);	
			
			
		} else {
			Toast.makeText(context, "Error in updating contacts",
				Toast.LENGTH_LONG).show();
		}
	
	}
}
