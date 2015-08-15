package com.alex.app.lifeassistant.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.domain.apistore.news.NewsTabInfo;

import java.util.List;


/**
 * 这是个选项卡式的控件，会随着viewpager的滑动而滑动
 */
public class TitleIndicator extends LinearLayout implements View.OnClickListener, OnFocusChangeListener {
	private boolean DEBUG = false;
	private static final String TAG = TitleIndicator.class.getSimpleName();
	private static final float FOOTER_LINE_HEIGHT = 4.0f;
	private static final int FOOTER_COLOR = 0xFFFFC445;
	private static final float FOOTER_TRIANGLE_HEIGHT = 10;
	private Context mContext;
	private int mCurrentScroll = 0;

	// 选项卡列表
	private List<NewsTabInfo> mTabs;
	// 选项卡所依赖的viewpager
	private ViewPager mViewPager;
	// 选项卡普通状态下的字体颜色
	private ColorStateList mTextColor;

	// 普通状态和选中状态下的字体大小
	private float mTextSizeNormal;
	private float mTextSizeSelected;

	private Path mPath = new Path();
	private Paint mPaintFooterLine;
	private Paint mPaintFooterTriangle;

	// 滚动条的高度
	private float mFooterLineHeight;
	private float mFooterTriangleHeight;
	// 当前选项卡的下标，从0开始

	private int mSelectedTab = 0;

	private final int BSSEEID = 0xffff00;
	private boolean mChangeOnClick = true;
	private int mCurrID = 0;

	// 单个选项卡的宽度
	private int mPerItemWidth = 0;
	// 表示选项卡总共有几个
	private int mTotal = 0;

	private LayoutInflater mInflater;

	public TitleIndicator(Context context) {
		super(context);
		initDraw(FOOTER_LINE_HEIGHT, FOOTER_COLOR);
	}

	public TitleIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setOnFocusChangeListener(this);

