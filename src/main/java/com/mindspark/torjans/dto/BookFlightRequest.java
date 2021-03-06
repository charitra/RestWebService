package com.mindspark.torjans.dto;

public class BookFlightRequest {

	private String airline;
	private String flightNo;
	private String DeptStation;
	private String ArrivalStation;
	private String DeptDate;
	private String ArrDate;
	private String paxSurname;
	private int quantity;
	
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
	public String getDeptStation() {
		return DeptStation;
	}
	public void setDeptStation(String deptStation) {
		DeptStation = deptStation;
	}
	public String getArrivalStation() {
		return ArrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		ArrivalStation = arrivalStation;
	}
	public String getDeptDate() {
		return DeptDate;
	}
	public void setDeptDate(String deptDate) {
		DeptDate = deptDate;
	}
	public String getArrDate() {
		return ArrDate;
	}
	public void setArrDate(String arrDate) {
		ArrDate = arrDate;
	}
	public String getPaxSurname() {
		return paxSurname;
	}
	public void setPaxSurname(String paxSurname) {
		this.paxSurname = paxSurname;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	
}
