package com.alex.app.lifeassistant.demo;

import android.content.Context;

import com.alex.app.lifeassistant.domain.juhe.express.ExpressBean;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressInfoBean;
import com.alex.app.lifeassistant.domain.juhe.express.ExpressPointBean;
import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by alex.lee on 2015-07-02.
 */

public class ExpressDao {
	private DbUtils db;
	private Context context;

	public ExpressDao(Context context) {
		this.context = context;
		this.db = DbUtils.create(this.context, "express.db");
	}

	public void likeQuery() {
		String params = 190 + "";
		StringBuilder str = new StringBuilder("测试数据: \n");
		WhereBuilder builder = WhereBuilder.b();
		builder.or("company", "like", "%" + params + "%");
		builder.or("no", "like", "%" + params + "%");
		try {
			List<ExpressInfoBean> infos = db.findAll(Selector.from(ExpressInfoBean.class).where(builder));
			str.append("数据长度 --> " + infos.size() + "\n");
			str.append("全部数据 --> " + infos.toString());
		} catch (DbException e) {
			e.printStackTrace();
		}
		System.out.println(str);
	}

	public void queryAll() {
		StringBuilder str = new StringBuilder("测试数据: \n");

		try {
			List<ExpressInfoBean> express = db.findAll(Selector.from(ExpressInfoBean.class));
			str.append("数据长度 --> " + express.size() + "\n");
			str.append("首位数据 --> " + express.get(0));
//			for (ExpressBean exp : express) {
//				str.append(exp.toString());
//			}
		} catch (DbException e) {
			e.printStackTrace();
		}

		System.out.println(str);
	}

	public void save() {
		try {
			InputStream is = context.getAssets().open("express_ok.json");
			byte[] buff = new byte[is.available()];
			is.read(buff);
			String json = new String(buff, "utf-8");
			System.out.println(json);
			ExpressBean express = JSON.parseObject(json, ExpressBean.class);
			ExpressInfoBean expressInfo = express.getResult();
//			expressInfo.express = express;
			List<ExpressPointBean> points = expressInfo.getList();

//			db.save(express);
			db.save(expressInfo);
			for (ExpressPointBean point : points) {
				point.expressInfo = expressInfo;
				db.save(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
