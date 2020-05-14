package com.webpage.crawler.model;

public class Response<T> {
	private boolean status;
	private String error;
	private T result;
	
	public Response(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public T getResult() {
		return result;
	}
	
	public void setResult(T result) {
		this.result = result;
	}
	
}
