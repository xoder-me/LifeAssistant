package com.alex.app.lifeassistant.domain.tuling.message;

/**
 * Created by alex.lee on 2015-07-04.
 */
public class FlightPointBean {
	private String flight;
	private String route;
	private String starttime;
	private String endtime;
	private String state;
	private String detailurl;
	private String icon;

	public FlightPointBean() {
	}

	@Override
	public String toString() {
		return "FlightPointBean{" +
				"flight='" + flight + '\'' +
				", route='" + route + '\'' +
				", starttime='" + starttime + '\'' +
				", endtime='" + endtime + '\'' +
				", state='" + state + '\'' +
				", detailurl='" + detailurl + '\'' +
				", icon='" + icon + '\'' +
				'}';
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
