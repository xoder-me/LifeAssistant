package com.alex.app.lifeassistant.demo;

import android.test.AndroidTestCase;

/**
 * Created by alex.lee on 2015-07-02.
 */
public class ExpressDaoTest extends AndroidTestCase {
	
	public void testSave() throws Exception {
		new ExpressDao(getContext()).save();
	}

	public void testQueryAll() throws Exception {
		new ExpressDao(getContext()).queryAll();
	}

	public void testLikeQuery() throws Exception {
		new ExpressDao(getContext()).likeQuery();
	}
}