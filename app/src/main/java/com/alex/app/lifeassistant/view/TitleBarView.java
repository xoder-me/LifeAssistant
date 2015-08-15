package com.alex.app.lifeassistant.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;

/**
 * Created by alex.lee on 2015-06-15.
 */
public class TitleBarView extends RelativeLayout {
	private View vTitleBar;
	private TextView tvRight;
	private TextView tvCenter;
	private ImageView ivLeft;
	private ImageView ivRight;
	private ImageView ivCenter;
	private RelativeLayout rlTitleBar;
	private LayoutInflater layoutInflater;

	public TitleBarView(Context context) {
		super(context);
		initTitleBarView(context);
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTitleBarView(context);
	}

	public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initTitleBarView(context);
	}

	private void initTitleBarView(Context context) {
		layoutInflater = LayoutInflater.from(context);
		vTitleBar = layoutInflater.inflate(R.layout.self_title_bar, this);
		tvCenter = (TextView) vTitleBar.findViewById(R.id.tv_center);
		tvRight = (TextView) vTitleBar.findViewById(R.id.tv_right);
		ivLeft = (ImageView) vTitleBar.findViewById(R.id.iv_left);
		ivRight = (ImageView) vTitleBar.findViewById(R.id.iv_right);
		ivCenter = (ImageView) vTitleBar.findViewById(R.id.iv_center);
		rlTitleBar = (RelativeLayout) vTitleBar.findViewById(R.id.rl_title_bar);
	}

	/**
	 * 设置标题栏的背景色彩
	 *
	 * @param color 颜色
	 */
	public void setBackColor(int color) {
		rlTitleBar.setBackgroundColor(color);
	}

	/**
	 * 设置标题
	 *
	 * @param title 需要显示的标题文字
	 */
	public void setTitle(String title) {
		tvCenter.setText(title);
		tvCenter.setVisibility(VISIBLE);
	}

	/**
	 * 仅显示标题
	 *
	 * @param title 需要显示的标题文字
	 */
	public void showCenter(String title) {
		setTitle(title);
	}

	/**
	 * 显示标题 - 左图
	 *
	 * @param title        标题
	 * @param leftImg      左图
	 * @param leftImgClick 左图点击事件
	 */
	public void showLeftImg(String title, Drawable leftImg, OnClickListener leftImgClick) {
		showCenter(title);

		ivLeft.setVisibility(VISIBLE);
		ivLeft.setImageDrawable(leftImg);
		ivLeft.setOnClickListener(leftImgClick);
	}

	/**
	 * 显示标题 - 右图
	 *
	 * @param title         标题
	 * @param rightImg      右图
	 * @param rightImgClick 右图点击事件
	 */
	public void showRightImg(String title, Drawable rightImg, OnClickListener rightImgClick) {
		showCenter(title);

		ivRight.setVisibility(VISIBLE);
		ivRight.setImageDrawable(rightImg);
		ivRight.setOnClickListener(rightImgClick);
	}

	/**
	 * 显示标题 - 左图 - 右文
	 *
	 * @param title         标题
	 * @param rightStr      右文
	 * @param leftImg       左图
	 * @param leftImgClick  左图点击事件
	 * @param rightStrClick 右文点击事件
	 */
	public void showLeftImgAndRightStr(String title, String rightStr, Drawable leftImg, OnClickListener leftImgClick, OnClickListener rightStrClick) {
		showCenter(title);
		showLeftImg(title, leftImg, leftImgClick);

		tvRight.setText(rightStr);
		tvRight.setVisibility(VISIBLE);
		tvRight.setOnClickListener(rightStrClick);
	}

	/**
	 * 显示标题 - 左图 - 右图
	 *
	 * @param title         标题
	 * @param leftImg       左图
	 * @param rightImg      右图
	 * @param leftImgClick  左图点击事件
	 * @param rightImgClick 右图点击事件
	 */
	public void showLeftAndRightImg(String title, Drawable leftImg, Drawable rightImg, OnClickListener leftImgClick, OnClickListener rightImgClick) {
		showCenter(title);
		showLeftImg(title, leftImg, leftImgClick);
		showRightImg(title, rightImg, rightImgClick);
	}

	/**
	 * 显示中间图标
	 *
	 * @param isShowed true 显示
	 */
	public void showCentenrImg(boolean isShowed) {
		if (isShowed) {
			ivCenter.setVisibility(VISIBLE);
		} else {
			ivCenter.setVisibility(GONE);
		}
	}

	/**
	 * 设置中间图标点击事件
	 *
	 * @param centerImgClick
	 */
	public void setCenterImgClick(OnClickListener centerImgClick) {
		ivCenter.setOnClickListener(centerImgClick);
	}
}
