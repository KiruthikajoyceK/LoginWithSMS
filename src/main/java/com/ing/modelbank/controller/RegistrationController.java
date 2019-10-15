package com.ing.modelbank.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ing.modelbank.constants.ModelBankConstants;
import com.ing.modelbank.dto.RegistrationRequest;
import com.ing.modelbank.dto.RegistrationResponse;
import com.ing.modelbank.service.RegistrationService;

/**
 * This class contains method for register the user
 * 
 * @since 14/10/2019
 * @author KiruthikaK
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

	/**
	 * This method used for register a user with his details and send the
	 * registration success message
	 * 
	 * @param registrationRequest
	 * @return RegistrationResponse
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	@PostMapping("/registration")

	public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest registrationRequest)
			throws NoSuchAlgorithmException, ParseException {

		LOGGER.info(ModelBankConstants.REGISTRATION_CONTROLLER);
		RegistrationResponse registrationResponse = registrationService.registration(registrationRequest);
		registrationResponse.setStatusCode(HttpStatus.CREATED.value());
		registrationResponse.setStatusMessage(ModelBankConstants.SUCCESS);
		return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);

	}
}
