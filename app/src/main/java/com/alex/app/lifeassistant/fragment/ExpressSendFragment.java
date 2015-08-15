package com.alex.app.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.app.lifeassistant.R;

/**
 * Created by alex.lee on 2015-07-01.
 */
public class ExpressSendFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_express_send, container, false);
		return view;
	}
}
