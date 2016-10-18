/**
 * 
 */
package com.mindspark.torjans.service;

import java.util.List;

import com.mindspark.torjans.dto.BookFlightRequest;
import com.mindspark.torjans.dto.BookFlightResponse;
import com.mindspark.torjans.dto.FlightSearchRequest;
import com.mindspark.torjans.dto.FlightSearchResponse;

/**
 * @author M1026329
 *
 */
public interface FlightBookingService {
	
	public List<FlightSearchResponse> searchForFlights(FlightSearchRequest flightSearchRequest);
	
	public BookFlightResponse bookFlight(BookFlightRequest bookFlightRequest);
}
