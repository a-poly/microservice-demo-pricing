package com.example;

import java.math.BigDecimal;

public class Item {

	private String code;
	private BigDecimal price;
	private BigDecimal tax;
	

	
	public Item() {
		super();
	}


	public Item(String code, BigDecimal price, BigDecimal tax) {
		this();
		this.code = code;
		this.price = price;
		this.tax = tax;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
}