package com.seek.trafficcounter.model;

import java.util.ArrayList;
import java.util.List;

public class Count {
	private List<Traffic> top3Traffics = new ArrayList<>();
	private List<Traffic> least3Traffics = new ArrayList<>();
	private List<Traffic> traffics = new ArrayList<>();
	private Traffic traffic = null;
	private long totalInDay = 0;
	private long total = 0;

	public List<DayTraffic> getDayTraffics() {
		return dayTraffics;
	}

	public void setDayTraffics(List<DayTraffic> dayTraffics) {
		this.dayTraffics = dayTraffics;
	}

	private List<DayTraffic> dayTraffics = new ArrayList<>();

	public List<Traffic> getTop3Traffics() {
		return top3Traffics;
	}

	public void setTop3Traffics(List<Traffic> top3Traffics) {
		this.top3Traffics = top3Traffics;
	}

	public List<Traffic> getLeast3Traffics() {
		return least3Traffics;
	}

	public void setLeast3Traffics(List<Traffic> least3Traffics) {
		this.least3Traffics = least3Traffics;
	}

	public List<Traffic> getTraffics() {
		return traffics;
	}

	public void setTraffics(List<Traffic> traffics) {
		this.traffics = traffics;
	}

	public Traffic getTraffic() {
		return traffic;
	}

	public void setTraffic(Traffic traffic) {
		this.traffic = traffic;
	}

	public long getTotalInDay() {
		return totalInDay;
	}

	public void setTotalInDay(long totalInDay) {
		this.totalInDay = totalInDay;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
