package com.alex.app.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.ExpressMineAdapter;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressInfoBean;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressPointBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alex.lee on 2015-07-01.
 */
public class ExpressMineFragment extends Fragment {
	private DbUtils db;
	private ListView lvMine;
	private TextView tvNotFound;
	private ImageView ivNotFound;
	private EditText etMineQuery;
	private TextWatcher textWatcher;
	private SweetAlertDialog dialog;
	private ExpressMineAdapter mineAdapter;
	private List<ExpressInfoBean> expressInfos;
	
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_express_mine, container, false);
		try {
			initView(view);
		} catch (DbException e) {
			e.printStackTrace();
		}

		return view;
	}

	private void initView(View view) throws DbException {
		dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelText("取消");
		dialog.setConfirmText("确定");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		lvMine = (ListView) view.findViewById(R.id.lv_express_mine);
		etMineQuery = (EditText) view.findViewById(R.id.et_mine_query);
		tvNotFound = (TextView) view.findViewById(R.id.tv_mine_not_found);
		ivNotFound = (ImageView) view.findViewById(R.id.iv_mine_not_found);

		// 初始化数据库
		initDB();
		expressInfos = db.findAll(ExpressInfoBean.class);

		if (expressInfos == null || expressInfos.isEmpty()) {
			lvMine.setVisibility(View.GONE);
			ivNotFound.setVisibility(View.VISIBLE);
			tvNotFound.setVisibility(View.VISIBLE);
		} else {
			lvMine.setVisibility(View.VISIBLE);
			ivNotFound.setVisibility(View.GONE);
			tvNotFound.setVisibility(View.GONE);

			mineAdapter = new ExpressMineAdapter(getActivity(), expressInfos);
			lvMine.setAdapter(mineAdapter);
			lvMine.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					final ExpressInfoBean infoBean = expressInfos.get(position);
					dialog.setTitleText("确定删除?");
					dialog.setContentText(infoBean.getCompany() + " - " + infoBean.getNo());
					dialog.show();

					dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							// 删除数据
							deleteData(infoBean);
							expressInfos.remove(infoBean);
							mineAdapter.setExpressInfoes(expressInfos);
							mineAdapter.notifyDataSetChanged();
							dialog.dismiss();
						}
					});

					return true;
				}
			});

			textWatcher = new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					String params = s.toString();
					WhereBuilder builder = WhereBuilder.b();
					builder.or("company", "like", "%" + params + "%");
					builder.or("no", "like", "%" + params + "%");
					try {
						mineAdapter.setExpressInfoes(db.<ExpressInfoBean>findAll(Selector.from(ExpressInfoBean.class).where(builder)));
						mineAdapter.notifyDataSetChanged();
					} catch (DbException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			};

			etMineQuery.addTextChangedListener(textWatcher);
		}
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		db = DbUtils.create(getActivity(), "express.db");
		db.configAllowTransaction(true);
		db.configDebug(true);
	}
	
	/**
	 * 删除数据
	 *
	 * @param expressInfo 快递信息
	 */
	private void deleteData(ExpressInfoBean expressInfo) {
		try {
			for (ExpressPointBean p : expressInfo.getList()) {
				db.delete(p);
			}
			db.delete(expressInfo);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		etMineQuery.removeTextChangedListener(textWatcher);
		db.close();
	}

	/**
	 * 刷新数据
	 */
	public void flushData() {
		try {
//			if (db == null || mineAdapter == null) {
//				initView(getView());
//			} else {
			mineAdapter.setExpressInfoes(db.findAll(ExpressInfoBean.class));
			mineAdapter.notifyDataSetChanged();
//			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
