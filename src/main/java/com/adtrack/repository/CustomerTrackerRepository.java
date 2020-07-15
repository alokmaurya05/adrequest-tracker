package com.adtrack.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adtrack.entity.CustomerTrackInfo;

@Repository
public interface CustomerTrackerRepository extends JpaRepository<CustomerTrackInfo, Integer> {
	
	CustomerTrackInfo findByCustomerIdAndTime(int customerId, Timestamp time);
	
	List<CustomerTrackInfo> findByCustomerIdAndTimeBetween(int customerId, Timestamp fromTime, Timestamp toTime);
}
