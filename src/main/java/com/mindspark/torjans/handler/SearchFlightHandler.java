/**
 * 
 */
package com.mindspark.torjans.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mindspark.torjans.dto.FlightSearchRequest;
import com.mindspark.torjans.dto.FlightSearchResponse;
import com.mindspark.torjans.service.FlightBookingService;
import com.mindspark.torjans.service.FlightBookingServiceImpl;

/**
 * @author M1026324
 *
 */
public class SearchFlightHandler {
	
	private FlightBookingService fligthBookingService = new FlightBookingServiceImpl();
	
	public SearchFlightHandler() {
	}

	public List<Map<String, String>> searchFlights(FlightSearchRequest searchRequest) {
		List<Map<String, String>> data = new ArrayList<>();
		List<FlightSearchResponse> searchForFlights = 
				fligthBookingService.searchForFlights(searchRequest);
		for(FlightSearchResponse searchResponse : searchForFlights) {
			Map<String, String> flightData = new HashMap<>();
	    	flightData.put("flightNumber", searchResponse.getAirline() + searchResponse.getFlightNo());
	    	flightData.put("deptTime", searchResponse.getDepartureDateTime());
	    	flightData.put("arrivalTime", searchResponse.getArrivalDateTime());
	    	flightData.put("cost", searchResponse.getCost());
	    	data.add(flightData);
		}
		
		
		return data;
	}

}
