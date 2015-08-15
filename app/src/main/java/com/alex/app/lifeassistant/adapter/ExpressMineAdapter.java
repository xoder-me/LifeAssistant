package com.alex.app.lifeassistant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressInfoBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-02.
 */
public class ExpressMineAdapter extends BaseAdapter {
	private Context context;
	private List<ExpressInfoBean> expressInfoes;

	public ExpressMineAdapter(Context context, List<ExpressInfoBean> expressInfoes) {
		this.context = context;
		this.expressInfoes = expressInfoes;
	}

	public void setExpressInfoes(List<ExpressInfoBean> expressInfoes) {
		this.expressInfoes = expressInfoes;
	}

	@Override
	public int getCount() {
		return expressInfoes.size();
	}

	@Override
	public Object getItem(int position) {
		return expressInfoes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView != null && convertView instanceof LinearLayout) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_mine, null);
			holder.tvCom = (TextView) convertView.findViewById(R.id.tv_express_com);
			holder.tvCode = (TextView) convertView.findViewById(R.id.tv_express_code);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.tv_express_desc);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_express_time);
		}


		ExpressInfoBean info = expressInfoes.get(position);
		holder.tvCom.setText(info.getCompany());
		holder.tvCode.setText(info.getNo());
		holder.tvDesc.setText(info.getList().get(info.getList().size() - 1).getRemark());
		holder.tvTime.setText(info.getList().get(info.getList().size() - 1).getDatetime());

		return convertView;
	}

	static class ViewHolder {
		TextView tvCom;
		TextView tvCode;
		TextView tvDesc;
		TextView tvTime;

	}
}
