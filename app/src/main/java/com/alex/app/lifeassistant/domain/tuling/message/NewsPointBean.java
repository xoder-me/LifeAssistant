package com.alex.app.lifeassistant.domain.tuling.message;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class NewsPointBean {
	private String icon;
	private String source;
	private String article;
	private String detailurl;

	public NewsPointBean() {
	}

	@Override
	public String toString() {
		return "NewsPointBean{" +
				"icon='" + icon + '\'' +
				", source='" + source + '\'' +
				", article='" + article + '\'' +
				", detailurl='" + detailurl + '\'' +
				'}';
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}
}
