package com.alex.app.lifeassistant.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.view.GridLayoutView;
import com.alex.app.lifeassistant.view.GridLayoutView.OnItemClickListener;

/**
 * Created by alex.lee on 2015-06-30.
 */
public class _NineGridBaseFragment extends Fragment {
	private Context mContext;
	private GridLayoutView gridView;

	private int[] imgs;
	private String[] titles;
	private Class[] clazz;

	public _NineGridBaseFragment(int[] imgs, String[] titles, Class[] clazz) {
		this.imgs = imgs;
		this.titles = titles;
		this.clazz = clazz;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_nine_grid, container, false);
		// 初始化内容
		initView(view);

		return view;
	}

	/**
	 * 初始化内容
	 *
	 * @param view 视图
	 */
	private void initView(View view) {
		mContext = getActivity();

		gridView = (GridLayoutView) view.findViewById(R.id.grid_view);
		gridView.setAdapter(new GridLayoutView.GridLayoutAdapter() {
			@Override
			public int getCount() {
				return titles.length;
			}

			@Override
			public View getView(int index) {
				View view = View.inflate(mContext, R.layout.item_grid, null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv);
				TextView tv = (TextView) view.findViewById(R.id.tv);
				iv.setImageResource(imgs[index]);
				tv.setText(titles[index]);
				return view;
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View view, int index) {
				for (int i = 0; i < clazz.length; i++) {
					if (clazz[i] != null && index == i) {
						startNewActivity(clazz[index]);
					}
				}
			}
		});
	}

	/**
	 * 开启新的一个activity
	 *
	 * @param clazz activity
	 */
	private void startNewActivity(Class clazz) {
		startActivity(new Intent(mContext, clazz));
	}
}
