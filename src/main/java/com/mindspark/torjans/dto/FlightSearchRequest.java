/**
 * 
 */
package com.mindspark.torjans.dto;


/**
 * @author M1026329
 *
 */
public class FlightSearchRequest {
	private String fromStation;
	private String toStation;
	private String departureDate;
	private String airline;
	private String cabin;
	private int Quantity;
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
	 * @return the departureDate
	 */
	public String getDepartureDate() {
		return departureDate;
	}
	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
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
}
