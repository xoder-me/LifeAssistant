package com.alex.app.lifeassistant.domain.juhe.lottery;

import com.alex.app.lifeassistant.domain.juhe.BaseBean;

/**
 * Created by alex.lee on 2015-07-05.
 */
public class LotteryPointBean extends BaseBean {
	private String lottery; /*彩票类型*/
	private String lottery_no;/*期号*/
	private String lottery_time;/*开奖日期*/
	private String lottery_nums;/*开奖号码*/
	private String lottery_sales; /*本期销量*/

	public LotteryPointBean() {
	}

	@Override
	public String toString() {
		return "LotteryPointBean{" +
				"lottery='" + lottery + '\'' +
				", lottery_no='" + lottery_no + '\'' +
				", lottery_time='" + lottery_time + '\'' +
				", lottery_nums='" + lottery_nums + '\'' +
				", lottery_sales='" + lottery_sales + '\'' +
				"} " + super.toString();
	}

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public String getLottery_no() {
		return lottery_no;
	}

	public void setLottery_no(String lottery_no) {
		this.lottery_no = lottery_no;
	}

	public String getLottery_time() {
		return lottery_time;
	}

	public void setLottery_time(String lottery_time) {
		this.lottery_time = lottery_time;
	}

	public String getLottery_nums() {
		return lottery_nums;
	}

	public void setLottery_nums(String lottery_nums) {
		this.lottery_nums = lottery_nums;
	}

	public String getLottery_sales() {
		return lottery_sales;
	}

	public void setLottery_sales(String lottery_sales) {
		this.lottery_sales = lottery_sales;
	}
}
