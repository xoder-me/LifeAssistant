package com.alex.app.lifeassistant.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.NewsAdapter;
import com.alex.app.lifeassistant.constans.Constans;
import com.alex.app.lifeassistant.domain.apistore.news.NewsBean;
import com.alex.app.lifeassistant.domain.apistore.news.NewsPageBean;
import com.alex.app.lifeassistant.utils.BitmapHelper;
import com.alex.app.lifeassistant.utils.ToastUtil;
import com.alex.app.lifeassistant.widget.XListView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class NewsBaseFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
	private Context mContext;
	private BitmapUtils bitmapUtils;

	private XListView lvNews;
	private NewsPageBean newsPage;
	private List<NewsBean> newsList;
	private NewsAdapter newsAdapter;
	private SweetAlertDialog loading;

	private static int page = 1;
	private boolean isFirst = true;

	private String NEWS_CHANNEL;

	public NewsBaseFragment(String NEWS_CHANNEL) {
		this.NEWS_CHANNEL = NEWS_CHANNEL;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, null);
		initVies(view);
		return view;
	}

	private void initVies(View view) {
		newsList = new ArrayList<>(20);

		if (isFirst) {
			loading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
			loading.getProgressHelper().setBarColor(getResources().getColor(R.color.base_color));
			loading.setCanceledOnTouchOutside(false);
			loading.setTitleText("数据加载中...");
			loading.show();
		}

		bitmapUtils = BitmapHelper.getBitmapUtils(mContext);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.express_not_found);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtils.configMemoryCacheEnabled(true);
		bitmapUtils.configDiskCacheEnabled(true);
		bitmapUtils.configDefaultAutoRotation(true);

		lvNews = (XListView) view.findViewById(R.id.lv_news);
		lvNews.setPullLoadEnable(true);
		lvNews.setAutoLoadEnable(true);
		lvNews.setPullRefreshEnable(true);
		lvNews.setXListViewListener(this);
		lvNews.setOnItemClickListener(this);
		lvNews.setRefreshTime(DateTime.now().toString("MM-dd HH:mm"));
		lvNews.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
		queryNews(NEWS_CHANNEL, page, 0);
	}

	private void queryNews(String channel, int page, final int flag) {
		RequestParams params = new RequestParams("utf-8");
		params.addBodyParameter("page", page + "");
		params.addBodyParameter("channelId", channel);
		params.addHeader("apikey", Constans.API_STORE_KEY);

		HttpUtils http = new HttpUtils(3000);
		http.send(HttpRequest.HttpMethod.POST, Constans.API_NEWS_URL, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				System.out.println("数据 --> " + responseInfo.result);

				JSONObject object = JSON.parseObject(JSON.parseObject(responseInfo.result).getString("showapi_res_body"));
				newsPage = JSON.parseObject(object.getString("pagebean"), NewsPageBean.class);
				if (newsPage != null) {
					if (newsPage.getAllPages() == newsPage.getCurrentPage()) {
						ToastUtil.show(mContext, "客官, 只有这么多了!");
					} else {
						if (flag == 0) {
							newsList = newsPage.getContentlist();
							newsAdapter = new NewsAdapter(mContext, bitmapUtils, newsList);
							lvNews.setAdapter(newsAdapter);
						} else {
							if (flag == 1) {
								newsList.addAll(0, newsPage.getContentlist());
							} else if (flag == 2) {
								newsList.addAll(newsList.size(), newsPage.getContentlist());
							}
							newsAdapter.setNewsList(newsList);
							newsAdapter.notifyDataSetChanged();
						}
					}
				} else {
					ToastUtil.show(mContext,JSON.parseObject(responseInfo.result).getString("showapi_res_error"));
				}

				onLoad();
				isFirst = false;
				loading.dismiss();
			}

			@Override
			public void onFailure(HttpException e, String s) {
				ToastUtil.show(mContext, "加载失败");

				onLoad();
				isFirst = false;
				loading.dismiss();
			}
		});
	}

	@Override
	public void onRefresh() {
		if (newsList == null) {
			queryNews(NEWS_CHANNEL, page, 0);
		} else {
			queryNews(NEWS_CHANNEL, ++page, 1);
		}
	}

	@Override
	public void onLoadMore() {
		queryNews(NEWS_CHANNEL, ++page, 2);
	}

	private void onLoad() {
		lvNews.stopRefresh();
		lvNews.stopLoadMore();
		lvNews.setRefreshTime(DateTime.now().toString("MM-dd HH:mm"));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Uri uri = Uri.parse(newsList.get(position - 1).getLink());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}