package com.adtrack.servicesImpl;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.adtrack.dto.CustomerDataRequest;
import com.adtrack.dto.CustomerDataResponse;
import com.adtrack.entity.CustomerTrackInfo;
import com.adtrack.repository.CustomerTrackerRepository;
import com.adtrack.validation.CustomerRequestValidator;

/*Service Class Test we can add more test */
public class CustomerServiceImplTest {
	
	@Mock
	private CustomerTrackerRepository customerTrackerRepo;
	@Mock
	private CustomerRequestValidator requestValidator;
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);	
	}
	
	@Test
	public void testSaveVaildRequest() {
	 Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
	CustomerDataRequest customerDataReq = new CustomerDataRequest(1,2,"aaaa-bba-cc-123","123.234.56.78",timestamp);
	CustomerTrackInfo expectedCustomer = new CustomerTrackInfo(1,1,timestamp,1l,1l);
	Mockito.when(requestValidator.isVaild(Mockito.any(CustomerDataRequest.class))).thenReturn(true);
	Mockito.when(customerTrackerRepo.save(Mockito.any(CustomerTrackInfo.class))).thenReturn(expectedCustomer);
	assertEquals(expectedCustomer, customerService.save(customerDataReq));	
	}
	
	@Test
	public void testSaveVaildRequestHourly() {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		CustomerTrackInfo customerTrackData = new CustomerTrackInfo(1,1,timestamp,2l,1l);
		Mockito.when(customerTrackerRepo.findByCustomerIdAndTimeBetween(Mockito.any(
				  Integer.class),Mockito.any(Timestamp.class),Mockito.any(Timestamp.class)))
				  .thenReturn(Collections.singletonList(customerTrackData));	
	
	CustomerDataRequest customerDataReq = new CustomerDataRequest(1,2,"aaaa-bba-cc-123","211.120.20.45",timestamp);
	CustomerTrackInfo expectedCustomer = new CustomerTrackInfo(1,1,timestamp,2l,1l);

	Mockito.when(requestValidator.isVaild(Mockito.any(CustomerDataRequest.class))).thenReturn(true);
	Mockito.when(customerTrackerRepo.save(Mockito.any(CustomerTrackInfo.class))).thenReturn(expectedCustomer);
	assertEquals(expectedCustomer, customerService.save(customerDataReq));	
	}
	
	@Test
	public void testSaveInVaildRequest() {
	Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
	CustomerDataRequest customerDataReq = new CustomerDataRequest(1,2,"aaaa-bba-cc-123","211.120.23.45",timestamp);
	CustomerTrackInfo expectedCustomer = new CustomerTrackInfo(1,1,timestamp,1l,1l);
	Mockito.when(requestValidator.isVaild(Mockito.any(CustomerDataRequest.class))).thenReturn(false);
	Mockito.when(customerTrackerRepo.save(Mockito.any(CustomerTrackInfo.class))).thenReturn(expectedCustomer);
	assertEquals(expectedCustomer, customerService.save(customerDataReq));	
	}
	
	@Test
	public void testGetCustomerDeatilsHourly() {
	CustomerTrackInfo customerData = new CustomerTrackInfo(1,1,new Timestamp(1500000000),5l,10l);
	Mockito.when(customerTrackerRepo.findByCustomerIdAndTimeBetween(Mockito.any(
			  Integer.class),Mockito.any(Timestamp.class),Mockito.any(Timestamp.class)))
			  .thenReturn(Collections.singletonList(customerData));
	CustomerTrackInfo customerExpected = new CustomerTrackInfo(1,1,new Timestamp(1500000000),5l,10l);
	List<CustomerTrackInfo> expectedCustomerList = new ArrayList<CustomerTrackInfo>();
	expectedCustomerList.add(customerExpected);
	CustomerDataRequest customerDataRequest =new CustomerDataRequest(1, 1, "aaaaa-bbbb-cc-213", "211.120.34.32", new Timestamp(1500000000));
	assertEquals(expectedCustomerList, customerService.getCustomersDetailHourly(customerDataRequest));	
	}
	
	@Test
	public void testgetCustomerReqDetailByDate() {
	CustomerTrackInfo customerData = new CustomerTrackInfo(1,2,Timestamp.valueOf(LocalDateTime.now()),5l,2l);
	Mockito.when(customerTrackerRepo.findByCustomerIdAndTimeBetween(Mockito.any(
			  Integer.class),Mockito.any(Timestamp.class),Mockito.any(Timestamp.class)))
			  .thenReturn(Collections.singletonList(customerData));
	CustomerDataResponse customerDataRes = new CustomerDataResponse();
	customerDataRes.setCustomerId(2);
	customerDataRes.setDate(LocalDate.now().toString());
	customerDataRes.setTotalNumberOfRequest(5);
	Optional<CustomerDataResponse> expectedCustomer = Optional.of(customerDataRes);
	assertEquals(expectedCustomer, customerService.getCustomerReqDetailByDate(2, LocalDate.now().toString()));	
	}
}
