package com.ing.modelbank.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import com.ing.modelbank.constants.ModelBankConstants;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class ModelBankUtils {

	public int ageCalculation(String dob) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(ModelBankConstants.FORMAT);
		Date d = sdf.parse(dob);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		LocalDate l1 = LocalDate.of(year, month, date);
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		return diff1.getYears();
	}

	public String sendSms(String userName, String passWord, Long phoneNumber) {

		Twilio.init(ModelBankConstants.ACCOUNT_SID, ModelBankConstants.AUTH_TOKEN);
		Message message = Message
				.creator(new PhoneNumber(ModelBankConstants.MOBILE + phoneNumber),
						new PhoneNumber(ModelBankConstants.TWILIO), userName + ModelBankConstants.INFO + passWord)
				.create();

		return message.getBody();
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

}
