package com.forex.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ForeignExchangePlazaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForeignExchangePlazaApplication.class, args);
	}

}
