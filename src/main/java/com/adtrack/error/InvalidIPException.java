package com.adtrack.error;

/**
 * This class throw Exception 
 * When Requested Data in IP blackList 
 */
public class InvalidIPException extends RuntimeException {
	
	private static final long serialVersionUID = 2769009288670086378L;

	public InvalidIPException(String ip) {
		super("Invalid IP Address"+ip);	
	}

}
