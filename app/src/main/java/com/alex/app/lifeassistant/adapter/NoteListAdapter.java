package com.alex.app.lifeassistant.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.domain.juhe.NoteBean;
import com.alex.app.lifeassistant.utils.TimeUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex.lee on 2015-07-16.
 */
public class NoteListAdapter extends BaseAdapter {
	private Context context;
	private List<NoteBean> items;
	private Map<Integer, Status> itemStatus;

	public NoteListAdapter(Context context, List<NoteBean> items) {
		this.context = context;
		this.items = items;
		itemStatus = new HashMap<>();
		initData();
	}
	
	private void initData() {
		for (int i = 0; i < items.size(); i++) {
			Status status = new Status(false, View.GONE);
			itemStatus.put(i, status);
		}
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public NoteBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_note_list, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		NoteBean note = items.get(position);
		Status status = itemStatus.get(position);
		holder.cb.setChecked(status.isSelected);
		holder.cb.setVisibility(status.isVisibled);
		holder.time.setText(TimeUtil.modifyDate(note.getDate()));
		holder.tv.setText(note.getContent());
		if (TextUtils.isEmpty(note.getPicture())) {
			holder.ivPic.setVisibility(View.GONE);
		}

		return convertView;
	}

	public class ViewHolder {
		@ViewInject(R.id.tv)
		public TextView tv;
		@ViewInject(R.id.time)
		public TextView time;
		@ViewInject(R.id.cb)
		public CheckBox cb;
		@ViewInject(R.id.iv_pic)
		public ImageView ivPic;
	}

	public Map<Integer, Status> getItemStatus() {
		return itemStatus;
	}

	public void setItems(List<NoteBean> items) {
		this.items = items;
	}

	public class Status {
		int isVisibled;
		boolean isSelected;

		public Status(boolean isSelected, int isVisibled) {
			this.isVisibled = isVisibled;
			this.isSelected = isSelected;
		}

		public int getIsVisibled() {
			return isVisibled;
		}

		public void setIsVisibled(int isVisibled) {
			this.isVisibled = isVisibled;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setIsSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}
	}
}
