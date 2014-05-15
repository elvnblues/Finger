package com.elvn.finger.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

public class NotifitionActivity extends Activity {
	private static ArrayList<Activity> allActivitys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addActivitys(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeActivity(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
	}

	public void addActivitys(Activity activity) {
		if (activity != null && !activity.isFinishing()) {
			if (allActivitys == null) {
				allActivitys = new ArrayList<Activity>();
			}
			allActivitys.add(activity);
		}
	}

	public void removeActivity(Activity activity) {
		if (allActivitys != null && allActivitys.size() > 0) {
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
			allActivitys.remove(activity);
		}
	}

	/**
	 * Close All Activity
	 * @param context
	 */
	public void closeAllActivity(Context context) {
		if (allActivitys != null && allActivitys.size() > 0) {
			for (Activity activity : allActivitys) {
				if (activity != null && !activity.isFinishing()) {
					activity.finish();
				}
			}
			allActivitys.clear();
		}
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		manager.restartPackage(context.getPackageName());
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
