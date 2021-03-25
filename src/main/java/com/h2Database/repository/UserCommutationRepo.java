package com.h2Database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.h2Database.entities.CommutationDetails;

@Repository
public interface UserCommutationRepo extends JpaRepository<CommutationDetails, Integer> {

	@Query(value = "SELECT * FROM commutation_details WHERE user_id = :user_id AND journey_date_time LIKE :journey_date%", nativeQuery = true)
	public Optional<List<CommutationDetails>> findCommutationDetailsForTheDayByUserId(
            @Param(value = "user_id") int userId, @Param(value = "journey_date") String journeyDate);

	@Query(value = "SELECT * FROM commutation_details WHERE user_id = :user_id AND journey_date_time BETWEEN :journey_start_date AND :journey_end_date", nativeQuery = true)
	public Optional<List<CommutationDetails>> findCommutationDetailsForTheWeekByUserId(
            @Param(value = "user_id") int userId, @Param(value = "journey_start_date") String journeyStartDate,
            @Param(value = "journey_end_date") String journeyEndDate);

}
