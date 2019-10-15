package com.ing.modelbank.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import com.ing.modelbank.dto.RegistrationRequest;
import com.ing.modelbank.dto.RegistrationResponse;

public interface RegistrationService {

	public RegistrationResponse registration(RegistrationRequest registrationRequest) throws NoSuchAlgorithmException, ParseException ;
}
