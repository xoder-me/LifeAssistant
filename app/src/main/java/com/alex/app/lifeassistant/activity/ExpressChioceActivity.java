package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.CompanySortAdapter;
import com.alex.app.lifeassistant.comparator.PinyinComparator;
import com.alex.app.lifeassistant.constans.Constans;
import com.alex.app.lifeassistant.domain.juhe.express.CompanyBean;
import com.alex.app.lifeassistant.domain.juhe.express.CompanyPointBean;
import com.alex.app.lifeassistant.utils.CharacterParser;
import com.alex.app.lifeassistant.utils.ToastUtil;
import com.alex.app.lifeassistant.view.TitleBarView;
import com.alex.app.lifeassistant.widget.SideBar;
import com.alibaba.fastjson.JSON;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExpressChioceActivity extends Activity {
	private TextView dialog;
	private EditText filter;
	private SideBar sideBar;
	private TextView catalog;
	private FrameLayout front;
	private CompanySortAdapter adapter;
	private TitleBarView titleBar;
	private ListView sortListView;

	private Context mContext;
	private SweetAlertDialog loading;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private static List<CompanyPointBean> sourceDataList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_express_chioce);
		initViews();
	}

	private void initViews() {
		mContext = ExpressChioceActivity.this;

		loading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
		loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
		loading.setTitleText("数据加载中...");
		loading.show();

		pinyinComparator = new PinyinComparator();
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		sortListView = (ListView) findViewById(R.id.company_list);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar = (SideBar) findViewById(R.id.sidebar);
		catalog = (TextView) findViewById(R.id.tv_catalog);
		filter = (EditText) findViewById(R.id.et_chioce_com);
		front = (FrameLayout) findViewById(R.id.frame_front);
		titleBar = (TitleBarView) findViewById(R.id.title_bar);
		sideBar.setTextView(dialog);

		// 抓取数据
		companyQuery();

		titleBar.showLeftImg("选择快递公司", getResources().getDrawable(R.drawable.express_back), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				front.setVisibility(View.VISIBLE);
				catalog.setText(s);

				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));

				if (position != -1) {
					sortListView.setSelection(position);
				}
			}
		});
	}

	/**
	 * 为ListView填充数据
	 *
	 * @return 转换后的字符串集合
	 */
	private void modifyData() {
		for (int i = 0; i < sourceDataList.size(); i++) {
			sourceDataList.get(i).setSortLetters(getLetters(sourceDataList.get(i).getNo()));
		}

		// 根据a-z进行排序源数据
		Collections.sort(sourceDataList, pinyinComparator);
	}

	/**
	 * 获取首字母
	 *
	 * @param name 名字
	 *
	 * @return 首字母
	 */
	private String getLetters(String name) {
		//汉字转换成拼音
//		String pinyin = characterParser.getSelling(name);
//		String sortString = pinyin.substring(0, 1).toUpperCase();
		String sortString = name.substring(0, 1).toUpperCase();

		// 正则表达式，判断首字母是否是英文字母
		if (sortString.matches("[A-Z]")) {
			return sortString.toUpperCase();
		} else {
			return "#";
		}
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新 ListView
	 *
	 * @param filterStr 过滤字符串
	 */
	private void filterData(String filterStr) {
		List<CompanyPointBean> filterDateList = new ArrayList<>();
		
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = sourceDataList;
		} else {
			filterDateList.clear();
			for (CompanyPointBean point : sourceDataList) {
				String name = point.getCom();
				if (name.indexOf(filterStr) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
					filterDateList.add(point);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	/**
	 * 执行快递公司查询
	 */
	private void companyQuery() {
		// 初始化参数
		Parameters params = new Parameters();
		params.add("dtype", "json");
		JuheData.executeWithAPI(mContext, Constans.JUHE_EXPRESS_ID, Constans.JUHE_EXPRESS_COM, JuheData.POST, params,
				new DataCallBack() {

					@Override
					public void onSuccess(int statusCode, String responseString) {
						CompanyBean company = JSON.parseObject(responseString, CompanyBean.class);
						sourceDataList = company.getResult();
						// 初始化适配器
						adapter = new CompanySortAdapter(mContext, sourceDataList);
						modifyData();

						sortListView.setAdapter(adapter);
						// 设置子项目点击监听
						sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								//这里要利用adapter.getItem(position)来获取当前position所对应的对象
//								Toast.makeText(getApplication(), ((CompanyPointBean) adapter.getItem(position)).getCom(), Toast.LENGTH_SHORT).show();
								String com = ((CompanyPointBean) adapter.getItem(position)).getCom();
								Intent intent = new Intent("com.alex.app.lifeassistant.UPDATE_UI");
								intent.putExtra("com", com);
								sendBroadcast(intent);
								finish();
							}
						});
						// 设置滚动监听
						sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {

							}

							@Override
							public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
								front.setVisibility(View.VISIBLE);
								if (!sourceDataList.isEmpty() || sourceDataList != null) {
									catalog.setText(((CompanyPointBean) adapter.getItem(firstVisibleItem)).getSortLetters());
								}
							}
						});

						// 设置过滤器
						filter.addTextChangedListener(new TextWatcher() {
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {

							}

							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {
								//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
								filterData(s.toString());
							}

							@Override
							public void afterTextChanged(Editable s) {

							}
						});

						// 加载结束
						loading.dismiss();
					}

					@Override
					public void onFinish() {
//						System.out.println("数据 --> 结束");
						// 加载结束
						loading.dismiss();
					}

					@Override
					public void onFailure(int statusCode, String responseString, Throwable throwable) {
//						System.out.println("数据 --> 失败");
						// 加载结束
						loading.dismiss();
						ToastUtil.show(ExpressChioceActivity.this, throwable.getMessage());
					}
				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JuheData.cancelRequests(mContext);
	}
}
