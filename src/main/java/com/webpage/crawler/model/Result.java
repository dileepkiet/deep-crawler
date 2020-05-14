package com.webpage.crawler.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	@JsonProperty("total_links")
	private int links;
	
	@JsonProperty("total_images")
	private int images;
	private List<Page> details;
	
	public int getLinks() {
		return links;
	}
	
	public void setLinks(int links) {
		this.links = links;
	}
	
	public int getImages() {
		return images;
	}
	
	public void setImages(int images) {
		this.images = images;
	}
	
	public List<Page> getDetails() {
		return details;
	}
	
	public void setDetails(List<Page> details) {
		this.details = details;
	}
	
}
