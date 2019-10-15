package com.ing.modelbank.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Optional;
import com.ing.modelbank.dto.RegistrationRequest;
import com.ing.modelbank.dto.RegistrationResponse;
import com.ing.modelbank.entity.Customer;
import com.ing.modelbank.exception.UserExistException;
import com.ing.modelbank.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

	@InjectMocks
	RegistrationServiceImpl registrationServiceImpl;

	@Mock
	CustomerRepository customerRepository;

	RegistrationResponse registrationResponse;
	RegistrationRequest registrationRequest;

	Customer customer;
	Customer customerrr;

	@Value("${password.length}")
	private String passwordLength;

	@Before
	public void setup() {

		registrationRequest = new RegistrationRequest();
		registrationRequest.setCustomerName("kiruthika");
		registrationRequest.setEmail("kiruthika@gmail.com");
		registrationRequest.setDob("1997/09/08");
		registrationRequest.setMobileNo(7867947568L);

		customer = new Customer();
		customer.setCustomerId(1);
		customer.setMobileNo(7867947568L);
		customer.setPassword("f704a5fe");
		customer.setCustomerName("kiruthika");
		customerrr = new Customer();
		customerrr.setMobileNo(customer.getMobileNo());
		customerrr.setPassword(customer.getPassword());
		customerrr.setCustomerName(customer.getCustomerName());
		registrationResponse = new RegistrationResponse();
		registrationResponse.setStatusCode(201);
	}

	@Test(expected = UserExistException.class)
	public void testRegistration() throws NoSuchAlgorithmException, ParseException {
		customer.setCustomerId(1);
		customer.setCustomerName(registrationRequest.getCustomerName());
		Mockito.when(customerRepository.findByEmail(registrationRequest.getEmail())).thenReturn(Optional.of(customer));
		RegistrationResponse actual = registrationServiceImpl.registration(registrationRequest);
		Assert.assertEquals(registrationResponse.getCustomerId(), actual.getCustomerId());

	}

}
