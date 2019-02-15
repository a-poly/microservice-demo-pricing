package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class PricingController {

	@Autowired PricingService svc;
	
	/**
	 * Get a specific product:
	 */
	@GetMapping("/pricings/{code}")
	public Item getShippingCosts( @PathVariable String code) throws Exception {
		return svc.calculatePricing(code);
	}

	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(HttpClientErrorException.NotFound.class)
	public void notFound( ) {}
}
