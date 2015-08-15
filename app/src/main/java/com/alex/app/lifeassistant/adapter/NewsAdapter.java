package com.alex.app.lifeassistant.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.domain.apistore.news.NewsBean;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.List;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class NewsAdapter extends BaseAdapter {
	private Context mContext;
	private List<NewsBean> newsList;
	private BitmapUtils bitmapUtils;

	private final ColorDrawable TRANSPARENT_DRAWABLE;

	public NewsAdapter(Context mContext, BitmapUtils bitmapUtils, List<NewsBean> newsList) {
		this.mContext = mContext;
		this.bitmapUtils = bitmapUtils;
		this.newsList = newsList;
		TRANSPARENT_DRAWABLE = new ColorDrawable(mContext.getResources().getColor(R.color.float_transparent));
	}

	public void setNewsList(List<NewsBean> newsList) {
		this.newsList = newsList;
	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		return newsList.get(position);
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
			convertView = View.inflate(mContext, R.layout.item_news_one_img, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		NewsBean news = newsList.get(position);
		File img = bitmapUtils.getBitmapFileFromDiskCache(news.getLink());
		if (img!=null && img.exists()) {
			holder.ivNewsImg.setImageURI(Uri.fromFile(img));
		} else if (news.getImageurls() != null && news.getImageurls().size() > 0) {
			bitmapUtils.display(holder.ivNewsImg, news.getImageurls().get(0).getUrl(), new CustomBitmapLoadCallBack(holder));
		} else {
			holder.ivNewsImg.setImageResource(R.drawable.express_not_found);
		}

		holder.tvNewsTitle.setText(news.getTitle());
		holder.tvNewsDesc.setText(news.getDesc());
		holder.tvNewsSource.setText(news.getSource());
		holder.tvNewsTime.setText(news.getPubDate());
		holder.pbLoading.setVisibility(View.GONE);

		return convertView;
	}

	static class ViewHolder {
		@ViewInject(R.id.iv_news_img)
		ImageView ivNewsImg;
		@ViewInject(R.id.tv_news_title)
		TextView tvNewsTitle;
		@ViewInject(R.id.tv_news_desc)
		TextView tvNewsDesc;
		@ViewInject(R.id.tv_news_source)
		TextView tvNewsSource;
		@ViewInject(R.id.tv_news_time)
		TextView tvNewsTime;
		@ViewInject(R.id.pb_loading)
		ProgressBar pbLoading;
	}

	public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
		private final ViewHolder holder;

		public CustomBitmapLoadCallBack(ViewHolder holder) {
			this.holder = holder;
		}

		@Override
		public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
//			holder.pbLoading.setVisibility(View.VISIBLE);
		}

		@Override
		public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
			fadeInDisplay(container, bitmap);
//			holder.pbLoading.setVisibility(View.GONE);
		}

		@Override
		public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
//			holder.pbLoading.setVisibility(View.GONE);
		}

	}

	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
		final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{TRANSPARENT_DRAWABLE, new BitmapDrawable(imageView.getResources(), bitmap)});
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(500);
	}
}
