package com.example.util;

public class Logger {
	
	private final static String LOG_OUTPUT_LINE_BREAKER = "============================";
	
	private static void outputPreLoggerMsg(String className) {
		System.out.println(LOG_OUTPUT_LINE_BREAKER);
		System.out.println("Class Name is : " + className);
	}
	
	public static void log(String className, String... msgs) {
		outputPreLoggerMsg(className);
		for(String msg : msgs) {
			System.out.print(msg + ", ");
		}
	}

}
