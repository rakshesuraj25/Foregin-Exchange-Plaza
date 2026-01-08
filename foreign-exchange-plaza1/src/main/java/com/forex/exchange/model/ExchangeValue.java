package com.forex.exchange.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "exchange_value")
@Data
public class ExchangeValue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "currency_from", nullable = false, length = 3)
	private String fromCurrency;

	@Column(name = "currency_to", nullable = false, length = 3)
	private String toCurrency;

	@Column(name = "conversion_multiple", nullable = false, precision = 19, scale = 6)
	private BigDecimal conversionMultiple;

	@Column(name = "environment")
	private String environment;

	public ExchangeValue() {
	}

	public ExchangeValue(String fromCurrency, String toCurrency, BigDecimal conversionMultiple) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.conversionMultiple = conversionMultiple;
	}

	public Object setEnvironment(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
