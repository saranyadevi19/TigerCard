package com.h2Database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fare_lookup_table")
public class FareLookupTableEntity {

	@Id
	private int id;

	@Column(name = "from_zone")
	private int fromZone;
	
	@Column(name = "to_zone")
	private int toZone;
	
	@Column(name = "peak_hour_rate")
	private int peakHourRate;
	
	@Column(name = "off_peak_hour_rate")
	private int offPeakHourRate;

	public FareLookupTableEntity(int id, int fromZone, int toZone, int peakHourRate, int offPeakHourRate) {
		super();
		this.id = id;
		this.fromZone = fromZone;
		this.toZone = toZone;
		this.peakHourRate = peakHourRate;
		this.offPeakHourRate = offPeakHourRate;
	}
	
	public FareLookupTableEntity() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPeakHourRate() {
		return peakHourRate;
	}

	public void setPeakHourRate(int peakHourRate) {
		this.peakHourRate = peakHourRate;
	}

	public int getOffPeakHourRate() {
		return offPeakHourRate;
	}

	public void setOffPeakHourRate(int offPeakHourRate) {
		this.offPeakHourRate = offPeakHourRate;
	}
	
	
	
}
