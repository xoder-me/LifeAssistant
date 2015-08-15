package com.alex.app.lifeassistant.domain.juhe.express;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alex.lee on 2015-07-01.
 */
@Table(name = "express_info")
public class ExpressInfoBean implements Serializable {
	@Id
	private String no;
	@Column(column = "com")
	private String com;
	@Column(column = "status")
	private String status;
	@Column(column = "company")
	private String company;
	@Finder(valueColumn = "no", targetColumn = "info_id")
	private List<ExpressPointBean> list;


	public ExpressInfoBean() {
	}

	@Override
	public String toString() {
		return "{" +
				"no:'" + no + '\'' +
				", com:'" + com + '\'' +
				", status:'" + status + '\'' +
				", company:'" + company + '\'' +
				", list:" + list +
				'}';
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ExpressPointBean> getList() {
		return list;
	}

	public void setList(List<ExpressPointBean> list) {
		this.list = list;
	}
}
