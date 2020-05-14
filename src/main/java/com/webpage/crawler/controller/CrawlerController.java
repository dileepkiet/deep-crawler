package com.webpage.crawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webpage.crawler.model.RequestStatus;
import com.webpage.crawler.model.Response;
import com.webpage.crawler.model.Result;
import com.webpage.crawler.model.Token;
import com.webpage.crawler.service.CrawlService;

@RestController
@RequestMapping("/crawl")
public class CrawlerController {
	
	private Logger logger = LoggerFactory.getLogger(CrawlerController.class);
	
	@Autowired
	private CrawlService crawlService;

	@PostMapping("/submit")
	public Response<Token> crawlSubmit(@RequestParam(name = "url") String url, 
			@RequestParam(name = "depth") Integer depth) {
		Response<Token> response = new Response<Token>(false);
		if (url != null && depth >= 0) {
			Token token = crawlService.submit(url, depth);
			if (token == null) {
				response.setStatus(false);
				logger.error("Unable to queue request, please try later!");
				response.setError("Unable to queue request, please try later!");;
			}else {
				response.setStatus(true);
				response.setResult(token);
				logger.debug("Replying with token {}", token);
			}
		}else {
			logger.error("Request not accepted as invalid input provided!");
			response.setError("Request not accepted as invalid input provided!");
		}
		return response;
	}
	
	@GetMapping("/status/{token}")
	public Response<RequestStatus> getStatus(@PathVariable String token) {
		Response<RequestStatus> response = new Response<RequestStatus>(false);
		if (token.length() != 10 || !crawlService.isTokenExists(token)) {
			response.setError("Invalid token!");
			logger.error("Invald token: {}", token);
		}else {
			RequestStatus status = crawlService.getStatus(token);
			if (status == null) {
				response.setStatus(false);
				response.setError("Invalid status");
				logger.error("Invalid status for token: {}", token);
			}else {
				response.setStatus(true);
				response.setResult(status);
			}
		}
		return response;
	}
	
	@GetMapping("/result/{token}")
	public Response<Result> getResult(@PathVariable String token) {
		Response<Result> response = new Response<Result>(false);
		if (token.length() != 10 || !crawlService.isTokenExists(token)) {
			response.setError("Invalid token!");
			logger.error("Invald token: {}", token);
		}else if(!crawlService.getStatus(token).equals(RequestStatus.PROCESSED)) {
			response.setError("Request is still pending for processing!");
			logger.error("Request is still pending for processing for token {}", token);
		}else {
			Result result = crawlService.getResult(token);
			if (result == null) {
				response.setStatus(false);
				response.setError("Result not found!");
				logger.error("Result not found for token {}", token);
			}else {
				response.setStatus(true);
				response.setResult(result);
			}
		}
		return response;
	}
}
