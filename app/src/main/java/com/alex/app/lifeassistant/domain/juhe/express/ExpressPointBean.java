package com.alex.app.lifeassistant.domain.juhe.express;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by alex.lee on 2015-07-01.
 */
@Table(name = "express_point")
public class ExpressPointBean implements Serializable {
	private int id;
	@Column(column = "datetime")
	private String datetime;
	@Column(column = "remark")
	private String remark;
	@Column(column = "zone")
	private String zone;

	@Foreign(column = "info_id", foreign = "no")
	public ExpressInfoBean expressInfo;

	public ExpressPointBean() {
	}

	@Override
	public String toString() {
		return "{" +
				"zone:'" + zone + '\'' +
				", id:" + id +
				", datetime:'" + datetime + '\'' +
				", remark:'" + remark + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
}
