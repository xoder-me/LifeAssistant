package com.alex.app.lifeassistant.domain.tuling.message;

import com.alex.app.lifeassistant.domain.tuling.TextBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class MenuBean extends TextBean {
	private List<MenuPointBean> list;

	public MenuBean() {
	}

	@Override
	public String toString() {
		return "MenuBean{" +
				"list=" + list +
				'}';
	}

	public List<MenuPointBean> getList() {
		return list;
	}

	public void setList(List<MenuPointBean> list) {
		this.list = list;
	}
}
