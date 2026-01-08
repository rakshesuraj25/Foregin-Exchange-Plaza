package com.forex.conversion.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.forex.conversion.model.CurrencyConversion;
import com.forex.conversion.proxy.CurrencyExchangeProxy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    // Resilience4j annotations:
    @CircuitBreaker(name = "currencyConversionCB", fallbackMethod = "fallbackConversion")
    @Retry(name = "currencyConversionRetry")
    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity,
            @RequestHeader(value = "X-Auth-User", required = false) String user
    ) {
        CurrencyConversion exchangeValue = proxy.retrieveExchangeValue(from, to, user);

        CurrencyConversion response = new CurrencyConversion();
        response.setId(exchangeValue.getId());
        response.setFrom(from);
        response.setTo(to);
        response.setConversionMultiple(exchangeValue.getConversionMultiple());
        response.setQuantity(quantity);
        response.setTotalCalculatedAmount(quantity.multiply(exchangeValue.getConversionMultiple()));
        response.setEnvironment(exchangeValue.getEnvironment() + " via Feign");

        return response;
    }

    // 🔁 Fallback method when Feign or service call fails
    public CurrencyConversion fallbackConversion(String from, String to, BigDecimal quantity, String user, Throwable ex) {
        System.out.println("Fallback triggered due to: " + ex.getMessage());

        return new CurrencyConversion(
                0L,
                from,
                to,
                BigDecimal.ZERO,
                quantity,
                BigDecimal.ZERO,
                "Fallback: Exchange Service Unavailable"
        );
    }
}
