package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.alex.app.lifeassistant.R;

/**
 * Created by alex.lee on 2015-07-14.
 */
public class WebViewActivity extends Activity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		initViews();
	}
	
	private void initViews() {
		String url = getIntent().getStringExtra("url");
		webView = (WebView) findViewById(R.id.web_view);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.requestFocus();
		webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.loadUrl(url);
	}
}
