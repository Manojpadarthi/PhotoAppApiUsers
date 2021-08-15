package com.example.users.exception;

import java.util.Date;

public class ErrorMessage {
	
	private String message;
	
	private Date date;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ErrorMessage(String message, Date date) {
		
		this.message = message;
		this.date = date;
	}

	//no args constructor
	public ErrorMessage() {
		
	}
	
	

}
