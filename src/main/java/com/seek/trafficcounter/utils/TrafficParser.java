package com.seek.trafficcounter.utils;

import com.seek.trafficcounter.model.Constants;
import com.seek.trafficcounter.model.Traffic;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TrafficParser {
	public Traffic parse(String input){
		Traffic traffic = new Traffic();
		String[] trafficString = input.split(" ");
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		try {
			traffic.setDate(format.parse(trafficString[0]));
			traffic.setCount(Long.valueOf(trafficString[1]).longValue());
		}catch(ParseException e){
			e.printStackTrace();
		}
		return traffic;
	}
}
