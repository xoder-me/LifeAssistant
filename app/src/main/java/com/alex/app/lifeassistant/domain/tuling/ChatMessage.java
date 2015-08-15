package com.alex.app.lifeassistant.domain.tuling;

import org.joda.time.DateTime;

public class ChatMessage {

	/**
	 * 消息类型
	 */
	private Type type;
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 日期的字符串格式
	 */
	private String dateStr;
	/**
	 * 发送人
	 */
	private String name;

	public enum Type {
		INPUT, OUTPUT
	}

	public ChatMessage() {
	}

	public ChatMessage(Type type, String msg) {
		super();
		this.type = type;
		this.msg = msg;
		this.dateStr = DateTime.now().toString("MM-dd hh:mm:ss");
	}

	public String getDateStr() {
		return dateStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
