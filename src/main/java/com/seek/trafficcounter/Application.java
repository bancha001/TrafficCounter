package com.seek.trafficcounter;

import com.seek.trafficcounter.model.Count;
import com.seek.trafficcounter.model.DayTraffic;
import com.seek.trafficcounter.model.Traffic;
import com.seek.trafficcounter.service.Counter;
import com.seek.trafficcounter.service.CounterImpl;

public class Application {
	public static void main(String[] args){
		Counter counter = new CounterImpl();
		try {
			String fileName = "traffic.log";
			Count count = counter.processTrafficFromFile(fileName);
			System.out.println("Number of cars in total :"+count.getTotal());

			System.out.println("Traffic each day");
			for(DayTraffic dayTraffic: count.getDayTraffics()){
				System.out.println(dayTraffic.getDate()+" "+dayTraffic.getCount());
			}
			System.out.println("Top 3 half hours most cars");

			for(Traffic traffic: count.getTop3Traffics()){
				System.out.println(traffic.getDateTimeString()+" "+traffic.getCount());
			}
			System.out.println("Least cars in 3 contiguous half hour records");
			for(Traffic traffic: count.getLeast3Traffics()){
				System.out.println(traffic.getDateTimeString()+" "+traffic.getCount());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
