package com.seek.trafficcounter.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Traffic {

	Date date;
	long count;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getDateString(){
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		return format.format(getDate());
	}
	public String getDateTimeString(){
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		return format.format(getDate());
	}
}
