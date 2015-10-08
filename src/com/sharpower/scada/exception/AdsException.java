package com.sharpower.scada.exception;

public class AdsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AdsException( String errMessage){
		super(errMessage);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}