		mContext = context;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleIndicator);
		mTextColor = a.getColorStateList(R.styleable.TitleIndicator_textColor);
		mTextSizeNormal = a.getDimension(R.styleable.TitleIndicator_textSizeNormal, 0);
		mTextSizeSelected = a.getDimension(R.styleable.TitleIndicator_textSizeSelected, mTextSizeNormal);
		mFooterLineHeight = a.getDimension(R.styleable.TitleIndicator_footerLineHeight, FOOTER_LINE_HEIGHT);
		mFooterTriangleHeight = a.getDimension(R.styleable.TitleIndicator_footerTriangleHeight, FOOTER_TRIANGLE_HEIGHT);

		initDraw(mFooterLineHeight, a.getColor(R.styleable.TitleIndicator_footerColor, FOOTER_COLOR));
		a.recycle();
	}

	private void initDraw(float footerLineHeight, int footerColor) {
		mPaintFooterLine = new Paint();
		mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaintFooterLine.setStrokeWidth(footerLineHeight);
		mPaintFooterLine.setColor(footerColor);

		mPaintFooterTriangle = new Paint();
		mPaintFooterTriangle.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaintFooterTriangle.setColor(footerColor);
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*
	 * 核心函数: 选项卡是用canvas画出来的。所有的invalidate方法均会触发onDraw
	 * 大意是这样的：当页面滚动的时候，会有一个滚动距离，然后onDraw被触发后， 就会在新位置重新画上滚动条（其实就是画线）
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 计算本次滑动的距离
		float scroll_x;
		if (mTotal != 0) {
			mPerItemWidth = getWidth() / mTotal;
			int tabID = mSelectedTab;
			scroll_x = (mCurrentScroll - ((tabID) * (getWidth() + mViewPager.getPageMargin()))) / mTotal;
		} else {
			mPerItemWidth = getWidth();
			scroll_x = mCurrentScroll;
		}

		// 下面就是如何画线了
		Path path = mPath;
		path.rewind();
		float offset = 0;
		float left_x = mSelectedTab * mPerItemWidth + offset + scroll_x;
		float right_x = (mSelectedTab + 1) * mPerItemWidth - offset + scroll_x;
		float top_y = getHeight() - mFooterLineHeight - mFooterTriangleHeight;
		float bottom_y = getHeight() - mFooterLineHeight;
		path.moveTo(left_x, top_y + 1f);
		path.lineTo(right_x, top_y + 1f);
		path.lineTo(right_x, bottom_y + 1f);
		path.lineTo(left_x, bottom_y + 1f);
		path.close();

		canvas.drawPath(path, mPaintFooterTriangle);
	}

	/**
	 * 获取指定下标的选项卡的标题
	 */
	private String getTitle(int pos) {
		// Set the default title
		String title = "title " + pos;
		// If the TitleProvider exist
		if (mTabs != null && mTabs.size() > pos) {
			title = mTabs.get(pos).getName();
		}
		return title;
	}

	/**
	 * 获取指定下标的选项卡的图标资源id（如果设置了图标的话）
	 */
	private int getIcon(int pos) {
		int ret = 0;
		if (mTabs != null && mTabs.size() > pos) {
			ret = mTabs.get(pos).getIcon();
		}
		return ret;
	}

	private boolean hasTips(int pos) {
		boolean ret = false;
		if (mTabs != null && mTabs.size() > pos) {
			ret = mTabs.get(pos).hasTips;
		}
		return ret;
	}

	// 当页面滚动的时候，重新绘制滚动条
	public void onScrolled(int h) {
		mCurrentScroll = h;
		invalidate();
	}

	// 当页面切换的时候，重新绘制滚动条
	public synchronized void onSwitched(int position) {
		if (mSelectedTab == position) {
			return;
		}
		setCurrentTab(position);
		invalidate();
	}

	// 初始化选项卡
	public void init(int startPos, List<NewsTabInfo> tabs, ViewPager mViewPager) {
		removeAllViews();

		this.mViewPager = mViewPager;
		this.mTabs = tabs;
		this.mTotal = tabs.size();

		for (int i = 0; i < mTotal; i++) {
			add(i, getTitle(i), getIcon(i), hasTips(i));
		}

		setCurrentTab(startPos);
		invalidate();
	}

	public void updateChildTips(int postion, boolean showTips) {
		final ImageView tips = (ImageView) getChildAt(postion).findViewById(R.id.tab_title_tips);

		if (showTips) {
			tips.setVisibility(View.VISIBLE);
		} else {
			tips.setVisibility(View.GONE);
		}
	}

	protected void add(int index, String label, int icon, boolean hasTips) {
		View tabIndicator;
		if (index < 2) {
			tabIndicator = mInflater.inflate(R.layout.title_flow_indicator, this, false);
		} else {
			tabIndicator = mInflater.inflate(R.layout.title_flow_indicator_v2, this, false);
		}

		final TextView tv = (TextView) tabIndicator.findViewById(R.id.tab_title);
		final ImageView tips = (ImageView) tabIndicator.findViewById(R.id.tab_title_tips);

		if (mTextColor != null) {
			tv.setTextColor(mTextColor);
		}
		if (mTextSizeNormal > 0) {
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeNormal);
		}
		tv.setText(label);

		if (icon > 0) {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
		}

		if (hasTips) {
			tips.setVisibility(View.VISIBLE);
		} else {
			tips.setVisibility(View.GONE);
		}

		tabIndicator.setId(BSSEEID + (mCurrID++));
		tabIndicator.setOnClickListener(this);
		LayoutParams lP = (LayoutParams) tabIndicator.getLayoutParams();
		lP.gravity = Gravity.CENTER_VERTICAL;
		addView(tabIndicator);
	}

	public void setDisplayedPage(int index) {
		mSelectedTab = index;
	}

	public void setChangeOnClick(boolean changeOnClick) {
		mChangeOnClick = changeOnClick;
	}

	public boolean getChangeOnClick() {
		return mChangeOnClick;
	}

	@Override
	public void onClick(View v) {
		int position = v.getId() - BSSEEID;
		setCurrentTab(position);
	}

	public int getTabCount() {
		int children = getChildCount();
		return children;
	}

	// 设置当前选项卡
	public synchronized void setCurrentTab(int index) {
		if (index < 0 || index >= getTabCount()) {
			return;
		}
		View oldTab = getChildAt(mSelectedTab);
		oldTab.setSelected(false);
		setTabTextSize(oldTab, false);

		mSelectedTab = index;
		View newTab = getChildAt(mSelectedTab);
		newTab.setSelected(true);
		setTabTextSize(newTab, true);
		newTab.findViewById(R.id.tab_title_tips).setVisibility(View.GONE);

		mViewPager.setCurrentItem(mSelectedTab);
		invalidate();
	}

	private void setTabTextSize(View tab, boolean selected) {
		TextView tv = (TextView) tab.findViewById(R.id.tab_title);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, selected ? mTextSizeSelected : mTextSizeNormal);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (v == this && hasFocus && getTabCount() > 0) {
			getChildAt(mSelectedTab).requestFocus();
			return;
		}

		if (hasFocus) {
			int i = 0;
			int numTabs = getTabCount();
			while (i < numTabs) {
				if (getChildAt(i) == v) {
					setCurrentTab(i);
					break;
				}
				i++;
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mCurrentScroll == 0 && mSelectedTab != 0) {
			mCurrentScroll = (getWidth() + mViewPager.getPageMargin()) * mSelectedTab;
		}
	}
}