package com.alex.app.lifeassistant.domain.juhe.huangli;

import com.alex.app.lifeassistant.domain.juhe.BaseBean;

import java.util.List;

/**
 * 老黄历 - 吉时
 * Created by alex.lee on 2015-07-07.
 */
public class JiShiBean extends BaseBean {
	private List<JiShiPointBean> result;

	public JiShiBean() {
	}

	@Override
	public String toString() {
		return "JiShiBean{" +
				"result=" + result +
				"} " + super.toString();
	}

	public List<JiShiPointBean> getResult() {
		return result;
	}

	public void setResult(List<JiShiPointBean> result) {
		this.result = result;
	}
}
