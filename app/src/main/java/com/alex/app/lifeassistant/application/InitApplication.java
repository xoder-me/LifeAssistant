package com.alex.app.lifeassistant.application;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by alex.lee on 2015-06-30.
 */
public class InitApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		JodaTimeAndroid.init(getApplicationContext());
		JuheSDKInitializer.initialize(getApplicationContext());
	}
}
