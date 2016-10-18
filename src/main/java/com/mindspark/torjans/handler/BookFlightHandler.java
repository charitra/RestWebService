/**
 * 
 */
package com.mindspark.torjans.handler;

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
	
	public String bookFlights(BookFlightRequest bookFlightRequest) {
		String result = "Sorry! We are facing some issue while booking your flight. Please try after some time";
		BookFlightResponse bookFlight = fligthBookingService.bookFlight(bookFlightRequest);
		if(bookFlight != null) {
			result = "Congrats! Your booking is confirmed on " + bookFlightRequest.getFlightNo();
		}
		return result;
	}
}
