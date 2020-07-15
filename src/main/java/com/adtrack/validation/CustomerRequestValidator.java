package com.adtrack.validation;

import java.math.BigInteger;
import java.util.Optional;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.adtrack.dto.CustomerDataRequest;
import com.adtrack.entity.Customer;
import com.adtrack.repository.CustomerRepository;
import com.adtrack.repository.IpRepository;
import com.adtrack.repository.UserAgentRepository;

@Component
public class CustomerRequestValidator {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private IpRepository ipRepository;
	@Autowired
	private UserAgentRepository userAgentRepository;
	
	public boolean isVaild(CustomerDataRequest customer) {
		if(isValidCustomer(customer.getCustomerID()) &&  isvalidIp(customer.getRemoteIP())) {
			return true;
		}
		return false;
   }
	
	private boolean isValidCustomer(int customerId) {
		Optional<Customer> customer = getCustomer(customerId);
		if(customer.isPresent()) {
		  if(customer.get().getActive()==1 && 
				    !(userAgentRepository.existsById(customer.get().getName()))) {
			  return true;
		  }
		}
		return false;
	}
	
	@Cacheable("ipAddress")
	public boolean isvalidIp(String ip) {
		if(! getIpNumber(ip).equals(BigInteger.ZERO)) {
			return ! ipRepository.existsById(getIpNumber(ip));
		}
		return false;
	}
	
	private BigInteger getIpNumber(String ip) {
		if(InetAddressValidator.getInstance().isValidInet4Address(ip)) {
			return new BigInteger(ip.replaceAll("\\.", ""));
		}
		return BigInteger.ZERO;
	}
	
	@Cacheable("customerPresent")
	public Optional<Customer> getCustomer(int id) {
		return customerRepository.findById(id);
	}
}
