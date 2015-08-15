package com.alex.app.lifeassistant.domain.juhe.express;

import com.alex.app.lifeassistant.domain.juhe.BaseBean;

import java.io.Serializable;

/**
 * Created by alex.lee on 2015-07-01.
 */
public class ExpressBean extends BaseBean implements Serializable {
	private ExpressInfoBean result;

	public ExpressBean() {
	}

	@Override
	public String toString() {
		return "ExpressBean{" +
				"result=" + result +
				'}';
	}

	public ExpressInfoBean getResult() {
		return result;
	}

	public void setResult(ExpressInfoBean result) {
		this.result = result;
	}
}
