package com.alex.app.lifeassistant.domain.tuling.message;

import com.alex.app.lifeassistant.domain.tuling.TextBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class NewsBean extends TextBean {
	private List<NewsPointBean> list;

	public NewsBean() {
	}

	@Override
	public String toString() {
		return "NewsBean{" +
				"list=" + list +
				"} " + super.toString();
	}

	public List<NewsPointBean> getList() {
		return list;
	}

	public void setList(List<NewsPointBean> list) {
		this.list = list;
	}
}
