package com.forex.conversion.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.forex.conversion.model.CurrencyConversion;

//The name must match the spring.application.name of your exchange service
@FeignClient(name = "foreign-exchange-plaza1") // registered name in Eureka
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to,
            @RequestHeader(value = "X-Auth-User", required = false) String user
    );
}