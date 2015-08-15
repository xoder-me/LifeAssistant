package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.QueryResultAdapter;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressInfoBean;
import com.alex.app.lifeassistant.view.TitleBarView;

/**
 * Created by alex.lee on 2015-07-01.
 */
public class ExpressQueryResultActivity extends Activity {
	private ListView lvQueryResult;
	private TitleBarView titleBarView;
	private TextView tvCom;
	private TextView tvCode;
//	private TextView ivlogo;

	private ExpressInfoBean expressInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_express_query_result);

		expressInfo = (ExpressInfoBean) getIntent().getSerializableExtra("express");
		titleBarView = (TitleBarView) findViewById(R.id.title_bar);
		lvQueryResult = (ListView) findViewById(R.id.lv_query_result);
		tvCom = (TextView) findViewById(R.id.tv_express_com);
		tvCode = (TextView) findViewById(R.id.tv_express_code);

		titleBarView.showLeftImg("查询结果", getResources().getDrawable(R.drawable.express_back), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		lvQueryResult.setAdapter(new QueryResultAdapter(this, expressInfo));

		tvCode.setText(expressInfo.getNo());
		tvCom.setText(expressInfo.getCompany());
	}
}
