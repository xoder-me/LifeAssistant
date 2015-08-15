package com.alex.app.lifeassistant.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.fragment.JiShiFragment;
import com.alex.app.lifeassistant.view.TitleBarView;

/**
 * 老黄历 - 吉时查看
 * Created by alex.lee on 2015-07-07.
 */
public class JiShiActivity extends FragmentActivity {
	private TitleBarView titleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jishi);

		titleBar = (TitleBarView) findViewById(R.id.title_bar);
		titleBar.showLeftImg("吉时查看", getResources().getDrawable(R.drawable.express_back), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		titleBar.setBackColor(getResources().getColor(R.color.lhl_base));

		getSupportFragmentManager().beginTransaction().add(R.id.frame_container_jishi, new JiShiFragment(), JiShiFragment.class.getSimpleName()).commit();
	}
}