package com.seek.trafficcounter.service;

import com.seek.trafficcounter.utils.TrafficParser;
import com.seek.trafficcounter.model.Count;
import com.seek.trafficcounter.model.DayTraffic;
import com.seek.trafficcounter.model.Traffic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CounterImpl implements Counter {


	public Count processTrafficFromFile(String fileName) throws IOException {

		Count count = new Count();

		File file = new File(fileName);

		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String line;

		while (null != (line = bufferedReader.readLine())) {
			processLine(line, count);
		}

		DayTraffic dayTraffic = new DayTraffic();
		dayTraffic.setCount(count.getTotalInDay());
		dayTraffic.setDate(count.getTraffic().getDateString());
		count.getDayTraffics().add(dayTraffic);

		bufferedReader.close();


		return count;
	}


	public void processLine(String line, Count count) {

		TrafficParser trafficParser = new TrafficParser();
		Traffic tempTraffic = trafficParser.parse(line);
		List<Traffic> top3Traffic = top3Traffic(count.getTop3Traffics(), tempTraffic);
		count.setTop3Traffics(top3Traffic);
		if (count.getTraffics().size() == 3) {
			List<Traffic> trafficList = new ArrayList<>();
			for (int i = 1; i < 3; i++) {
				trafficList.add(count.getTraffics().get(i));
			}
			trafficList.add(tempTraffic);
			count.setTraffics(trafficList);
		} else {
			count.getTraffics().add(tempTraffic);
		}

		List<Traffic> temp3LeastTraffic = getOneAndHalfPeriodTraffic(count.getTraffics());


		if (!temp3LeastTraffic.isEmpty() && count.getLeast3Traffics().isEmpty()
				|| countList(temp3LeastTraffic) < countList(count.getLeast3Traffics())) {
			count.setLeast3Traffics(temp3LeastTraffic);

		}

		if (count.getTraffic() != null && tempTraffic.getDateString().equals(count.getTraffic().getDateString())) {
			long totalInDay = count.getTotalInDay() + tempTraffic.getCount();
			count.setTotalInDay(totalInDay);
		} else {
			if (count.getTraffic() != null) {
				DayTraffic dayTraffic = new DayTraffic();
				dayTraffic.setDate(count.getTraffic().getDateString());
				dayTraffic.setCount(count.getTotalInDay());
				count.getDayTraffics().add(dayTraffic);

			}
			count.setTotalInDay(tempTraffic.getCount());
		}
		count.setTotal(count.getTotal() + tempTraffic.getCount());
		count.setTraffic(tempTraffic);

	}

	private List<Traffic> top3Traffic(List<Traffic> list, Traffic traffic) {
		list.add(traffic);
		list = list.stream().sorted(Comparator.comparing(Traffic::getCount).reversed()).collect(Collectors.toList());

		List<Traffic> result = new ArrayList<>();
		for (int i = 0; i < list.size() && i < 3; i++) {
			result.add(list.get(i));
		}
		return result;
	}

	private List<Traffic> getOneAndHalfPeriodTraffic(List<Traffic> list) {
		if (isContiguous(list)) {
			return list;
		} else {
			return new ArrayList<>();
		}
	}

	private boolean isContiguous(List<Traffic> traffics) {

		boolean result = true;
		if (traffics.size() == 3) {
			for (int i = 0; i < 2; i++) {
				result = result && ((traffics.get(i + 1).getDate().getTime() - traffics.get(i).getDate().getTime()) / 1000 == 1800);
			}
		} else {
			result = false;
		}

		return result;
	}

	private long countList(List<Traffic> traffics) {
		long result = 0;
		if (traffics.size() < 3) {
			return -1;
		}
		for (Traffic traffic : traffics) {
			result = result + traffic.getCount();
		}
		return result;
	}
}
