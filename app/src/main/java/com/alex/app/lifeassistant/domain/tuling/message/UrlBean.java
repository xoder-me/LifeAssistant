package com.alex.app.lifeassistant.domain.tuling.message;

import com.alex.app.lifeassistant.domain.tuling.TextBean;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class UrlBean extends TextBean {
	private String url;

	public UrlBean() {
	}

	@Override
	public String toString() {
		return "UrlBean{" +
				"url='" + url + '\'' +
				"} " + super.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
