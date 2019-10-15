package com.ing.modelbank.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.ing.modelbank.constants.ModelBankConstants;
import com.ing.modelbank.dto.LoginRequest;
import com.ing.modelbank.dto.LoginResponse;
import com.ing.modelbank.entity.Customer;
import com.ing.modelbank.exception.CommonException;
import com.ing.modelbank.repository.CustomerRepository;

/**
 * @author KiruthikaK
 * 
 *         This method contains for login based on userName and password
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * @param loginRequest
	 * @return LoginResponse
	 * 
	 *         This method for login based on validation ,suppose customer is not
	 *         present means it will throw the exception
	 */
	@Transactional
	public LoginResponse login(LoginRequest loginRequest) {
		LOGGER.info(ModelBankConstants.LOGIN_SERVICE);

		Optional<Customer> customer = customerRepository.findByEmailAndPassword(loginRequest.getEmail(),
				loginRequest.getPassword());

		if (!customer.isPresent()) {
			LOGGER.error(ModelBankConstants.CUSTOMER_NOT_FOUND);
			throw new CommonException(ModelBankConstants.CUSTOMER_NOT_FOUND);
		}
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setCustomerId(customer.get().getCustomerId());
		return loginResponse;
	}

}
