package com.alex.app.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.utils.ToastUtil;

/**
 * Created by alex.lee on 2015-06-30.
 */
public class ExpressFragment extends Fragment implements View.OnClickListener {
	private LinearLayout llMine;
	private LinearLayout llQuery;
	private LinearLayout llSend;

	private TextView tvMine;
	private TextView tvQuery;
	private TextView tvSend;

	private ImageView ivMine;
	private ImageView ivQuery;
	private ImageView ivSend;

	private Fragment fgMine;
	private Fragment fgQuery;
	private Fragment fgSend;
	private Fragment fgCurrent;

	private int normalColor;
	private int selectedColor;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_express, container, false);
		initView(view);
		return view;
	}

	/**
	 * 初始化界面
	 *
	 * @param view 父视图
	 */
	private void initView(View view) {
		if (fgQuery == null) {
			fgQuery = new ExpressQueryFragment();
		}

		if (!fgQuery.isAdded()) {
			getFragmentManager().beginTransaction().add(R.id.frame_container_express, fgQuery).commit();
		}

		fgCurrent = fgQuery;

		normalColor = getResources().getColor(R.color.tab_text_normal);
		selectedColor = getResources().getColor(R.color.tab_text_selected);

		llMine = (LinearLayout) view.findViewById(R.id.ll_express_mine);
		llQuery = (LinearLayout) view.findViewById(R.id.ll_express_query);
		llSend = (LinearLayout) view.findViewById(R.id.ll_express_send);

		tvMine = (TextView) view.findViewById(R.id.tv_express_mine);
		tvQuery = (TextView) view.findViewById(R.id.tv_express_query);
		tvSend = (TextView) view.findViewById(R.id.tv_express_send);

		ivMine = (ImageView) view.findViewById(R.id.iv_express_mine);
		ivQuery = (ImageView) view.findViewById(R.id.iv_express_query);
		ivSend = (ImageView) view.findViewById(R.id.iv_express_send);

		llMine.setOnClickListener(this);
		llQuery.setOnClickListener(this);
		llSend.setOnClickListener(this);

		tvQuery.setTextColor(selectedColor);
		ivQuery.setImageResource(R.drawable.tab_expresss_search_selected);
	}

	@Override
	public void onClick(View v) {
		int resId = v.getId();
		setClickEffect(resId);

		switch (resId) {
			case R.id.ll_express_mine:// 我的
				if (fgMine == null) {
					fgMine = new ExpressMineFragment();
				}
				addOrShowFragmet(getFragmentManager().beginTransaction(), fgMine);
				ToastUtil.show(this.getActivity(), "我的快递");
				break;
			case R.id.ll_express_query: // 快递查询
				if (fgQuery == null) {
					fgQuery = new ExpressQueryFragment();
				}
				addOrShowFragmet(getFragmentManager().beginTransaction(), fgQuery);
				ToastUtil.show(this.getActivity(), "快递查询");
				break;
			case R.id.ll_express_send: // 寄送快递
				if (fgSend == null) {
					fgSend = new ExpressSendFragment();
				}
				addOrShowFragmet(getFragmentManager().beginTransaction(), fgSend);
				ToastUtil.show(this.getActivity(), "寄送快递");
				break;
		}
	}

	/**
	 * 根据点击的父视图id 改变子视图的点击效果
	 *
	 * @param resId 父视图id
	 */
	private void setClickEffect(int resId) {
		switch (resId) {
			case R.id.ll_express_mine:// 我的
				ivMine.setImageResource(R.drawable.tab_expresss_mine_selected);
				tvMine.setTextColor(selectedColor);

				ivQuery.setImageResource(R.drawable.tab_expresss_search_normal);
				tvQuery.setTextColor(normalColor);

				ivSend.setImageResource(R.drawable.tab_expresss_send_normal);
				tvSend.setTextColor(normalColor);
				break;
			case R.id.ll_express_query: // 快递查询
				ivMine.setImageResource(R.drawable.tab_expresss_mine_normal);
				tvMine.setTextColor(normalColor);

				ivQuery.setImageResource(R.drawable.tab_expresss_search_selected);
				tvQuery.setTextColor(selectedColor);

				ivSend.setImageResource(R.drawable.tab_expresss_send_normal);
				tvSend.setTextColor(normalColor);
				break;
			case R.id.ll_express_send: // 寄送快递
				ivMine.setImageResource(R.drawable.tab_expresss_mine_normal);
				tvMine.setTextColor(normalColor);

				ivQuery.setImageResource(R.drawable.tab_expresss_search_normal);
				tvQuery.setTextColor(normalColor);

				ivSend.setImageResource(R.drawable.tab_expresss_send_selected);
				tvSend.setTextColor(selectedColor);
				break;
		}
	}

	/**
	 * 添加或显示Fragment
	 *
	 * @param transaction fragment事务
	 * @param fragment    fragment
	 */
	private void addOrShowFragmet(FragmentTransaction transaction, Fragment fragment) {
		if (fragment == fgCurrent) {
			return;
		}

		if (!fragment.isAdded()) {
			transaction.hide(fgCurrent).add(R.id.frame_container_express, fragment, fragment.getClass().getSimpleName()).commit();
		} else {
			transaction.hide(fgCurrent).show(fragment).commit();
		}

		fgCurrent = fragment;
	}
}
