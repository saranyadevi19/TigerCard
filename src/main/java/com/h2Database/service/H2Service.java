package com.h2Database.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h2Database.constants.H2Constants;
import com.h2Database.dtos.TigerCardDetails;
import com.h2Database.entities.CommutationDetails;
import com.h2Database.entities.FareLookupTableEntity;
import com.h2Database.repository.LookUpFareTableRepo;
import com.h2Database.repository.UserCommutationRepo;

@Service
public class H2Service {

	@Autowired
	private UserCommutationRepo userCommutationRepo;

	@Autowired
	private LookUpFareTableRepo fareTableRepo;

	/*
		Save the commutation details and return the fare for the current trip
		TigerCardDetails:
			(user id, from and to zone) - input parameter.
			fare - output parameter
	 */
	public TigerCardDetails computeJourneyDetails(TigerCardDetails cardDetails) {

		return this.saveCommutationDetails(cardDetails);

	}

	private TigerCardDetails saveCommutationDetails(TigerCardDetails cardDetails) {

		//Construct the commutation POJO
		CommutationDetails commutationDetails = new CommutationDetails(cardDetails.getUserId(),
				cardDetails.getFromZone(), cardDetails.getToZone(), 0, this.findJourneyDay(), LocalDateTime.now());
		//Calculate the journey rate and set in commutation object.
		commutationDetails.setJourneyRate(this.calcJourneyRate(commutationDetails));

		cardDetails.setFare(commutationDetails.getJourneyRate());

		//save the commutation in H2
		userCommutationRepo.save(commutationDetails);
		
		return cardDetails;
	}

	private String findJourneyDay() {
		return LocalDateTime.now().getDayOfWeek().name();
	}

	private int calcJourneyRate(CommutationDetails commutationDetails) {
		//Get the Fare details from fare lookup table.
		FareLookupTableEntity fareLookupTableEntity = fareTableRepo
				.findFaresByFromAndToZone(commutationDetails.getFromZone(), commutationDetails.getToZone())
				.orElse(new FareLookupTableEntity());
		int fare = 0;
		//check for peak or non-peak hour.
		if (isPeakHours(commutationDetails)) {
			fare = fareLookupTableEntity.getPeakHourRate();
		} else {
			fare = fareLookupTableEntity.getOffPeakHourRate();
		}
		// find out the day and week capping for that user
		List<Integer> zonalDailyData = this.determineDailyCappingZoneDetails(commutationDetails);
		List<Integer> zonalWeeklyData = this.determineWeeklyCappingZoneDetails(commutationDetails);

		if (zonalDailyData.get(1) > zonalDailyData.get(0)) {
			return 0;
		} else if (zonalDailyData.get(0) > 0 && zonalDailyData.get(0) - zonalDailyData.get(1) < fare) {
			return zonalDailyData.get(0) - zonalDailyData.get(1);
		}
		// weely cap starts
		else if (zonalWeeklyData.get(1) > zonalWeeklyData.get(0)) {
			return 0;
		} else if (zonalWeeklyData.get(0) > 0 && zonalWeeklyData.get(0) - zonalWeeklyData.get(1) < fare) {
			return zonalWeeklyData.get(0) - zonalWeeklyData.get(1);
		}
		return fare;
	}

	private List<Integer> determineWeeklyCappingZoneDetails(CommutationDetails commutationDetails) {
		List<CommutationDetails> userCommutes = userCommutationRepo
				.findCommutationDetailsForTheWeekByUserId(commutationDetails.getUserId(),
						commutationDetails.getJourneyDateTime()
								.minusDays(
										commutationDetails.getJourneyDateTime().toLocalDate().getDayOfWeek().getValue())
								.toLocalDate().toString() + " 00:00:00",
						commutationDetails.getJourneyDateTime().plusDays(1).toLocalDate().toString() + " 00:00:00")
				.orElse(new ArrayList<CommutationDetails>());

		int userZonalWeeklyCapRate = this.determineCappingZoneRate(userCommutes, H2Constants.ZONE_WEEKLY_CAP_RATES);
		int currentCommuteCapRate = H2Constants.ZONE_WEEKLY_CAP_RATES
				.get(Integer.valueOf(commutationDetails.getFromZone()).toString()
						+ Integer.valueOf(commutationDetails.getToZone()).toString());
		if (currentCommuteCapRate > userZonalWeeklyCapRate) {
			return new ArrayList<Integer>(Arrays.asList(currentCommuteCapRate,
					userCommutes.stream().map(commute -> commute.getJourneyRate()).reduce(0, (r1, r2) -> r1 + r2)));
		}

		return new ArrayList<Integer>(Arrays.asList(userZonalWeeklyCapRate,
				userCommutes.stream().map(commute -> commute.getJourneyRate()).reduce(0, (r1, r2) -> r1 + r2)));

	}

