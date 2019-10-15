package com.ing.modelbank.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.ing.modelbank.dto.LoginRequest;
import com.ing.modelbank.dto.LoginResponse;
import com.ing.modelbank.entity.Customer;
import com.ing.modelbank.exception.CommonException;
import com.ing.modelbank.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

	@InjectMocks
	LoginServiceImpl loginServiceImpl;

	@Mock
	CustomerRepository customerRepository;

	LoginRequest loginRequest;

	LoginResponse loginResponse;

	Customer customer;

	@Before
	public void setup() {
		loginRequest = new LoginRequest();
		loginRequest.setEmail("kiruthika@gmail.com");
		loginRequest.setPassword("joyce");
		loginResponse = new LoginResponse();
		loginResponse.setCustomerId(1);
		loginResponse.setStatusCode(200);

		customer = new Customer();
		customer.setCustomerId(1);
	}

	@Test
	public void testLogin() {
		Mockito.when(customerRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(customer));
		LoginResponse actual = loginServiceImpl.login(loginRequest);
		Assert.assertEquals(loginResponse.getCustomerId(), actual.getCustomerId());

	}

	@Test(expected = CommonException.class)
	public void testLogin1() {
		Mockito.when(customerRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.absent());
		LoginResponse actual = loginServiceImpl.login(loginRequest);
		Assert.assertEquals(loginResponse.getCustomerId(), actual.getCustomerId());

	}
}
