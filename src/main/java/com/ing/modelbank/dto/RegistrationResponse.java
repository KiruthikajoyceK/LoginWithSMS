package com.ing.modelbank.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationResponse {

	private int customerId;
	private String statusMessage;
	private int statusCode;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
