/**
 * 
 */
package com.example.paypro.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;

import com.example.paypro.data.User;

/**
 * @author Tarang
 *
 */
public class ApplicationManager {

	private User user;
	final private static ApplicationManager instance = new ApplicationManager();
	private float dip;
	
	
	private ApplicationManager() {
	}
	
	public static ApplicationManager getInstance() {
		return instance;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void clearSharedPreferences(Context context) {
		SharedPreferences userDetails = context
			.getSharedPreferences("user", Context.MODE_PRIVATE);
		userDetails.edit().clear().commit();
	}
	
	public void setDip(Context context) {
		Resources r = context.getResources();
		this.dip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
	}
	
	public float getDip() {
		return dip;
	}
}
