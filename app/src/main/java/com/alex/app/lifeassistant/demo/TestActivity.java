package com.alex.app.lifeassistant.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class TestActivity extends Activity {
	private DisplayMetrics dm;
	private LinearLayout layout, layoutLeft, layoutCenter, layoutRight;
	private static Handler messageHandler;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.HORIZONTAL); //水平
		layout.setGravity(Gravity.CENTER);

		layoutLeft = new LinearLayout(this);
		layoutLeft.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT));
		layoutLeft.setGravity(Gravity.RIGHT);

		layoutCenter = new LinearLayout(this);
		layoutCenter.setLayoutParams(new LayoutParams(dm.widthPixels, LayoutParams.WRAP_CONTENT));

		layoutRight = new LinearLayout(this);
		layoutRight.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT));
		layoutRight.setGravity(Gravity.LEFT);

		Button btA = new Button(this);
		btA.setText("按钮A");
		btA.setSingleLine(true);

		Button btB = new Button(this);
		btB.setText("按钮B");
		btB.setSingleLine(true);

		layoutLeft.addView(btA);
		layoutRight.addView(btB);

		layout.addView(layoutLeft);
		layout.addView(layoutCenter);
		layout.addView(layoutRight);

		setContentView(layout);

		Looper looper = Looper.myLooper();
		messageHandler = new MessageHandler(looper);

		new Thread(new Runnable() {
			@Override
			public void run() {
				int speed = 5;        //每次移动间隔毫秒，数字越大越慢
				int speedPx = 1;    //每次移动间隔像素，数字越大越快
				int loopCount = dm.widthPixels / 2;
				int i = 1;
				while (i < loopCount) {
					Message message = Message.obtain();
					message.what = 1;
					message.arg1 = i;
					messageHandler.sendMessage(message);

					i = i + speedPx;

					synchronized (this) {
						try {
							wait(speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				i = 0;
				while (i < 10) {
					Message message = Message.obtain();
					message.what = 1;
					message.arg1 = loopCount - i;
					messageHandler.sendMessage(message);

					i = i + speedPx;

					synchronized (this) {
						try {
							wait(speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

	}

	class MessageHandler extends Handler {
		public MessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					layoutLeft.setLayoutParams(new LayoutParams(msg.arg1, LayoutParams.WRAP_CONTENT));
					layoutRight.setLayoutParams(new LayoutParams(msg.arg1, LayoutParams.WRAP_CONTENT));
					layoutCenter.setLayoutParams(new LayoutParams(dm.widthPixels - msg.arg1 * 2, LayoutParams.WRAP_CONTENT));
					break;
			}
		}
	}
}