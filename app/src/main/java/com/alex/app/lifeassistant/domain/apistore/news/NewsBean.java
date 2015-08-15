package com.alex.app.lifeassistant.domain.apistore.news;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class NewsBean {
	private String desc;
	private String link;
	private String pubDate;
	private String source;
	private String title;
	private List<NewsImgBean> imageurls;

	public NewsBean() {
	}

	@Override
	public String toString() {
		return "NewsBean{" +
				"desc='" + desc + '\'' +
				", link='" + link + '\'' +
				", pubDate='" + pubDate + '\'' +
				", source='" + source + '\'' +
				", title='" + title + '\'' +
				", imageurls=" + imageurls +
				'}';
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<NewsImgBean> getImageurls() {
		return imageurls;
	}

	public void setImageurls(List<NewsImgBean> imageurls) {
		this.imageurls = imageurls;
	}
}
