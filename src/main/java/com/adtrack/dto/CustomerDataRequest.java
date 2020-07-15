package com.adtrack.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDataRequest {

	@NotNull
	@Min(1)
	private int customerID;
	@NotNull
	@Min(1)
	private int tagID;
	@NotEmpty
	private String userID;
	@NotEmpty
	private String remoteIP;
	@NotNull
	private Timestamp timestamp;
}
