package com.cattles.cloudplatforms;

import java.util.UUID;

public class MakeUUID {
	public static void main(String[] args) {
		String[] ss = getUUID(1);
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
	}
	/**
	 * get one single UUID string 
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s;
	}
	/**
	 * get a serial number of UUID string
	 * @param number
	 * @return
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
}
