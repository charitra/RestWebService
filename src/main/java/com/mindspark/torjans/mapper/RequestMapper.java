package com.mindspark.torjans.mapper;

import hello.FlightServiceController;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindspark.torjans.dto.BookFlightRequest;
import com.mindspark.torjans.dto.FlightSearchRequest;

public class RequestMapper {
	private static final Logger logger = LoggerFactory.getLogger(FlightServiceController.class);
	
	public FlightSearchRequest constructSearchRequest(JSONObject params) {
		logger.info("Inside constructSearchRequest() method");
		FlightSearchRequest searchRequest = new FlightSearchRequest();
		
		try {
			if(params.getString("fromCity") != null) {
				searchRequest.setFromStation(params.getString("fromCity"));
			}
			if(params.getString("toCity") != null) {
				searchRequest.setToStation(params.getString("toCity"));
			}
			if(params.getString("date") != null) {
				searchRequest.setDepartureDate(params.getString("date"));
			}
			if(params.getString("noOfPassengers") != null) {
				int qty = Integer.parseInt(params.getString("noOfPassengers"));
				searchRequest.setQuantity(qty);
			}
			
		} catch (JSONException ex) {
			logger.info("Exception in constructSearchRequest() method");
			ex.printStackTrace();
		}
		logger.info("Exiting constructSearchRequest() method");
		return searchRequest;
	}

	public BookFlightRequest constructBookFlightRequest(JSONObject params, JSONObject contextparams) throws Exception {
		logger.info("Inside constructBookFlightRequest() method");
		BookFlightRequest flightRequest = new BookFlightRequest();
		
		try {
			if(contextparams.getString("flightNumber") != null) {
				flightRequest.setFlightNo(contextparams.getString("flightNumber"));
			}
			if(contextparams.getString("date") != null) {
				flightRequest.setDeptDate(contextparams.getString("date"));
			}
			if(contextparams.getString("fromCity") != null) {
				flightRequest.setDeptStation(contextparams.getString("fromCity"));
			}
			if(contextparams.getString("toCity") != null) {
				flightRequest.setArrivalStation(contextparams.getString("toCity"));
			}
			if(contextparams.getString("noOfPassengers") != null) {
				flightRequest.setQuantity(Integer.parseInt(contextparams.getString("noOfPassengers")));
			}
			flightRequest.setAirline("AI");
			flightRequest.setPaxSurname("John");
			
		} catch (JSONException e) {
			logger.info("Exception in constructBookFlightRequest() method");
			throw new Exception("Sorry! I coudldn't understand");
		}
		logger.info("Exiting constructBookFlightRequest() method");
		return flightRequest;
	}
}
