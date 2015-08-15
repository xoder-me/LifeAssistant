package com.alex.app.lifeassistant.domain.juhe.huangli;

/**
 * Created by alex.lee on 2015-07-06.
 */
public class HuangLiBean {
	private String id;
	private String yangli;
	private String yinli;
	private String wuxing;
	private String chongsha;
	private String baiji;
	private String jishen;
	private String yi;
	private String xiongshen;
	private String ji;

	public HuangLiBean() {
	}

	@Override
	public String toString() {
		return "HuangLiBean{" +
				"id='" + id + '\'' +
				", yangli='" + yangli + '\'' +
				", yinli1='" + yinli + '\'' +
				", wuxing='" + wuxing + '\'' +
				", chongsha='" + chongsha + '\'' +
				", baiji='" + baiji + '\'' +
				", jishen='" + jishen + '\'' +
				", yi='" + yi + '\'' +
				", xiongshen='" + xiongshen + '\'' +
				", ji='" + ji + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYangli() {
		return yangli;
	}

	public void setYangli(String yangli) {
		this.yangli = yangli;
	}

	public String getYinli() {
		return yinli;
	}

	public void setYinli(String yinli) {
		this.yinli = yinli;
	}

	public String getWuxing() {
		return wuxing;
	}

	public void setWuxing(String wuxing) {
		this.wuxing = wuxing;
	}

	public String getChongsha() {
		return chongsha;
	}

	public void setChongsha(String chongsha) {
		this.chongsha = chongsha;
	}

	public String getBaiji() {
		return baiji;
	}

	public void setBaiji(String baiji) {
		this.baiji = baiji;
	}

	public String getJishen() {
		return jishen;
	}

	public void setJishen(String jishen) {
		this.jishen = jishen;
	}

	public String getYi() {
		return yi;
	}

	public void setYi(String yi) {
		this.yi = yi;
	}

	public String getXiongshen() {
		return xiongshen;
	}

	public void setXiongshen(String xiongshen) {
		this.xiongshen = xiongshen;
	}

	public String getJi() {
		return ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}
}
