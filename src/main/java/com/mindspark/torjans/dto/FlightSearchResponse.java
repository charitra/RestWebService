/**
 * 
 */
package com.mindspark.torjans.dto;


/**
 * @author M1026329
 *
 */
public class FlightSearchResponse {
	private String flightNo;
	private String fromStation;
	private String toStation;
	private String departureDateTime;
	private String arrivalDateTime;
	private String airline;
	private String cabin;
	private int Quantity;
	private String cost;
	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}
	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	/**
	 * @return the fromStation
	 */
	public String getFromStation() {
		return fromStation;
	}
	/**
	 * @param fromStation the fromStation to set
	 */
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	/**
	 * @return the toStation
	 */
	public String getToStation() {
		return toStation;
	}
	/**
	 * @param toStation the toStation to set
	 */
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	/**
	 * @return the departureDateTime
	 */
	public String getDepartureDateTime() {
		return departureDateTime;
	}
	/**
	 * @param departureDateTime the departureDateTime to set
	 */
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	/**
	 * @return the arrivalDateTime
	 */
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}
	/**
	 * @param arrivalDateTime the arrivalDateTime to set
	 */
	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	/**
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	/**
	 * @return the cabin
	 */
	public String getCabin() {
		return cabin;
	}
	/**
	 * @param cabin the cabin to set
	 */
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return Quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}
}
