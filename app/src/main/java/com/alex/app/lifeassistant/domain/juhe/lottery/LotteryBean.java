package com.alex.app.lifeassistant.domain.juhe.lottery;

import com.alex.app.lifeassistant.domain.juhe.BaseBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-05.
 */
public class LotteryBean extends BaseBean {
	private List<LotteryPointBean> result;

	public LotteryBean() {
	}

	@Override
	public String toString() {
		return "LotteryBean{" +
				"result=" + result +
				"} " + super.toString();
	}

	public List<LotteryPointBean> getResult() {
		return result;
	}

	public void setResult(List<LotteryPointBean> result) {
		this.result = result;
	}
}
