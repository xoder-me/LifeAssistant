package com.alex.app.lifeassistant.comparator;

import com.alex.app.lifeassistant.domain.juhe.express.CompanyPointBean;

import java.util.Comparator;

/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<CompanyPointBean> {
	public int compare(CompanyPointBean o1, CompanyPointBean o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return 1;
		} else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
			return -1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}
}
