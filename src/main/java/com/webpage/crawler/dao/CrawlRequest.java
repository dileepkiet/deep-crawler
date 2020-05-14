package com.webpage.crawler.dao;

import com.webpage.crawler.model.Request;
import com.webpage.crawler.model.RequestStatus;
import com.webpage.crawler.model.Result;

public interface CrawlRequest {
	public boolean isTokenExists(String token);
	public Request getRequestByToken(String token);
	public void updateStatusByToken(String token, RequestStatus status);
	public String getUrlByToken(String token);
	public int getDepthByToken(String token);
	public String addRequest(String url, Integer depth);
	public RequestStatus getStatus(String token);
	public Result getResult(String token);
	public void updateResultByToken(String token, Result result);

}
