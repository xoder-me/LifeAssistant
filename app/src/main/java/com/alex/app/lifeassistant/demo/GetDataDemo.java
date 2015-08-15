package com.alex.app.lifeassistant.demo;

import android.content.Context;

import com.alex.app.lifeassistant.constans.Constans;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

/**
 * Created by alex.lee on 2015-07-03.
 */
public class GetDataDemo {
	/**
	 * 执行快递公司查询
	 */
	public void companyQuery(Context context) {
		System.out.println("测试 --> 执行");
		// 初始化参数
		Parameters params = new Parameters();
		params.add("dtype", "json");

		JuheData.executeWithAPI(context, Constans.JUHE_EXPRESS_ID, Constans.JUHE_EXPRESS_COM, JuheData.POST, params, new DataCallBack() {
			@Override
			public void onSuccess(int statusCode, String responseString) {
				System.out.println(responseString);
//				CompanyBean company = JSON.parseObject(responseString, CompanyBean.class);
//				if (company.getResultcode().equals("200")) {
//					List<CompanyPointBean> result = company.getResult();
//					System.out.println(result);
//				} else {
//					System.out.println(company.getReason());
//				}
			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onFailure(int statusCode, String responseString, Throwable throwable) {
				System.out.println(throwable.getMessage());
			}
		});
	}
}
