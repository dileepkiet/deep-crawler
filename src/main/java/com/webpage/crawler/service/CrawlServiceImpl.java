package com.webpage.crawler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.webpage.crawler.dao.CrawlRequest;
import com.webpage.crawler.model.RequestStatus;
import com.webpage.crawler.model.Result;
import com.webpage.crawler.model.Token;

@Service
public class CrawlServiceImpl implements CrawlService{
	
	private Logger logger = LoggerFactory.getLogger(CrawlServiceImpl.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private CrawlRequest crawlRequest;

	@Override
	public Token submit(String url, Integer depth) {
		String token = crawlRequest.addRequest(url, depth);
		try {
			jmsTemplate.convertAndSend("crawling_queue", token);
			return new Token(token);
		}catch(Exception e) {
			logger.error("Unable to queue request!");
		}
		return null;
	}

	@Override
	public RequestStatus getStatus(String token) {
		return crawlRequest.getStatus(token);
	}

	@Override
	public Result getResult(String token) {
		return crawlRequest.getResult(token);
	}

	@Override
	public boolean isTokenExists(String token) {
		return crawlRequest.isTokenExists(token);
	}
}
