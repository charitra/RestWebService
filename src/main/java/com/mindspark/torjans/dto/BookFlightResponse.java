package com.mindspark.torjans.dto;

public class BookFlightResponse {

	private String airline;
	private String flightNo;
	private String deptStation;
	private String arrivalStation;
	private String paxSurname;
	private int quantity;
	private String cost;
	private String bookingReference;
	private String departureDateTime;
	private String arrivalDateTime;
	
	public String getDeptStation() {
		return deptStation;
	}
	public void setDeptStation(String deptStation) {
		this.deptStation = deptStation;
	}
	public String getArrivalStation() {
		return arrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}
	public String getPaxSurname() {
		return paxSurname;
	}
	public void setPaxSurname(String paxSurname) {
		this.paxSurname = paxSurname;
	}
	public String getDepartureDateTime() {
		return departureDateTime;
	}
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}
	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	public String getBookingReference() {
		return bookingReference;
	}
	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
}
