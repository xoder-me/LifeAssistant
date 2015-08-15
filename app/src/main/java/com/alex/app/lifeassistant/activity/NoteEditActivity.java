package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.domain.juhe.NoteBean;
import com.alex.app.lifeassistant.utils.BitmapHelper;
import com.alex.app.lifeassistant.utils.ToastUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.DbException;

import org.joda.time.DateTime;

import java.io.File;

/**
 * Created by alex.lee on 2015-07-18.
 */
public class NoteEditActivity extends Activity implements View.OnClickListener {
	private EditText etNote;
	private ImageView ivBack;
	private ImageView ivMore;
	private ImageView ivCamera;
	private ImageView ivPicture;
	
	private String date;
	private String fileName;
	private File imgFile;
	private NoteBean note;
	private DbUtils dbUtils;
	private MyTextWatcher watcher;
	private BitmapUtils bitmapUtils;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				try {
					dbUtils.saveOrUpdate(note);
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_edit);
		
		initViews();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		etNote.removeTextChangedListener(watcher);
	}
	
	private void initViews() {
		watcher = new MyTextWatcher();
		bitmapUtils = BitmapHelper.getBitmapUtils(this);
		date = DateTime.now().toString("yyyyMMddhhmmss");
		
		ivBack = (ImageView) findViewById(R.id.back);
		ivMore = (ImageView) findViewById(R.id.more);
		ivCamera = (ImageView) findViewById(R.id.camera);
		ivPicture = (ImageView) findViewById(R.id.iv_picture);
		etNote = (EditText) findViewById(R.id.et_note);
		
		ivBack.setOnClickListener(this);
		ivMore.setOnClickListener(this);
		ivCamera.setOnClickListener(this);
		ivPicture.setOnClickListener(this);
		etNote.addTextChangedListener(watcher);
		
		dbUtils = DbUtils.create(this, "note.db");
		
		if (getIntent().getParcelableExtra("data") != null) {
			note = getIntent().getParcelableExtra("data");
			if (note.getContent().equals("未命名")) {
				etNote.setText("");
			} else {
				etNote.setText(note.getContent());
				etNote.setSelection(note.getContent().length());
			}

			if (!TextUtils.isEmpty(note.getPicture())) {
				ivPicture.setVisibility(View.VISIBLE);
				String imgUri = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/生活小助手/note/" + note.getPicture() + ".jpg";
				bitmapUtils.display(ivPicture, imgUri, new BitmapLoadCallBack<ImageView>() {
					@Override
					public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
						imageView.setBackgroundColor(Color.argb(0, 0, 0, 0));
						imageView.setImageBitmap(bitmap);
					}

					@Override
					public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
						super.onLoading(container, uri, config, total, current);
						container.setBackgroundColor(Color.GRAY);
					}

					@Override
					public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

					}
				});
			}
		} else {
			note = new NoteBean("", date);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.more:
				break;
			case R.id.camera:
				takePic();
				break;
			case R.id.iv_picture:
				zoomOut();
				break;
		}
	}

	/**
	 * 放大图片查看
	 */
	private void zoomOut() {
		Intent intent = new Intent(NoteEditActivity.this, PictureZoomActivity.class);
		intent.putExtra("data", note.getPicture());
		startActivity(intent);
	}
	
	private void takePic() {
		ivPicture.setVisibility(View.VISIBLE);
		fileName = DateTime.now().toString("yyyyMMddhhmmss");
		Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
		imgFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/生活小助手/note/" + fileName + ".jpg");
		mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
		startActivityForResult(mIntent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (imgFile != null) {
				bitmapUtils.display(ivPicture, imgFile.getAbsolutePath(), new BitmapLoadCallBack<ImageView>() {
					@Override
					public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
						ToastUtil.show(NoteEditActivity.this, "图片添加成功");
						imageView.setBackgroundColor(Color.argb(0, 0, 0, 0));
						imageView.setImageBitmap(bitmap);

						note.setPicture(fileName);
						if (TextUtils.isEmpty(etNote.getText())) {
							note.setContent("未命名");
						}
						mHandler.sendEmptyMessage(0);
					}

					@Override
					public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
						super.onLoading(container, uri, config, total, current);
						container.setBackgroundColor(Color.GRAY);
					}

					@Override
					public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

					}
				});
			}
		}
	}

	class MyTextWatcher implements TextWatcher {
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (TextUtils.isEmpty(s)) {
				note.setContent("未命名");
			} else {
				note.setContent(String.valueOf(s));
			}
			mHandler.sendEmptyMessage(0);
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	}
}
