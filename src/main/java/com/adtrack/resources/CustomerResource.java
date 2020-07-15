package com.adtrack.resources;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtrack.buildResponse.Response;
import com.adtrack.dto.CustomerDataRequest;
import com.adtrack.dto.CustomerDataResponse;
import com.adtrack.entity.CustomerTrackInfo;
import com.adtrack.error.NoDataFoundException;
import com.adtrack.services.CustomerService;
import com.adtrack.validation.CustomerRequestValidator;


@RestController
@RequestMapping (value = "/api/v1/customer")
public class CustomerResource {

	public static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerRequestValidator requestVaildator;

	// -------------------Save Customer ---------------------------------------------------------
	/**
	 * This method use to save Customer Information 
	 * @param CustomerDataRequest customerDataRequest
	 * @return ResponseEntity<?>
	 */
	@PostMapping
	public ResponseEntity<CustomerTrackInfo> saveCutomer(@Valid @RequestBody CustomerDataRequest request ) {
		logger.info("Customer hourly information request......");
		CustomerTrackInfo	customerInformation =null;
		if(! requestVaildator.getCustomer(request.getCustomerID()).isPresent())	{
			throw new NoDataFoundException("Worng customerId " +request.getCustomerID());
		}
		customerInformation = customerService.save(request);
		return Response.success(customerInformation, HttpStatus.CREATED);     
	} 

	// -------------------Get  specific customer by date ---------------------------------------------------------
	/**
	 * This method use to get Customer requested detail by given date
	 * @param int id
	 * @param String date
	 * @return ResponseEntity<CustomerDataResponse>
	 */
	@GetMapping(value = "/{id}/{date}")
	public ResponseEntity<CustomerDataResponse> getCutomerByDate(@PathVariable ("id") @NotNull int id,
			@PathVariable ("date") @NotEmpty String date) {
		logger.info("Get customer deatil with Id "+ id +" & date "+date);
		Optional<CustomerDataResponse> customerData = customerService.getCustomerReqDetailByDate(id, date);
		if(! customerData.isPresent()) {
			logger.info("No customer deatil found with Id "+ id +" & date "+date);
			throw new NoDataFoundException("Not found customer detail with "+id +" on Date "+date)	;
		}
		return Response.success(customerData.get(), HttpStatus.OK);      
	} 
}
