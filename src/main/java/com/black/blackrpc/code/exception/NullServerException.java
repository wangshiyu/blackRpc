package com.black.blackrpc.code.exception;
/**
 * 无该服务
 * @author Administrator
 *
 */
public class NullServerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullServerException() {
	        super();
	}

	public NullServerException(String message) {
	        super(message);
	}
}
