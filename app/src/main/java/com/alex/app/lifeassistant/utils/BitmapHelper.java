package com.alex.app.lifeassistant.utils;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	public static BitmapUtils getBitmapUtils(Context context) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(context,"/sdcard/生活小助手/cache/img");
		}

		return bitmapUtils;
	}
}
