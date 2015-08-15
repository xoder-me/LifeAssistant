package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ToggleButton;

import com.alex.app.lifeassistant.R;

/**
 * Created by alex.lee on 2015-07-08.
 */
public class FlashLightActivity extends Activity {
	private ToggleButton tbLock;
	private ToggleButton tbLight;
	private Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_light);

		initViews();
	}
	
	private void initViews() {
		tbLock = (ToggleButton) findViewById(R.id.tb_lock);
		tbLight = (ToggleButton) findViewById(R.id.tb_light);

		tbLock.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 保持屏幕常亮
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				tbLight.setChecked(tbLock.isChecked());

				if (tbLock.isChecked()) {
					// 小哥儿 开个灯
					openLight();
				} else {
					closeLight();
				}
			}
		});
	}

	/**
	 * 关闭手电筒
	 */
	private void closeLight() {
		if (null != mCamera) {
			Camera.Parameters parameters = mCamera.getParameters();
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

			mCamera.setParameters(parameters);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
	
	/**
	 * 打开手电筒
	 */
	private void openLight() {
		if (mCamera == null) {
			mCamera = Camera.open();
		}

		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}
}
