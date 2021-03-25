package com.h2Database.dtos;

public class TigerCardDetails {
	
	private int userId;
	private int fromZone;
	private int toZone;
	private int fare;
	
	
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFromZone() {
		return fromZone;
	}
	public void setFromZone(int fromZone) {
		this.fromZone = fromZone;
	}
	public int getToZone() {
		return toZone;
	}
	public void setToZone(int toZone) {
		this.toZone = toZone;
	}
	
	public TigerCardDetails() {
		// TODO Auto-generated constructor stub
	}
	public TigerCardDetails(int userId, int fromZone, int toZone) {
		super();
		this.userId = userId;
		this.fromZone = fromZone;
		this.toZone = toZone;
	}
	
	

}
