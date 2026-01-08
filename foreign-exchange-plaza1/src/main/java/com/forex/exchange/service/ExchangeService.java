package com.forex.exchange.service;


import com.forex.exchange.model.ExchangeValue;
import com.forex.exchange.repository.ExchangeValueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeService {

	private final ExchangeValueRepository repository;

    // to inject port/environment info
    @Value("${server.port:unknown}")
    private String serverPort;

    public ExchangeService(ExchangeValueRepository repository) {
        this.repository = repository;
    }

    public Optional<ExchangeValue> findExchangeValue(String from, String to) {
        Optional<ExchangeValue> ev = repository.findByFromCurrencyAndToCurrency(from.toUpperCase(), to.toUpperCase());
        ev.ifPresent(e -> e.setEnvironment("port:" + serverPort));
        return ev;
    }
}