package com.adtrack.error;

/**
 * This class throw Exception 
 * When Requested Data Not Found
 */
public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	  public NoDataFoundException(String notFound) {
			super("Requested Data " + notFound );
		}
}
