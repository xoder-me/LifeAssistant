package com.alex.app.lifeassistant.domain.tuling;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class TextBean {
	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 文字内容
	 */
	private String text;

	public TextBean() {
	}

	@Override
	public String toString() {
		return "TextBean{" +
				"code=" + code +
				", text='" + text + '\'' +
				'}';
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
