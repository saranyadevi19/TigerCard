package com.h2Database.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commutation_details")
public class CommutationDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private int userId;
	
	@Column
	private int fromZone;
	
	@Column
	private int toZone;
	
	@Column
	private int journeyRate;
	
	@Column
	private String journeyDay;
	
	@Column
	private LocalDateTime journeyDateTime;

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

	public int getJourneyRate() {
		return journeyRate;
	}

	public void setJourneyRate(int journeyRate) {
		this.journeyRate = journeyRate;
	}

	public String getJourneyDay() {
		return journeyDay;
	}

	public void setJourneyDay(String journeyDay) {
		this.journeyDay = journeyDay;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getJourneyDateTime() {
		return journeyDateTime;
	}

	public void setJourneyDateTime(LocalDateTime journeyDateTime) {
		this.journeyDateTime = journeyDateTime;
	}

	public CommutationDetails() {
		// TODO Auto-generated constructor stub
	}

	public CommutationDetails(int userId, int fromZone, int toZone, int journeyRate, String journeyDay,
			LocalDateTime journeyDateTime) {
		super();
		this.userId = userId;
		this.fromZone = fromZone;
		this.toZone = toZone;
		this.journeyRate = journeyRate;
		this.journeyDay = journeyDay;
		this.journeyDateTime = journeyDateTime;
	}
	

}
