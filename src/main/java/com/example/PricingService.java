package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingService {

	@Value("${product.service.url}") String productServiceUrl;
	@Value("${promotion.service.url}") String promotionServiceUrl;
	@Value("${tax.service.url}") String taxServiceUrl;
	
	RestTemplate productService = new RestTemplate();
	RestTemplate promotionService = new RestTemplate();
	RestTemplate taxService = new RestTemplate();
	
	/**
	 * Calculate the sales price of an individual item.  
	 * 
	 * The sales price of any product = (list price - promotional discount) + sales tax. 
	 * List price is obtained from the product service by supplying the product code. 
	 * The promotion service will obtain any promotional discount to subtract. 
	 * The tax service is used to calculate the tax given the product code and working price.
	 */
	public Item calculatePricing(String code) throws Exception {
		
		//	TODO: These lookups can be done in parallel:
		BigDecimal price = lookupInventoryPrice(code);
		BigDecimal discount = lookupDiscount(code);
		
		price = price.subtract(discount);
		
		//	Get Tax and add:
		BigDecimal tax = lookupTax(code, price);

		return new Item(code,price,tax);
	}

	
	/**
	 * Obtain the product's list price from the product service.
	 */
	public BigDecimal lookupInventoryPrice(String code) {
		Item item = productService.getForObject(productServiceUrl + "/products/{code}", Item.class, code);
		return item.getPrice();
	}

	
	/**
	 * Some items get discounts from time to time.  Check Promotion service.
	 * @return the $ amount of discount to subtract from whatever the normal list price.
	 */
	public BigDecimal lookupDiscount(String code) {
		Promotion promotion = promotionService.getForObject(promotionServiceUrl + "/promotions/{code}", Promotion.class, code);
		if ( promotion.getPriceDiscount() == null ) promotion.setPriceDiscount(new BigDecimal(0));
		return promotion.getPriceDiscount();
	}

	
	/**
	 * Calculate tax for an individual item
	 * @throws URISyntaxException 
	 */
	public BigDecimal lookupTax(String code,BigDecimal price) throws URISyntaxException  {
		
		Item item = new Item();
		item.setCode(code);
		item.setPrice(price);
		
		RequestEntity<Item> request = 
			new RequestEntity(item, HttpMethod.GET, new URI(promotionServiceUrl + "/promotions/" + code));
		
		ResponseEntity<Item> response = taxService.exchange(request,Item.class);
		
		item = response.getBody();
		return item.getTax();
	}

	
}
