/**
 * 文件名：OpennebulaException.java
 * 创建时间：Jul 28, 2011
 * 创建者：xiong rong
 */
package com.cattles.exception;

/**
 * @author xiong rong
 *
 */
public class OpennebulaException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1414505232444617324L;

	public OpennebulaException(){
		super();
	}
	
	public OpennebulaException(String msg){
		super(msg);
	}
	
	public OpennebulaException(Throwable cause){
		super(cause);
	}
	
	public OpennebulaException(String msg,Throwable cause){
		super(msg,cause);
	}
}
