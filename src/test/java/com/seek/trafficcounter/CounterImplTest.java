package com.seek.trafficcounter;

import com.seek.trafficcounter.model.Count;
import com.seek.trafficcounter.model.DayTraffic;
import com.seek.trafficcounter.service.Counter;
import com.seek.trafficcounter.service.CounterImpl;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CounterImplTest {
	@Test
	public void testFromResourceFile(){

		Counter counter = new CounterImpl();
		try {
			Count count = counter.processTrafficFromFile("traffic.log");
			assertEquals(count.getTotal(),411);
			assertEquals(count.getDayTraffics().get(0).getCount(),179);
			assertEquals(count.getTop3Traffics().get(0).getCount(),46);
			assertEquals(count.getTop3Traffics().get(1).getCount(),42);
			assertEquals(count.getTop3Traffics().get(2).getCount(),33);
			assertEquals(count.getLeast3Traffics().get(0).getCount(),5);
			assertEquals(count.getLeast3Traffics().get(1).getCount(),6);
			assertEquals(count.getLeast3Traffics().get(2).getCount(),2);
		}catch(IOException e){

			assertFalse(true);
		}
	}
	@Test
	public void testIncomplete3Periods(){

		List<String> list = new ArrayList<>();
		list.add("2016-12-09T00:00:00 4");
		list.add("2016-12-09T00:30:00 5");

		Count count = new Count();
		Counter counter = new CounterImpl();
		for(String line: list) {
			counter.processLine(line,count);
		}

		DayTraffic dayTraffic = new DayTraffic();
		dayTraffic.setCount(count.getTotalInDay());
		dayTraffic.setDate(count.getTraffic().getDateString());
		count.getDayTraffics().add(dayTraffic);

		assertEquals(count.getTotal(),9);
		assertEquals(count.getDayTraffics().get(0).getCount(),9);
		assertEquals(count.getTop3Traffics().get(0).getCount(),5);
		assertEquals(count.getTop3Traffics().get(1).getCount(),4);
		assertEquals(count.getLeast3Traffics().size(),0);

	}


	@Test
	public void testOneDay(){

		List<String> list = new ArrayList<>();
		list.add("2016-12-09T00:00:00 4");
		list.add("2016-12-09T00:30:00 5");
		list.add("2016-12-09T01:00:00 6");
		list.add("2016-12-09T01:30:00 2");
		Count count = new Count();
		Counter counter = new CounterImpl();
		for(String line: list) {
			counter.processLine(line,count);
		}

		DayTraffic dayTraffic = new DayTraffic();
		dayTraffic.setCount(count.getTotalInDay());
		dayTraffic.setDate(count.getTraffic().getDateString());
		count.getDayTraffics().add(dayTraffic);

		assertEquals(count.getTotal(),17);
		assertEquals(count.getDayTraffics().get(0).getCount(),17);
		assertEquals(count.getTop3Traffics().get(0).getCount(),6);
		assertEquals(count.getTop3Traffics().get(1).getCount(),5);
		assertEquals(count.getTop3Traffics().get(2).getCount(),4);

		assertEquals(count.getLeast3Traffics().get(0).getCount(),5);
		assertEquals(count.getLeast3Traffics().get(1).getCount(),6);
		assertEquals(count.getLeast3Traffics().get(2).getCount(),2);
	}

	@Test
	public void testMultipleDays(){

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date startDate = null;
		try {
			startDate = format.parse("2019-05-20T00:00:00");
		}catch (ParseException p){
			p.printStackTrace();
		}



		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.setTime(startDate);
		List<String> list = new ArrayList<>();
		for(int i=0;i<10000;i++){

			list.add(format.format(calendar.getTime()) +" "+ String.valueOf(i%3));
			calendar.add(Calendar.MINUTE, 30);
		}
		for(int i=0;i<3;i++){

			list.add(format.format(calendar.getTime()) +" "+ 7);
			calendar.add(Calendar.MINUTE, 30);
		}

		Count count = new Count();

		Counter counter = new CounterImpl();
		for(String line: list) {
			counter.processLine(line,count);
		}

		DayTraffic dayTraffic = new DayTraffic();
		dayTraffic.setCount(count.getTotalInDay());
		dayTraffic.setDate(count.getTraffic().getDateString());
		count.getDayTraffics().add(dayTraffic);

		assertEquals(count.getTotal(),10020);
		assertEquals(count.getDayTraffics().get(0).getDate(),"2019-05-20");
		assertEquals(count.getDayTraffics().get(1).getDate(),"2019-05-21");
		assertEquals(count.getDayTraffics().get(2).getDate(),"2019-05-22");
		assertEquals(count.getDayTraffics().get(3).getDate(),"2019-05-23");

		assertEquals(count.getTop3Traffics().get(0).getCount(),7);
		assertEquals(count.getTop3Traffics().get(1).getCount(),7);
		assertEquals(count.getTop3Traffics().get(2).getCount(),7);

		assertEquals(count.getLeast3Traffics().get(0).getCount(),0);
		assertEquals(count.getLeast3Traffics().get(1).getCount(),1);
		assertEquals(count.getLeast3Traffics().get(2).getCount(),2);
	}



}
