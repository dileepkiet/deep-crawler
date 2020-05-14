package com.webpage.crawler.dao;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webpage.crawler.TokenGenerator;
import com.webpage.crawler.model.Request;
import com.webpage.crawler.model.RequestStatus;
import com.webpage.crawler.model.Result;

@Repository
public class CrawlRequestImpl implements CrawlRequest{
	
	@Autowired
	private TokenGenerator tokenGenerator;

	private ConcurrentMap<String, Request> requests = new ConcurrentHashMap<String, Request>();
	
	public boolean isTokenExists(String token) {
		return requests.containsKey(token);
	}
	
	public Request getRequestByToken(String token) {
		return requests.getOrDefault(token, null);
	}
	
	public void updateStatusByToken(String token, RequestStatus status) {
		Request request = getRequestByToken(token);
		if (request != null) {
			request.setStatus(status);
		}
	}
	
	public String getUrlByToken(String token) {
		Request request = requests.getOrDefault(token, null);
		if (request != null) {
			return request.getUrl();
		}
		return null;
	}
	
	public int getDepthByToken(String token) {
		Request request = requests.getOrDefault(token, null);
		if (request != null) {
			return request.getDepth();
		}
		return 0;
	}
	
	public String addRequest(String url, Integer depth) {
		String randomString = null;
		while (true) {
			randomString = tokenGenerator.getRandomToken();
			if (!requests.containsKey(randomString)) break;
		}
		
		requests.putIfAbsent(randomString, new Request(RequestStatus.SUBMITTED, url, depth));
		return randomString;
	}
	
	public RequestStatus getStatus(String token) {
		Request request = requests.getOrDefault(token, null);
		if (request != null) {
			return request.getStatus();
		}
		return null;
	}
	
	public Result getResult(String token) {
		Request request = requests.getOrDefault(token, null);
		if (request != null) {
			return request.getResult();
		}
		return null;
	}
	
	public void updateResultByToken(String token, Result result) {
		Request request = requests.getOrDefault(token, null);
		if (request != null) {
			request.setResult(result);
		}
	}
}
