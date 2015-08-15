package com.alex.app.lifeassistant.domain.apistore.news;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class NewsImgBean {
	private int width;
	private int height;
	private String url;

	public NewsImgBean() {
	}

	@Override
	public String toString() {
		return "NewsImgBean{" +
				"width=" + width +
				", height=" + height +
				", url='" + url + '\'' +
				'}';
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
