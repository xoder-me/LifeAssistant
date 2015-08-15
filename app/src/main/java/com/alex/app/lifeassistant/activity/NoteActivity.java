package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.NoteListAdapter;
import com.alex.app.lifeassistant.domain.juhe.NoteBean;
import com.alex.app.lifeassistant.popup.MorePopWindow;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alex.lee on 2015-07-16.
 */
public class NoteActivity extends Activity {
	private boolean longPressFlag;
	private List<NoteBean> original;
	private List<NoteBean> delItems;

	private ListView lv;
	private ImageView more;
	private TextView showNum;
	private RelativeLayout actionOne;
	private RelativeLayout actionTwo;
	private NoteListAdapter mAdapter;

	private MorePopWindow morePopWindow;

	private DbUtils dbUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();

		initViews();
	}

	private void initViews() {
		longPressFlag = false;
		delItems = new ArrayList<>();
		original = new ArrayList<>();

		dbUtils = DbUtils.create(this, "note.db");
		dbUtils.configAllowTransaction(true);
		dbUtils.configDebug(true);

		lv = (ListView) findViewById(R.id.lv);
		more = (ImageView) findViewById(R.id.more);
		showNum = (TextView) findViewById(R.id.show_num);
		actionOne = (RelativeLayout) findViewById(R.id.action_bar_one);
		actionTwo = (RelativeLayout) findViewById(R.id.action_bar_two);

		fillData();
		mAdapter = new NoteListAdapter(NoteActivity.this, original);
		lv.setAdapter(mAdapter);

		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				changeChexkBoxStatus(View.VISIBLE, true);
				longPressFlag = true;
				clickResponse(view, position);
				return true;
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (longPressFlag) {
					clickResponse(view, position);
				} else {
					Intent intent = new Intent(NoteActivity.this, NoteEditActivity.class);
					Bundle data = new Bundle();
					data.putParcelable("data", original.get(position));
					intent.putExtras(data);
					startActivity(intent);
				}
			}
		});

		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				morePopWindow.showPopupWindow(more);
			}
		});

		morePopWindow = new MorePopWindow(this,
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						morePopWindow.dismiss();
						Toast.makeText(NoteActivity.this, "变为表格形式", Toast.LENGTH_SHORT).show();
					}
				},
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						morePopWindow.dismiss();
						Toast.makeText(NoteActivity.this, "进入设置界面", Toast.LENGTH_SHORT).show();
					}
				}
		);
	}

	/**
	 * 点击的响应事件 - 重构
	 *
	 * @param view     父视图
	 * @param position 在listView里的位置
	 */
	private void clickResponse(View view, int position) {
		NoteListAdapter.ViewHolder holder = (NoteListAdapter.ViewHolder) view.getTag();
		holder.cb.toggle();
		NoteListAdapter.Status status = mAdapter.getItemStatus().get(position);
		status.setIsSelected(holder.cb.isChecked());
		mAdapter.getItemStatus().put(position, status);

		if (holder.cb.isChecked()) {
			delItems.add(original.get(position));
		} else {
			delItems.remove(original.get(position));
		}

		showNum.setText("已选中" + delItems.size() + "个");
	}

	/**
	 * 更改侧边CheckBox的状态
	 *
	 * @param visible 是否可见
	 * @param isFirst 是否第一次出现
	 */
	private void changeChexkBoxStatus(int visible, boolean isFirst) {
		delItems.clear();
		actionOne.setVisibility(View.VISIBLE);
		actionTwo.setVisibility(View.GONE);
		showNum.setVisibility(View.VISIBLE);

		Map<Integer, NoteListAdapter.Status> itemsStatus = mAdapter.getItemStatus();
		for (Integer index : itemsStatus.keySet()) {
			NoteListAdapter.Status status = itemsStatus.get(index);
			status.setIsVisibled(visible);

			// 如果不是第一次出现的话, 就不用隐藏, 等待顶部Title操作时再隐藏
			if (!isFirst) {
				status.setIsSelected(false);
			}
			itemsStatus.put(index, status);
		}
		mAdapter.notifyDataSetChanged();
	}

	private void fillData() {
		try {
			original = dbUtils.findAll(NoteBean.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按钮点击事件
	 *
	 * @param view 视图
	 */
	public void delete(View view) {
		try {
			File delFile;
			String imgUri = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/生活小助手/note/";
			for (NoteBean delItem : delItems) {
				dbUtils.delete(delItem);
				delFile = new File(imgUri + delItem.getPicture() + ".jpg");
				delFile.delete();
			}
			original.removeAll(delItems);
			mAdapter.setItems(original);
			changeChexkBoxStatus(View.VISIBLE, false);
			back(view);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 点击返回上一操作
	 *
	 * @param view 视图
	 */
	public void back(View view) {
		finish();
	}

	/**
	 * 点击返回上一操作
	 *
	 * @param view 视图
	 */
	public void done(View view) {
		changeChexkBoxStatus(View.INVISIBLE, false);
		actionOne.setVisibility(View.GONE);
		actionTwo.setVisibility(View.VISIBLE);
		showNum.setVisibility(View.GONE);
		longPressFlag = false;
	}

	/**
	 * 添加一个条数据
	 *
	 * @param view 视图
	 */
	public void add(View view) {
		startActivity(new Intent(NoteActivity.this, NoteEditActivity.class));
	}
}
