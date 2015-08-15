package com.alex.app.lifeassistant.domain.juhe.express;

/**
 * Created by alex.lee on 2015-07-03.
 */
public class CompanyPointBean {
	private String no;
	private String com;
	//显示数据拼音的首字母
	private String sortLetters;

	@Override
	public String toString() {
		return "CompanyPointBean{" +
				"no='" + no + '\'' +
				", com='" + com + '\'' +
				", sortLetters='" + sortLetters + '\'' +
				'}';
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
