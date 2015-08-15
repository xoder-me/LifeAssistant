package com.alex.app.lifeassistant.domain.juhe.lottery;

import com.alex.app.lifeassistant.domain.juhe.BaseBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-05.
 */
public class LotteryTypeBean extends BaseBean {
	private List<LotteryTypePointBean> result;

	public LotteryTypeBean() {
	}

	@Override
	public String toString() {
		return "LotteryBean{" +
				"result=" + result +
				"} " + super.toString();
	}

	public List<LotteryTypePointBean> getResult() {
		return result;
	}

	public void setResult(List<LotteryTypePointBean> result) {
		this.result = result;
	}
}
