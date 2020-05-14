package com.webpage.crawler;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
	
	@Autowired
	private Random random;
	
	public String getRandomToken() {
		String supportedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder token = new StringBuilder();
		
		while(token.length() < 10) {
			int index = (int) (random.nextFloat() * supportedChars.length());
			token.append(supportedChars.charAt(index));
		}
		
		return token.toString();
	}
}
