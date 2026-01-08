package com.forex.exchange.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forex.exchange.model.ExchangeValue;
import com.forex.exchange.service.ExchangeService;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {
	
	private final ExchangeService exchangeService;

    public CurrencyExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    // Example: GET /currency-exchange/from/USD/to/INR
    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<?> retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        Optional<ExchangeValue> ev = exchangeService.findExchangeValue(from, to);
        return ev.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Exchange_rate not found for " + from + " to " + to));

    }
}


