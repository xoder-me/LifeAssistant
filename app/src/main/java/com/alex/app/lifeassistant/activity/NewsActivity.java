package com.alex.app.lifeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.NewsFragmentAdapter;
import com.alex.app.lifeassistant.domain.apistore.news.NewsTabInfo;
import com.alex.app.lifeassistant.fragment.NewsDianyingFragment;
import com.alex.app.lifeassistant.fragment.NewsGuojiFragment;
import com.alex.app.lifeassistant.fragment.NewsGuoneiFragment;
import com.alex.app.lifeassistant.fragment.NewsShehuiFragment;
import com.alex.app.lifeassistant.view.TitleBarView;
import com.alex.app.lifeassistant.widget.TitleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class NewsActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
	public static final String EXTRA_TAB = "tab";
	private static final String TAG = NewsActivity.class.getSimpleName();

	protected int mLastTab = -1;
	protected int mCurrentTab = 0;
	// ViewPager
	protected ViewPager mPager;

	// 标题栏
	private TitleBarView titleBar;
	// 选项卡控件
	protected TitleIndicator mIndicator;
	// ViewPager 适配器
	protected NewsFragmentAdapter mAdapter = null;
	// 存放选项卡信息的列表
	protected ArrayList<NewsTabInfo> mTabs = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		initViews();
	}

	/**
	 * 在这里提供要显示的选项卡数据
	 */
	protected int supplyTabs(List<NewsTabInfo> tabs) {
		tabs.add(new NewsTabInfo(0, "国内要闻", NewsGuoneiFragment.class));
		tabs.add(new NewsTabInfo(1, "国际最新", NewsGuojiFragment.class));
		tabs.add(new NewsTabInfo(2, "电影频道", NewsDianyingFragment.class));
		tabs.add(new NewsTabInfo(3, "社会焦点", NewsShehuiFragment.class));
		return 0;
	}


	/**
	 * 初始化界面
	 */
	private void initViews() {
		titleBar = (TitleBarView) findViewById(R.id.title_bar);

		titleBar.showLeftImg("今日要闻", getResources().getDrawable(R.drawable.express_back), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mCurrentTab = supplyTabs(mTabs);
		Intent intent = getIntent();
		if (intent != null) {
			mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
		}
		Log.d(TAG, "mTabs.size() == " + mTabs.size() + ", cur: " + mCurrentTab);
		mAdapter = new NewsFragmentAdapter(this, getSupportFragmentManager(), mTabs);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);
		mPager.setOffscreenPageLimit(mTabs.size());
		// 设置viewpager内部页面之间的间距
		mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
		// 设置viewpager内部页面间距的drawable
		mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);

		mIndicator = (TitleIndicator) findViewById(R.id.pagerindicator);
		mIndicator.init(mCurrentTab, mTabs, mPager);

		mPager.setCurrentItem(mCurrentTab);
		mLastTab = mCurrentTab;
	}

	@Override
	protected void onDestroy() {
		mTabs.clear();
		mTabs = null;
		mAdapter.notifyDataSetChanged();
		mAdapter = null;
		mPager.setAdapter(null);
		mPager = null;
		mIndicator = null;

		super.onDestroy();
	}

	/**
	 * 添加一个选项卡
	 *
	 * @param tab 选项卡信息
	 */
	public void addTabInfo(NewsTabInfo tab) {
		mTabs.add(tab);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 从列表添加选项卡
	 *
	 * @param tabs 选项卡信息
	 */
	public void addTabInfos(ArrayList<NewsTabInfo> tabs) {
		mTabs.addAll(tabs);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mIndicator.onScrolled((mPager.getWidth() + mPager.getPageMargin()) * position + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		mIndicator.onSwitched(position);
		mCurrentTab = position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == ViewPager.SCROLL_STATE_IDLE) {
			mLastTab = mCurrentTab;
		}
	}

	protected NewsTabInfo getFragmentById(int tabId) {
		if (mTabs == null) return null;
		for (int index = 0, count = mTabs.size(); index < count; index++) {
			NewsTabInfo tab = mTabs.get(index);
			if (tab.getId() == tabId) {
				return tab;
			}
		}
		return null;
	}

	/**
	 * 跳转到任意选项卡
	 *
	 * @param tabId 选项卡下标
	 */
	public void navigate(int tabId) {
		for (int index = 0, count = mTabs.size(); index < count; index++) {
			if (mTabs.get(index).getId() == tabId) {
				mPager.setCurrentItem(index);
			}
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// for fix a known issue in support library
		// this solution form: https://code.google.com/p/android/issues/detail?id=19917
		outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
		super.onSaveInstanceState(outState);
	}

	public TitleIndicator getIndicator() {
		return mIndicator;
	}
}
