package com.ing.modelbank.controller;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ing.modelbank.dto.RegistrationRequest;
import com.ing.modelbank.dto.RegistrationResponse;
import com.ing.modelbank.service.RegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

	@InjectMocks
	RegistrationController registrationController;

	@Mock
	RegistrationService registrationService;

	RegistrationResponse registrationResponse;
	RegistrationRequest registrationRequest;

	@Before
	public void setup() {

		registrationRequest = new RegistrationRequest();
		registrationRequest.setCustomerName("kiruthika");
		registrationRequest.setEmail("kiruthika@gmail.com");

		registrationResponse = new RegistrationResponse();
		registrationResponse.setCustomerId(1);
		registrationResponse.setStatusCode(201);

	}

	@Test
	public void testRegistration() throws NoSuchAlgorithmException, ParseException {
		Mockito.when(registrationService.registration(registrationRequest)).thenReturn(registrationResponse);
		ResponseEntity<RegistrationResponse> actual = registrationController.registration(registrationRequest);
		ResponseEntity<RegistrationResponse> expected = new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
		assertEquals(expected.getStatusCode().value(), actual.getStatusCodeValue());

	}

}
