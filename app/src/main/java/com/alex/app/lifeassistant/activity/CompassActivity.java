package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.view.GpsStatusView;
import com.alex.app.lifeassistant.view.TitleBarView;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class CompassActivity extends Activity implements SensorEventListener {
	private TitleBarView mTitleBarView;
	private GpsStatusView mGpsStatusView;

	private Sensor mSensor;
	private SensorManager mSensorManager;
	private static final int iSensorRate = 0;
	private long mOrLast = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);

		initView();
	}

	private void initView() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mGpsStatusView = (GpsStatusView) findViewById(R.id.gps_status);

		mTitleBarView.showLeftImg("实际上是指北针", getResources().getDrawable(R.drawable.express_back), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();

			}
		});

		mSensorManager.registerListener(this, mSensor, iSensorRate);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		boolean isRateElapsed = false;

		switch (event.sensor.getType()) {
			case Sensor.TYPE_ORIENTATION:
				isRateElapsed = (event.timestamp / 1000) - mOrLast >= iSensorRate;
				break;

		}


		if (isRateElapsed) {
			switch (event.sensor.getType()) {
				case Sensor.TYPE_ORIENTATION:
					mGpsStatusView.setYaw(event.values[0]);
					break;
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}
