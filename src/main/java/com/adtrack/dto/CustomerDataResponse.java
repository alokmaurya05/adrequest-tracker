package com.adtrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataResponse {
	 
	 private int customerId;
	 private String  date;
	 private long totalNumberOfRequest ;
}
