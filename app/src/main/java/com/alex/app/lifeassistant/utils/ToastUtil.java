package com.alex.app.lifeassistant.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alex.lee on 2015-06-30.
 */
public class ToastUtil {
	public static void show(Context ctx, String message) {
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}
}