	private List<Integer> determineDailyCappingZoneDetails(CommutationDetails commutationDetails) {
		// get the commutation details from commutation table for the day
		List<CommutationDetails> userCommutes = userCommutationRepo
				.findCommutationDetailsForTheDayByUserId(commutationDetails.getUserId(),
						commutationDetails.getJourneyDateTime().toLocalDate().toString())
				.orElse(new ArrayList<CommutationDetails>());

		int userZonalDailyCapRate = this.determineCappingZoneRate(userCommutes, H2Constants.ZONE_DAILY_CAP_RATES);
		int currentCommuteCapRate = H2Constants.ZONE_DAILY_CAP_RATES
				.get(Integer.valueOf(commutationDetails.getFromZone()).toString()
						+ Integer.valueOf(commutationDetails.getToZone()).toString());

		if (currentCommuteCapRate > userZonalDailyCapRate) {
			return new ArrayList<Integer>(Arrays.asList(currentCommuteCapRate,
					userCommutes.stream().map(commute -> commute.getJourneyRate()).reduce(0, (r1, r2) -> r1 + r2)));
		}

		return new ArrayList<Integer>(Arrays.asList(userZonalDailyCapRate,
				userCommutes.stream().map(commute -> commute.getJourneyRate()).reduce(0, (r1, r2) -> r1 + r2)));
	}

	private int determineCappingZoneRate(List<CommutationDetails> userCommutes, Map<String, Integer> zoneCapRates) {

		int userZoneRate = 0;
		for (CommutationDetails commutationDetails : userCommutes) {
			int zoneRate = zoneCapRates.get(Integer.valueOf(commutationDetails.getFromZone()).toString()
					+ Integer.valueOf(commutationDetails.getToZone()).toString());
			if (userZoneRate < zoneRate) {
				userZoneRate = zoneRate;
			}

		}
		return userZoneRate;
	}

	private boolean isPeakHours(CommutationDetails commutationDetails) {
		// Sample format for - LocalDateTime.parse("2018-12-30T19:34:00");
		if (commutationDetails.getJourneyDateTime()
				.compareTo(LocalDateTime.parse(LocalDate.now().toString() + "T"
						+ H2Constants.PEAK_HOURS.get(commutationDetails.getJourneyDay()).get(0) + ":00")) > 0
				&& commutationDetails.getJourneyDateTime().compareTo(LocalDateTime.parse(LocalDate.now().toString()
						+ "T" + H2Constants.PEAK_HOURS.get(commutationDetails.getJourneyDay()).get(1) + ":00")) < 0) {
			return true;
		} else //If travelling from any station outside Zone 1 to a station in Zone
			if (!(commutationDetails.getFromZone() != 1 && commutationDetails.getToZone() == 1)
				&& commutationDetails.getJourneyDateTime()
						.compareTo(LocalDateTime.parse(LocalDate.now().toString() + "T"
								+ H2Constants.PEAK_HOURS.get(commutationDetails.getJourneyDay()).get(2) + ":00")) > 0
				&& commutationDetails.getJourneyDateTime().compareTo(LocalDateTime.parse(LocalDate.now().toString()
						+ "T" + H2Constants.PEAK_HOURS.get(commutationDetails.getJourneyDay()).get(3) + ":00")) < 0) {
			return true;
		} else {
			return false;
		}
	}

}
