package com.ing.modelbank.service;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.ing.modelbank.constants.ModelBankConstants;
import com.ing.modelbank.dto.RegistrationRequest;
import com.ing.modelbank.dto.RegistrationResponse;
import com.ing.modelbank.entity.Customer;
import com.ing.modelbank.exception.UserExistException;
import com.ing.modelbank.repository.CustomerRepository;
import com.ing.modelbank.util.ModelBankUtils;

/**
 * @author KiruthikaK
 * @since 14/10/2019 this class includes the method for registration
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	CustomerRepository customerRepository;

	ModelBankUtils modelBankUtils;
	Customer customer;
	RegistrationResponse registrationResponse;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	/**
	 * @param RegistrationRequest
	 * @return RegistrationResponse Based on email validation the person can
	 *         register with his unique mail
	 */

	@Transactional
	public RegistrationResponse registration(RegistrationRequest registrationRequest)
			throws NoSuchAlgorithmException, ParseException {

		LOGGER.info(ModelBankConstants.REGISTRATION_SERVICE);

		Optional<Customer> customerr = customerRepository.findByEmail(registrationRequest.getEmail());
		if (customerr.isPresent()) {
			throw new UserExistException(ModelBankConstants.REGITRATION_DONE);
		}

		modelBankUtils = new ModelBankUtils();
		String password = modelBankUtils.generatePassword(registrationRequest.getCustomerName());
		customer = new Customer();
		customer.setDob(registrationRequest.getDob());
		customer.setCity(registrationRequest.getCity());
		customer.setDoorNo(registrationRequest.getDoorNo());
		customer.setPinCode(registrationRequest.getPinCode());
		customer.setStreet(registrationRequest.getStreet());
		customer.setAge(modelBankUtils.ageCalculation(registrationRequest.getDob()));
		customer.setCustomerName(registrationRequest.getCustomerName());
		customer.setEmail(registrationRequest.getEmail());
		customer.setMobileNo(registrationRequest.getMobileNo());
		customer.setPassword(password);
		customerRepository.save(customer);

		modelBankUtils.sendSms(customer.getCustomerName(), customer.getPassword(), customer.getMobileNo());
		registrationResponse = new RegistrationResponse();
		registrationResponse.setCustomerId(customer.getCustomerId());

		return registrationResponse;
	}

}
