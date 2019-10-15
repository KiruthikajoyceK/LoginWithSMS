package com.ing.modelbank.controller;

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
import com.ing.modelbank.dto.LoginRequest;
import com.ing.modelbank.dto.LoginResponse;
import com.ing.modelbank.service.LoginService;


/**
 * @since 14/10/2019
 * @author KiruthikaK
 * 
 * This class contains login method
 *
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	/**
	 * @param loginRequest
	 * @return LoginResponse
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
	{
		LOGGER.info(ModelBankConstants.LOGIN_CONTROLLER);
		
		LoginResponse loginResponse=loginService.login(loginRequest);
		
		loginResponse.setStatusCode(HttpStatus.OK.value());
		loginResponse.setStatusMessage(ModelBankConstants.LOGIN_SUCCESS);
		
		return new ResponseEntity<>(loginResponse,HttpStatus.OK);
		
	}


}
