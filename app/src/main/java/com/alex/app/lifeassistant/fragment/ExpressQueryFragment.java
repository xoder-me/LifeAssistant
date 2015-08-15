package com.alex.app.lifeassistant.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.activity.ExpressChioceActivity;
import com.alex.app.lifeassistant.activity.ExpressQueryResultActivity;
import com.alex.app.lifeassistant.constans.Constans;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressBean;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressInfoBean;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressPointBean;
import com.alex.app.lifeassistant.utils.ToastUtil;
import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alex.lee on 2015-07-01.
 */
public class ExpressQueryFragment extends Fragment {
	private EditText etCode;// 快递单号
	private EditText etCompany; // 快递公司
	private Button btnQuery;// 点击查询

	private ImageView ivChioce;
	private ImageView ivCodeClose;
	private ImageView ivCompanyClose;

	private TextView tvCode;
	private TextView tvCompany;
	private TextView tvResult;
	private DbUtils db;

	private Context mContext;
	private FragmentManager fragmentManager;
	private UpdateUIReceiver updateUIReceiver;

	private ExpressBean express;
	private Map<String, String> company;

	private SweetAlertDialog dialog;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_express_query, container, false);
		initData();
		initView(view);

		return view;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		company = new HashMap<>();
		company.put("顺丰", "sf");
		company.put("申通", "st");
		company.put("圆通", "yt");
		company.put("韵达", "yd");
		company.put("天天", "tt");
		company.put("EMS", "ems");
		company.put("中通", "zto");
		company.put("汇通", "ht");
		company.put("全峰", "qf");
	}
	
	/**
	 * 初始化界面
	 *
	 * @param view 父视图
	 */
	private void initView(View view) {
		mContext = getActivity();
		fragmentManager = getFragmentManager();
		updateUIReceiver = new UpdateUIReceiver();
		IntentFilter filter = new IntentFilter("com.alex.app.lifeassistant.UPDATE_UI");
		mContext.registerReceiver(updateUIReceiver, filter);

		db = DbUtils.create(mContext, "express.db");
		dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
		dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
		dialog.setTitleText("努力查询中...");
		dialog.setCanceledOnTouchOutside(true);

		etCode = (EditText) view.findViewById(R.id.et_code);
		etCompany = (EditText) view.findViewById(R.id.et_company);
		btnQuery = (Button) view.findViewById(R.id.btn_query);
		tvResult = (TextView) view.findViewById(R.id.tv_result);

		ivChioce = (ImageView) view.findViewById(R.id.iv_chioce);
		ivCodeClose = (ImageView) view.findViewById(R.id.iv_code_close);
		ivCompanyClose = (ImageView) view.findViewById(R.id.iv_company_close);

		tvCode = (TextView) view.findViewById(R.id.tv_code);
		tvCompany = (TextView) view.findViewById(R.id.tv_company);

		ivChioce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(mContext, ExpressChioceActivity.class), 0x123);
//				startActivity(new Intent(mContext, ExpressChioceActivity.class));
			}
		});

		ivCodeClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				etCode.setText("");
				tvCode.setVisibility(View.INVISIBLE);
				ivCodeClose.setVisibility(View.GONE);
			}
		});

		ivCompanyClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				etCompany.setText("");
				tvCompany.setVisibility(View.INVISIBLE);
				ivCompanyClose.setVisibility(View.GONE);
			}
		});

		etCode.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changeButtonBack();

				if (TextUtils.isEmpty(s)) {
					tvCode.setVisibility(View.INVISIBLE);
					ivCodeClose.setVisibility(View.GONE);
				} else {
					tvCode.setVisibility(View.VISIBLE);
					ivCodeClose.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				changeButtonBack();
			}
		});

		etCompany.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				changeButtonBack();

				if (TextUtils.isEmpty(s)) {
					tvCompany.setVisibility(View.INVISIBLE);
					ivCompanyClose.setVisibility(View.GONE);
				} else {
					tvCompany.setVisibility(View.VISIBLE);
					ivCompanyClose.setVisibility(View.VISIBLE);
				}
			}


			@Override
			public void afterTextChanged(Editable s) {
				changeButtonBack();
			}
		});

		btnQuery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String code = etCode.getText().toString();
				String companyName = etCompany.getText().toString();

				if (TextUtils.isEmpty(code)) {
					ToastUtil.show(mContext, "快递单号不得为空");
				} else if (TextUtils.isEmpty(companyName)) {
					ToastUtil.show(mContext, "请选择快递公司");
				} else {
					dialog.show();
					expressQuery(code, company.get(companyName));
				}
			}
		});
	}

	/**
	 * 改变查询按钮的背景
	 */
	private void changeButtonBack() {
		if (!TextUtils.isEmpty(etCode.getText()) || !TextUtils.isEmpty(etCompany.getText())) {
			btnQuery.setBackgroundResource(R.drawable.exress_query_btn_pressed);
			btnQuery.setEnabled(true);
		} else {
			btnQuery.setBackgroundResource(R.drawable.exress_query_btn_normal);
			btnQuery.setEnabled(false);
		}
	}

	/**
	 * 执行快递查询
	 *
	 * @param code    快递单号
	 * @param company 快递公司
	 */
	private void expressQuery(String code, String company) {
		// 初始化参数
		Parameters params = new Parameters();
		params.add("com", company);
		params.add("no", code);

		/**
		 * 请求的方法 参数: 第一个参数 当前请求的context;
		 * 				   第二个参数 接口id;
		 * 				   第三个参数 接口请求的url;
		 * 				   第四个参数 接口请求的方式;
		 * 				   第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型;
		 * 				   第六个参数请求的回调方法,com.thinkland.sdk.android.DataCallBack;
		 *
		 */
		JuheData.executeWithAPI(mContext, Constans.JUHE_EXPRESS_ID, Constans.JUHE_EXPRESS_URL, JuheData.POST, params,
				new DataCallBack() {
					@Override
					public void onSuccess(int statusCode, String responseString) {
						tvResult.setText("");
						express = JSON.parseObject(responseString, ExpressBean.class);
						if (express.getResultcode().equals("200")) {
							Intent intent = new Intent(mContext, ExpressQueryResultActivity.class);
							Bundle data = new Bundle();
							data.putSerializable("express", express.getResult());
							intent.putExtras(data);

							// 保存数据
							saveData();

							ExpressMineFragment mineFragment = (ExpressMineFragment) fragmentManager.findFragmentByTag(ExpressMineFragment.class.getSimpleName());
							if (mineFragment != null) {
								mineFragment.flushData();
							}

							startActivity(intent);
						} else {
							tvResult.setText(express.getReason());
						}
					}

					@Override
					public void onFinish() {
						dialog.dismiss();
					}

					@Override
					public void onFailure(int statusCode, String responseString, Throwable throwable) {
						tvResult.setText(throwable.getMessage());
					}
				});
	}

	/**
	 * 存入数据
	 */
	private void saveData() {
		ExpressInfoBean expressInfo = express.getResult();
		List<ExpressPointBean> points = expressInfo.getList();

		try {
			db.save(expressInfo);
			for (ExpressPointBean point : points) {
				point.expressInfo = expressInfo;
				db.save(point);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		JuheData.cancelRequests(mContext);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mContext.unregisterReceiver(updateUIReceiver);
	}

	/**
	 * 一个用来接收广播更新 UI的接收器
	 */
	class UpdateUIReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			etCompany.setText(intent.getStringExtra("com"));
		}
	}
}
