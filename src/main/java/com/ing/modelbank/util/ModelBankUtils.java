package com.ing.modelbank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import com.ing.modelbank.constants.ModelBankConstants;

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

}
