package com.alex.app.lifeassistant.domain.juhe.lottery;

/**
 * Created by alex.lee on 2015-07-05.
 */
public class LotteryTypePointBean {
	private int lotteryId;
	private String lottery;

	public LotteryTypePointBean() {
	}

	@Override
	public String toString() {
		return "LotteryTypePointBean{" +
				"lotteryId=" + lotteryId +
				", lottery='" + lottery + '\'' +
				'}';
	}

	public int getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
}
