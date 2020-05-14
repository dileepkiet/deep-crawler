package com.webpage.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.webpage.crawler.dao.CrawlRequest;
import com.webpage.crawler.model.Page;
import com.webpage.crawler.model.RequestStatus;
import com.webpage.crawler.model.Result;

@Service
public class CrawlReceiverSevice {
	
	private Logger logger = LoggerFactory.getLogger(CrawlReceiverSevice.class);
	
	@Autowired
	private CrawlRequest crawlRequest;
	
	@JmsListener(destination = "crawling_queue", containerFactory = "jmsContainerFactory")
	public void processRequest(String token) {
		if (crawlRequest.isTokenExists(token)) {
			crawlRequest.updateStatusByToken(token, RequestStatus.IN_PROGRESS);
			try {
				List<Page> pages = deepCrawl(crawlRequest.getUrlByToken(token), crawlRequest.getDepthByToken(token), null);
				
				System.out.println(pages);
				if (pages != null) {
					
					int totalLinks = (int) pages.stream().count();
					int totalImages = pages.stream()
							.collect(Collectors.summingInt(Page::getImageCount));
					
					Result result = new Result();
					result.setLinks(totalLinks);
					result.setImages(totalImages);
					result.setDetails(pages);
					
					crawlRequest.updateResultByToken(token, result);
					crawlRequest.updateStatusByToken(token, RequestStatus.PROCESSED);
				}
			}catch (Exception e) {
				logger.error("Request failed for token {}", token);
				crawlRequest.updateStatusByToken(token, RequestStatus.FAILED);
			}
			System.out.println("Token done: " + token);
		}
	}
	
	public List<Page> deepCrawl(String url, int depth, List<String> processedUrls) {
		if (depth < 0) return null;
		List<Page> pages = new ArrayList<Page>();
		try {
			if (processedUrls == null) {
				processedUrls = new ArrayList<String>();
			}
			List<String> alreadyProcessed = Optional.of(processedUrls).orElse(new ArrayList<>());
			if (!alreadyProcessed.contains(url)) {
				Page page = crawl(url);
				if (page != null) {
					logger.error("page {}", page);
					pages.add(page);
					if (depth > 1) {
						page.getLinks().stream().forEach(link -> {
							try {
								pages.addAll(deepCrawl(link, depth - 1, alreadyProcessed));
							}catch(Exception e) {
								logger.error(e.getMessage());
							}
						});
					}
				}
			}

		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		
		return pages;
	}
	
	public Page crawl(String url) {
		Page page = null;
		try {
			Document document = Jsoup.connect(url).get();
			Elements images = document.getElementsByTag("img");
			String title = document.title();
			List<String> links = document.select("a[href]")
					.stream()
					.map(link -> link.attr("abs:href"))
					.collect(Collectors.toList());
			page = new Page();
			page.setTitle(title);
			page.setLink(url);
			page.setLinks(links);
			page.setImageCount(images.size());
		}catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return page;
	}
}
