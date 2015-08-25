package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("classpath:context-*.xml");
	}

}
