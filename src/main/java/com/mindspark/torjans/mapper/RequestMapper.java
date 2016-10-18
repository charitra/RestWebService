package com.mindspark.torjans.mapper;

import org.json.JSONException;
import org.json.JSONObject;

import com.mindspark.torjans.dto.BookFlightRequest;
import com.mindspark.torjans.dto.FlightSearchRequest;

public class RequestMapper {

	public FlightSearchRequest constructSearchRequest(JSONObject params) {
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
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return searchRequest;
	}

	public BookFlightRequest constructBookFlightRequest(JSONObject params) {
		BookFlightRequest flightRequest = new BookFlightRequest();
		
		try {
			if(params.getString("flightNumber") != null) {
				flightRequest.setFlightNo(params.getString("flightNumber"));
			}
			if(params.getString("deptTime") != null) {
				flightRequest.setDeptDate(params.getString("deptTime"));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flightRequest;
	}
}
