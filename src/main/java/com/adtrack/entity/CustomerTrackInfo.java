package com.adtrack.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name="HOURLY_STATS")
public class CustomerTrackInfo {
	
	 @Id
	 @GeneratedValue
	 private int id;
	 private int customerId;
	 private Timestamp time;
	 private long requestCount;
	 private long invalidCount;	
}
