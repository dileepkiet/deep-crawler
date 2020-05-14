package com.webpage.crawler.service;

import com.webpage.crawler.model.RequestStatus;
import com.webpage.crawler.model.Result;
import com.webpage.crawler.model.Token;

public interface CrawlService {
	public Token submit(String url, Integer depth);
	public RequestStatus getStatus(String token);
	public Result getResult(String token);
	public boolean isTokenExists(String token);
}
