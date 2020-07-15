package com.adtrack.error;

/**
 * This class throw Exception 
 * When Requested Data Wrong 
 */
public class InvaildData extends RuntimeException {
	
	private static final long serialVersionUID = 2769009285570086378L;

	public InvaildData(String data) {
		super("Wrong Input Data "+data);	
	}
}
