package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.view.TitleBarView;
import com.alex.app.lifeassistant.view.WaterWaveView;

/**
 * Created by alex.lee on 2015-07-10.
 */
public class FlowManagerActivity extends Activity {
	private TitleBarView titleBarView;
	private WaterWaveView mWaterWaveView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flow_manager);

		titleBarView = (TitleBarView) findViewById(R.id.title_bar);
		titleBarView.setBackColor(getResources().getColor(R.color.base_color));
		titleBarView.showLeftImg("流量管理", getResources().getDrawable(R.drawable.express_back), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mWaterWaveView = (WaterWaveView) findViewById(R.id.wave_view);
		mWaterWaveView.setFlowNum("1200M");
		mWaterWaveView.setAmplitude(30f);
		mWaterWaveView.startWave();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		mWaterWaveView.stopWave();
//		mWaterWaveView = null;
	}
}
