package com.alex.app.lifeassistant.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.alex.app.lifeassistant.domain.apistore.news.NewsTabInfo;

import java.util.ArrayList;

/**
 * Created by alex.lee on 2015-07-07.
 */
public class NewsFragmentAdapter extends FragmentPagerAdapter {
	ArrayList<NewsTabInfo> tabs = null;
	Context context = null;

	public NewsFragmentAdapter(Context context, FragmentManager fm, ArrayList<NewsTabInfo> tabs) {
		super(fm);
		this.tabs = tabs;
		this.context = context;
	}

	@Override
	public Fragment getItem(int pos) {
		Fragment fragment = null;
		if (tabs != null && pos < tabs.size()) {
			NewsTabInfo tab = tabs.get(pos);
			if (tab == null) return null;
			fragment = tab.createFragment();
		}
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		if (tabs != null && tabs.size() > 0) return tabs.size();
		return 0;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		NewsTabInfo tab = tabs.get(position);
		Fragment fragment = (Fragment) super.instantiateItem(container, position);
		tab.fragment = fragment;
		return fragment;
	}
}
