package com.adtrack.servicesImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.adtrack.dto.CustomerDataRequest;
import com.adtrack.dto.CustomerDataResponse;
import com.adtrack.entity.CustomerTrackInfo;
import com.adtrack.error.InvaildData;
import com.adtrack.repository.CustomerTrackerRepository;
import com.adtrack.services.CustomerService;
import com.adtrack.validation.CustomerRequestValidator;

@Service
public class CustomerServiceImpl  implements CustomerService{

	public static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	public static final long one = 1l;
	public static final long zero = 0l;
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private CustomerTrackerRepository customerTrackerRepo;
	@Autowired
	private CustomerRequestValidator requestValidator;

	@Override
	public CustomerTrackInfo save(CustomerDataRequest customerDataRequest) {

		List<CustomerTrackInfo> customerList = getCustomersDetailHourly(customerDataRequest);
		logger.info("Save Customer Request started");

		if (!customerList.isEmpty()) {
			return updateHourly(customerList.get(0));
		}
		CustomerTrackInfo customerTrackInfo = new CustomerTrackInfo();
		customerTrackInfo.setCustomerId(customerDataRequest.getCustomerID());
		customerTrackInfo.setTime(customerDataRequest.getTimestamp());

		if (requestValidator.isVaild(customerDataRequest)) {
			return saveVaildRequest(customerTrackInfo);
		}
		return saveInVaildRequest(customerTrackInfo);
	}


	@Cacheable("customersDeatailHourly")
	public List<CustomerTrackInfo> getCustomersDetailHourly(CustomerDataRequest customerDataRequest) {
		List<CustomerTrackInfo> customerHourlyList = new ArrayList<CustomerTrackInfo>();

		Long currentRequestTime = customerDataRequest.getTimestamp().getTime();
		logger.info("Current time of Customer Request " + customerDataRequest.getTimestamp());
		Long currentReqTimeBeforeHour = currentRequestTime - 3600000;
		logger.info("One houre before Customer Request time " + new Timestamp(currentReqTimeBeforeHour));

		customerHourlyList = customerTrackerRepo.findByCustomerIdAndTimeBetween(customerDataRequest.getCustomerID(),
				new Timestamp(currentReqTimeBeforeHour), customerDataRequest.getTimestamp());
		return customerHourlyList;
	}

	private CustomerTrackInfo saveVaildRequest(CustomerTrackInfo customerTrackInfo) {
		logger.info("Save vaild request of customer");
		customerTrackInfo.setRequestCount(one);
		customerTrackInfo.setInvalidCount(zero);

		return customerTrackerRepo.save(customerTrackInfo);
	}

	private CustomerTrackInfo saveInVaildRequest(CustomerTrackInfo customerTrackInfo) {
		logger.info("Save Invalid request of customer");
		customerTrackInfo.setRequestCount(one);
		customerTrackInfo.setInvalidCount(one);

		return customerTrackerRepo.save(customerTrackInfo);
	}

	private CustomerTrackInfo updateHourly(CustomerTrackInfo customerHourlyInfo) {
		logger.info("Updating  Customer request  ");
		CustomerTrackInfo custInformation = new CustomerTrackInfo();
		custInformation.setId(customerHourlyInfo.getId());
		custInformation.setCustomerId(customerHourlyInfo.getCustomerId());
		custInformation.setRequestCount(customerHourlyInfo.getRequestCount() + 1);
		custInformation.setInvalidCount(customerHourlyInfo.getInvalidCount() + 1);
		custInformation.setTime(customerHourlyInfo.getTime());

		return customerTrackerRepo.save(custInformation);
	}

	@Override
	public Optional<CustomerDataResponse> getCustomerReqDetailByDate(int id, String date) {

		List<CustomerTrackInfo>  customerlist = new ArrayList<CustomerTrackInfo>();
		CustomerDataResponse customerDataResponse = new CustomerDataResponse();
		String fromDatetime = date +" 00:00:00";
		String toDatetime   = date + " 23:59:59";

		logger.info("Getting customer info with id "+id +" FromDateTime "+fromDatetime+ " ToDateTime "+toDatetime);
		customerlist =  customerTrackerRepo.findByCustomerIdAndTimeBetween(id, 
				getTimestamp(fromDatetime), getTimestamp(toDatetime));
		if(!customerlist.isEmpty()) {
			long totalRequestSum = customerlist.stream().mapToLong(request -> request.getRequestCount()).sum();
			customerDataResponse.setCustomerId(id);
			customerDataResponse.setDate(date);
			customerDataResponse.setTotalNumberOfRequest(totalRequestSum);
		} 
		return Optional.of(customerDataResponse) ;
	}

	private Timestamp getTimestamp(String datetime) {
		try {
			Date dateformatted = dateFormatter.parse(datetime);
			long dateTimestamp = dateformatted.getTime();
			return new Timestamp(dateTimestamp);
		}
		catch (ParseException e) {
			logger.error("Date Formate issue "+datetime);
			throw new InvaildData("Date Formate should be dd-mm-yyyy");
		}	
	}
}
