/**
 * 
 */
package com.mindspark.torjans.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindspark.torjans.dto.BookFlightRequest;
import com.mindspark.torjans.dto.BookFlightResponse;
import com.mindspark.torjans.service.FlightBookingService;
import com.mindspark.torjans.service.FlightBookingServiceImpl;

/**
 * @author M1026324
 *
 */
public class BookFlightHandler {

	private FlightBookingService fligthBookingService = new FlightBookingServiceImpl();
	
	private static final Logger logger = LoggerFactory.getLogger(SearchFlightHandler.class);
	
	public Map<String, String> bookFlights(BookFlightRequest bookFlightRequest) {
		logger.info("Inside BookFlightHandler :: bookFlights");
		Map<String, String> bookResponseMap = null;
		BookFlightResponse bookFlight = fligthBookingService.bookFlight(bookFlightRequest);
		if(bookFlight != null) {
			logger.info("Inside BookFlightHandler :: constructing response map");
			bookResponseMap = new HashMap<String, String>();
			bookResponseMap.put("flightNumber",bookFlight.getAirline() + bookFlight.getFlightNo());
			bookResponseMap.put("deptTime", bookFlight.getDepartureDateTime());
			bookResponseMap.put("arrivalTime", bookFlight.getArrivalDateTime());
			bookResponseMap.put("cost", bookFlight.getCost());
			bookResponseMap.put("pnr", "12869");
			bookResponseMap.put("deptStation", bookFlight.getDeptStation());
			bookResponseMap.put("arrvlStation", bookFlight.getArrivalStation());
			bookResponseMap.put("noOfPassengers", String.valueOf(bookFlight.getQuantity()));
			bookResponseMap.put("PassengerName", "John");
		}
		logger.info("Exiting BookFlightHandler :: bookFlights");
		return bookResponseMap;
	}
}
