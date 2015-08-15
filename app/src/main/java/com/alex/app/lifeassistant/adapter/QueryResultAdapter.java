package com.alex.app.lifeassistant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressInfoBean;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressPointBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-01.
 */
public class QueryResultAdapter extends BaseAdapter {
	private Context context;
	private List<ExpressPointBean> points;
	private ExpressInfoBean expressInfo;

	public QueryResultAdapter(Context context, ExpressInfoBean expressInfo) {
		this.context = context;
		this.expressInfo = expressInfo;
		this.points = expressInfo.getList();
	}

	@Override
	public int getCount() {
		return points.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView != null && convertView instanceof RelativeLayout) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_query_result, null);
			holder.ivUp = (ImageView) convertView.findViewById(R.id.iv_time_line_up);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tv_point_date);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_point_time);
			holder.tvInfo = (TextView) convertView.findViewById(R.id.tv_point_info);

			convertView.setTag(holder);
		}

		ExpressPointBean point = points.get(getCount() - position - 1);

		if (point.getRemark().contains("签收")) {
//		if (position == 0) {
			holder.ivUp.setImageResource(R.drawable.express_time_line_ok);
//			holder.tvDate.setTextColor(Color.RED);
//			holder.tvTime.setTextColor(Color.RED);
//			holder.tvInfo.setTextColor(Color.RED);
		} else {
			holder.ivUp.setImageResource(R.drawable.express_time_line_up);
		}

		holder.tvDate.setText(point.getDatetime().split(" ")[0]);
		holder.tvTime.setText(point.getDatetime().split(" ")[1]);
		holder.tvInfo.setText(point.getRemark());

		return convertView;
	}

	static class ViewHolder {
		ImageView ivUp;
		TextView tvDate;
		TextView tvTime;
		TextView tvInfo;
	}
}
