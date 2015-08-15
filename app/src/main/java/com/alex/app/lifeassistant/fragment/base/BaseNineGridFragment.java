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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;

/**
 * Created by alex.lee on 2015-06-30.
 */
public class BaseNineGridFragment extends Fragment implements View.OnClickListener {
	private Context mContext;

	private TextView[] tvs;
	private ImageView[] ivs;
	private LinearLayout[] lls;

	private int[] llIds = {
			R.id.ll_one, R.id.ll_two, R.id.ll_three,
			R.id.ll_four, R.id.ll_five, R.id.ll_six,
			R.id.ll_seven, R.id.ll_eight, R.id.ll_nine
	};

	private int[] ivIds = {
			R.id.iv_one, R.id.iv_two, R.id.iv_three,
			R.id.iv_four, R.id.iv_five, R.id.iv_six,
			R.id.iv_seven, R.id.iv_eight, R.id.iv_nine
	};

	private int[] tvIds = {
			R.id.tv_one, R.id.tv_two, R.id.tv_three,
			R.id.tv_four, R.id.tv_five, R.id.tv_six,
			R.id.tv_seven, R.id.tv_eight, R.id.tv_nine
	};

	private Class[] clazz;
	private int[] enterImgs;
	private String[] enterTitles;

	public BaseNineGridFragment(int[] enterImgs, String[] enterTitles, Class[] clazz) {
		this.enterImgs = enterImgs;
		this.enterTitles = enterTitles;
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
		View view = inflater.inflate(R.layout.fragment_entertainment, null);
		initViews(view);

		return view;
	}

	private void initViews(View view) {
		tvs = new TextView[9];
		ivs = new ImageView[9];
		lls = new LinearLayout[9];

		for (int i = 0; i < tvs.length; i++) {
			tvs[i] = (TextView) view.findViewById(tvIds[i]);
			ivs[i] = (ImageView) view.findViewById(ivIds[i]);
			lls[i] = (LinearLayout) view.findViewById(llIds[i]);

			tvs[i].setText(enterTitles[i]);
			ivs[i].setImageResource(enterImgs[i]);
			lls[i].setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < clazz.length; i++) {
			if (v.getId() == llIds[i]) {
				if (clazz[i] != null) {
					startActivity(new Intent(mContext, clazz[i]));
				}
			}
		}
	}
}
