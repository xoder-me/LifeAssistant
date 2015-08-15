package com.alex.app.lifeassistant.domain.juhe.express;

import com.alex.app.lifeassistant.domain.juhe.BaseBean;

import java.util.List;

/**
 * Created by alex.lee on 2015-07-03.
 */
public class CompanyBean extends BaseBean {
	private List<CompanyPointBean> result;

	@Override
	public String toString() {
		return "CompanyBean{" +
				"result=" + result +
				"} " + super.toString();
	}

	public List<CompanyPointBean> getResult() {
		return result;
	}

	public void setResult(List<CompanyPointBean> result) {
		this.result = result;
	}
}
