package com.seek.trafficcounter.service;

import com.seek.trafficcounter.model.Count;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Counter {

	Count processTrafficFromFile(String fileName) throws IOException;
	void processLine(String line, Count count);
}
