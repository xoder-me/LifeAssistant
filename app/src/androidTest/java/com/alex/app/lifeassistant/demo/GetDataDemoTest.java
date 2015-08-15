package com.alex.app.lifeassistant.demo;

import android.test.AndroidTestCase;


/**
 * Created by alex.lee on 2015-07-03.
 */
public class GetDataDemoTest extends AndroidTestCase {
	
	public void testCompanyQuery() throws Exception {
		new GetDataDemo().companyQuery(getContext());
	}
}