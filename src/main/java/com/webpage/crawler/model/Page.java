package com.webpage.crawler.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page {
	@JsonProperty("page_title")
	private String title;
	
	@JsonProperty("page_link")
	private String link;
	
	@JsonProperty("image_count")
	private Integer imageCount;
	
	@JsonIgnore
	private List<String> links;
	
	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Integer getImageCount() {
		return imageCount;
	}
	
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}

	@Override
	public String toString() {
		return "Page [title=" + title + ", link=" + link + ", imageCount=" + imageCount + ", links=" + links + "]";
	}
}
