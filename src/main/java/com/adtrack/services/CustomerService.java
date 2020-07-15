package com.adtrack.services;


import java.util.Optional;

import com.adtrack.dto.CustomerDataRequest;
import com.adtrack.dto.CustomerDataResponse;
import com.adtrack.entity.CustomerTrackInfo;

public interface CustomerService {

	CustomerTrackInfo save(CustomerDataRequest customerDataRequest);
	Optional<CustomerDataResponse> getCustomerReqDetailByDate(int id, String date);
}
