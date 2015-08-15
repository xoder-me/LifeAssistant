package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.lifeassistant.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by alex.lee on 2015-07-20.
 */
public class PictureZoomActivity extends Activity {
	private ImageView ivShow;
	private ImageView ivBack;
	private PhotoViewAttacher mAttacher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_zoom);

		initViews();
	}

	private void initViews() {
		ivShow = (ImageView) findViewById(R.id.iv_show);
		ivBack = (ImageView) findViewById(R.id.iv_back);

		String imgUri = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/生活小助手/note/" + getIntent().getStringExtra("data") + ".jpg";
		ivShow.setImageBitmap(BitmapFactory.decodeFile(imgUri));
		mAttacher = new PhotoViewAttacher(ivShow);

		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
