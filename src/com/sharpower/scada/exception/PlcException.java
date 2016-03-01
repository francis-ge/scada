package com.sharpower.scada.exception;

public class PlcException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PlcException( String errMessage){
		super(errMessage);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
