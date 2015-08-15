package com.alex.app.lifeassistant.domain.apistore.news;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-09.
 */
public class NewsPageBean {
	private int allNum;
	private int allPages;
	private int currentPage;
	private int maxResult;
	private List<NewsBean> contentlist;

	public NewsPageBean() {
	}

	@Override
	public String toString() {
		return "NewsPageBean{" +
				"allNum=" + allNum +
				", allPages=" + allPages +
				", currentPage=" + currentPage +
				", maxResult=" + maxResult +
				", contentlist=" + contentlist +
				'}';
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public List<NewsBean> getContentlist() {
		return contentlist;
	}

	public void setContentlist(List<NewsBean> contentlist) {
		this.contentlist = contentlist;
	}
}
