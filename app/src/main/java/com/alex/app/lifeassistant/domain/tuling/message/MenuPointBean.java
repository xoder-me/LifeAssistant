package com.alex.app.lifeassistant.domain.tuling.message;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class MenuPointBean {
	private String name;
	private String info;
	private String detailurl;
	private String icon;

	public MenuPointBean() {
	}

	@Override
	public String toString() {
		return "MenuPointBean{" +
				"name='" + name + '\'' +
				", info='" + info + '\'' +
				", detailurl='" + detailurl + '\'' +
				", icon='" + icon + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
