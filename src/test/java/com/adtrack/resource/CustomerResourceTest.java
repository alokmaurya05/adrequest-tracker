package com.adtrack.resource;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.adtrack.AdtrackApplication;
import com.adtrack.dto.CustomerDataRequest;
import com.adtrack.dto.CustomerDataResponse;
import com.adtrack.entity.CustomerTrackInfo;
import com.adtrack.resources.CustomerResource;
import com.adtrack.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*This class for resource test
 * We can add more test case also. This is just a basic.
 * */
@SpringBootTest(classes={AdtrackApplication.class})
@RunWith(SpringRunner.class)
public class CustomerResourceTest {

	private final String URL = "/api/v1/customer";
	MockMvc mockMvc;
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	CustomerResource customerResource; 
	@MockBean
	CustomerService customerService;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(this.customerResource).build();// Standalone context
	}

	@Test
	public void saveCustomer() throws Exception {
		LocalDateTime datetime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(datetime);
		CustomerTrackInfo customerTrackData = new CustomerTrackInfo(1,1,timestamp,2l,0l);
		CustomerDataRequest customerDataReq = new CustomerDataRequest(1,2,"aaaa-bba-cc-123","211.120.20.45",timestamp);

		when(customerService.save(any(CustomerDataRequest.class))).thenReturn(customerTrackData);
		mockMvc.perform(post(URL).content(mapper.writeValueAsBytes(customerDataReq)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.customerId", is(1)))
		.andExpect(jsonPath("$.time", is(customerDataReq.getTimestamp().getTime())))
		.andExpect(jsonPath("$.requestCount", is(2)))
		.andExpect(jsonPath("$.invalidCount", is(0))) ;
	}

	@Test
	public void testgetCutomerByDate() throws Exception {
		CustomerDataResponse customerDataRes =new CustomerDataResponse(1,"2020-07-14",2l) ;
		when(customerService.getCustomerReqDetailByDate(Mockito.anyInt(), Mockito.anyString()))
		.thenReturn(Optional.of(customerDataRes));

		mockMvc.perform(get(URL+"/1/2020-07-14").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerId", is(1)))
		.andExpect(jsonPath("$.date", is("2020-07-14")))
		.andExpect(jsonPath("$.totalNumberOfRequest", is(2)));
	} 
}
