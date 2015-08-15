package com.alex.app.lifeassistant.domain.juhe.huangli;

/**
 * 老黄历 - 吉时
 * Created by alex.lee on 2015-07-07.
 */
public class JiShiPointBean {
	private String yangli;
	private String hours;
	private String des;
	private String yi;
	private String ji;

	public JiShiPointBean() {
	}

	@Override
	public String toString() {
		return "JiShiPointBean{" +
				"yangli='" + yangli + '\'' +
				", hours='" + hours + '\'' +
				", des='" + des + '\'' +
				", yi='" + yi + '\'' +
				", ji='" + ji + '\'' +
				'}';
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getYangli() {
		return yangli;
	}

	public void setYangli(String yangli) {
		this.yangli = yangli;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getYi() {
		return yi;
	}

	public void setYi(String yi) {
		this.yi = yi;
	}

	public String getJi() {
		return ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}
}
