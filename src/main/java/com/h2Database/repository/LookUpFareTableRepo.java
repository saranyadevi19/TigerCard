package com.h2Database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.h2Database.entities.FareLookupTableEntity;

@Repository
public interface LookUpFareTableRepo extends JpaRepository<FareLookupTableEntity, Integer>{

	@Query(value = "SELECT * FROM fare_lookup_table WHERE from_zone = :from_zone and to_zone = :to_zone", nativeQuery = true)
	public Optional<FareLookupTableEntity> findFaresByFromAndToZone(@Param(value = "from_zone") int fromZone, @Param(value = "to_zone") int toZone);
}
