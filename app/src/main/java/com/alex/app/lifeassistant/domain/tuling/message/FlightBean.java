package com.alex.app.lifeassistant.domain.tuling.message;

import com.alex.app.lifeassistant.domain.tuling.TextBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class FlightBean extends TextBean {
	private List<FlightPointBean> list;

	public FlightBean() {
	}

	@Override
	public String toString() {
		return "FlightBean{" +
				"list=" + list +
				"} " + super.toString();
	}

	public List<FlightPointBean> getList() {
		return list;
	}

	public void setList(List<FlightPointBean> list) {
		this.list = list;
	}
}
