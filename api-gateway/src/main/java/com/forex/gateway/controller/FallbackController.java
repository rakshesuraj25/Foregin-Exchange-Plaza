package com.forex.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
	 @RequestMapping("/fallback")
	    public Mono<ResponseEntity<String>> fallback() {
	        return Mono.just(ResponseEntity.status(503).body("Service is temporarily unavailable - fallback response from Gateway"));
	    }
	}
	