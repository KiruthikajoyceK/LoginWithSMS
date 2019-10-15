package com.ing.modelbank.service;

import java.security.MessageDigest;
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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
		String password = generatePassword(registrationRequest.getCustomerName());
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
		Customer customerrr = customerRepository.save(customer);

		sendSms(customerrr.getCustomerName(), customerrr.getPassword(), customerrr.getMobileNo());
		registrationResponse = new RegistrationResponse();
		registrationResponse.setCustomerId(customerrr.getCustomerId());

		return registrationResponse;
	}

	public String generatePassword(String passwordToHash) throws NoSuchAlgorithmException {

		String generatedPassword = null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passwordToHash.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		generatedPassword = sb.toString();
		return generatedPassword.substring(0, Integer.parseInt(ModelBankConstants.PASSWORD_LENGTH));
	}

	public String sendSms(String userName, String passWord, Long phoneNumber) {

		Twilio.init(ModelBankConstants.ACCOUNT_SID, ModelBankConstants.AUTH_TOKEN);
		Message message = Message
				.creator(new PhoneNumber(ModelBankConstants.MOBILE + phoneNumber),
						new PhoneNumber(ModelBankConstants.TWILIO), userName + ModelBankConstants.INFO + passWord)
				.create();

		return message.getBody();
	}

}
