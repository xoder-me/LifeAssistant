package com.alex.app.lifeassistant.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alex.app.lifeassistant.R;

public class MorePopWindow extends PopupWindow {
	private View conentView;
	private LinearLayout llGrid;
	private LinearLayout llSetting;

	public MorePopWindow(final Activity context, View.OnClickListener setOnClickListener, View.OnClickListener gridOnClickListener) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = inflater.inflate(R.layout.more_popup_dialog, null);
		// 设置SelectPicPopupWindow的View
		setContentView(conentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		setFocusable(true);
//		setOutsideTouchable(true);
		// 刷新状态
		update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(Color.parseColor("#33000000"));
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		setBackgroundDrawable(dw);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		setAnimationStyle(R.style.AnimationPreview);

		llGrid = (LinearLayout) conentView.findViewById(R.id.ll_grid);
		llSetting = (LinearLayout) conentView.findViewById(R.id.ll_setting);

		conentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		llGrid.setOnClickListener(gridOnClickListener);
		llSetting.setOnClickListener(setOnClickListener);
	}

	public void showPopupWindow(View parent) {
		if (!isShowing()) {
			showAsDropDown(parent, parent.getLayoutParams().width / 2, 0);
		} else {
			dismiss();
		}
	}
}
