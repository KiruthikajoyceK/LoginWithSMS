package com.ing.modelbank.service;

import com.ing.modelbank.dto.LoginRequest;
import com.ing.modelbank.dto.LoginResponse;

public interface LoginService {

	public LoginResponse login(LoginRequest loginRequest);

}
