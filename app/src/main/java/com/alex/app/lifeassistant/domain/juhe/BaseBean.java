package com.alex.app.lifeassistant.domain.juhe;

/**
 * Created by alex.lee on 2015-07-03.
 */
public class BaseBean {
	private int error_code;
	private String reason;
	private String resultcode;

	@Override
	public String toString() {
		return "TextBean{" +
				"error_code=" + error_code +
				", reason='" + reason + '\'' +
				", resultcode='" + resultcode + '\'' +
				'}';
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
}
