package com.alex.app.lifeassistant.domain.tuling.message;

import com.alex.app.lifeassistant.domain.tuling.TextBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class TrainBean extends TextBean {
	private List<TrainPointBean> list;

	public TrainBean() {
	}

	@Override
	public String toString() {
		return "TrainBean{" +
				"list=" + list +
				"} " + super.toString();
	}

	public List<TrainPointBean> getList() {
		return list;
	}

	public void setList(List<TrainPointBean> list) {
		this.list = list;
	}
}
