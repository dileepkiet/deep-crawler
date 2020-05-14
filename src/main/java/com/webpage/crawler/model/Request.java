package com.webpage.crawler.model;

public class Request {
	private RequestStatus status;
	private String url;
	private Integer depth;
	private Result result;
	
	public Request(RequestStatus status, String url, Integer depth) {
		this.status = status;
		this.url = url;
		this.depth = depth;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
