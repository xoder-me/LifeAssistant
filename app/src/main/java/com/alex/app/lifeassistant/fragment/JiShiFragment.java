package com.alex.app.lifeassistant.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.constans.Constans;
import com.alex.app.lifeassistant.domain.juhe.huangli.JiShiBean;
import com.alex.app.lifeassistant.domain.juhe.huangli.JiShiPointBean;
import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.joda.time.DateTime;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alex.lee on 2015-07-07.
 */
public class JiShiFragment extends Fragment implements View.OnClickListener {
	private TextView tvDate, tvTime, tvShiyi, tvJihui, tvMiaoshu;

	private Context mContext;
	private SweetAlertDialog loading;
	private List<JiShiPointBean> jiShiPoints;
	private CheckedTextView zi, chou, yin, mou, chen, si, wu, wei, shen, you, xu, hai;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_jishi, null);
		initViews(view);
		jiShiQuery(DateTime.now().toString("yyyy-MM-dd"));
		return view;
	}
	
	private void initViews(View view) {
		loading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
		loading.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.lhl_base));
		loading.setTitleText("努力查询中...");
		loading.setCanceledOnTouchOutside(false);

		tvDate = (TextView) view.findViewById(R.id.tv_date);
		tvTime = (TextView) view.findViewById(R.id.tv_time);
		tvShiyi = (TextView) view.findViewById(R.id.tv_shiyi);
		tvJihui = (TextView) view.findViewById(R.id.tv_jihui);
		tvMiaoshu = (TextView) view.findViewById(R.id.tv_miaoshu);

		zi = (CheckedTextView) view.findViewById(R.id.ctv_zi);
		chou = (CheckedTextView) view.findViewById(R.id.ctv_chou);
		yin = (CheckedTextView) view.findViewById(R.id.ctv_yin);
		mou = (CheckedTextView) view.findViewById(R.id.ctv_mou);
		chen = (CheckedTextView) view.findViewById(R.id.ctv_chen);
		si = (CheckedTextView) view.findViewById(R.id.ctv_si);
		wu = (CheckedTextView) view.findViewById(R.id.ctv_wu);
		wei = (CheckedTextView) view.findViewById(R.id.ctv_wei);
		shen = (CheckedTextView) view.findViewById(R.id.ctv_shen);
		you = (CheckedTextView) view.findViewById(R.id.ctv_you);
		xu = (CheckedTextView) view.findViewById(R.id.ctv_xu);
		hai = (CheckedTextView) view.findViewById(R.id.ctv_hai);

		zi.setOnClickListener(this);
		chou.setOnClickListener(this);
		yin.setOnClickListener(this);
		mou.setOnClickListener(this);
		chen.setOnClickListener(this);
		si.setOnClickListener(this);
		wu.setOnClickListener(this);
		wei.setOnClickListener(this);
		shen.setOnClickListener(this);
		you.setOnClickListener(this);
		xu.setOnClickListener(this);
		hai.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		changeStatus();
		switch (v.getId()) {
			case R.id.ctv_zi:
				zi.setChecked(true);
				updateUI(jiShiPoints.get(11));
				break;
			case R.id.ctv_chou:
				updateUI(jiShiPoints.get(0));
				chou.setChecked(true);
				break;
			case R.id.ctv_yin:
				updateUI(jiShiPoints.get(1));
				yin.setChecked(true);
				break;
			case R.id.ctv_mou:
				updateUI(jiShiPoints.get(2));
				mou.setChecked(true);
				break;
			case R.id.ctv_chen:
				updateUI(jiShiPoints.get(3));
				chen.setChecked(true);
				break;
			case R.id.ctv_si:
				updateUI(jiShiPoints.get(4));
				si.setChecked(true);
				break;
			case R.id.ctv_wu:
				updateUI(jiShiPoints.get(5));
				wu.setChecked(true);
				break;
			case R.id.ctv_wei:
				updateUI(jiShiPoints.get(6));
				wei.setChecked(true);
				break;
			case R.id.ctv_shen:
				updateUI(jiShiPoints.get(7));
				shen.setChecked(true);
				break;
			case R.id.ctv_you:
				updateUI(jiShiPoints.get(8));
				you.setChecked(true);
				break;
			case R.id.ctv_xu:
				updateUI(jiShiPoints.get(9));
				xu.setChecked(true);
				break;
			case R.id.ctv_hai:
				updateUI(jiShiPoints.get(10));
				hai.setChecked(true);
				break;
		}
	}

	private void jiShiQuery(String date) {
		HttpUtils http = new HttpUtils(3000);
		RequestParams params = new RequestParams("utf-8");
		params.addBodyParameter("date", date);
		params.addBodyParameter("key", "6bd4e673441c3f1a827d2737690cc236");


		http.send(HttpMethod.POST, Constans.JUHE_HUANGLI_TIME, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				JiShiBean jiShi = JSON.parseObject(responseInfo.result, JiShiBean.class);
				jiShiPoints = jiShi.getResult();
				updateUI(jiShiPoints.get(0));
				loading.dismiss();
			}

			@Override
			public void onStart() {
				loading.show();
			}

			@Override
			public void onFailure(HttpException e, String s) {

			}
		});
	}

	/**
	 * 更新UI
	 *
	 * @param jiShiPoint 吉时的数据节点
	 */
	private void updateUI(JiShiPointBean jiShiPoint) {
		String[] hours = jiShiPoint.getHours().split("-");
		int hour_1 = Integer.valueOf(hours[0]);
		int hour_2 = Integer.valueOf(hours[1]);
		tvDate.setText(DateTime.parse(jiShiPoint.getYangli()).toString("yyyy年MM月dd日"));
		tvTime.setText(String.format("%02d : %02d", hour_1, hour_2));
		tvShiyi.setText(jiShiPoint.getYi());
		tvJihui.setText(jiShiPoint.getJi());
		tvMiaoshu.setText(jiShiPoint.getDes());
	}

	/**
	 * 改变按钮的状态, 一律设置为未选中
	 */
	private void changeStatus() {
		zi.setChecked(false);
		chou.setChecked(false);
		yin.setChecked(false);
		mou.setChecked(false);
		chen.setChecked(false);
		si.setChecked(false);
		wu.setChecked(false);
		wei.setChecked(false);
		shen.setChecked(false);
		you.setChecked(false);
		xu.setChecked(false);
		hai.setChecked(false);
	}
}
