# Demo Microservice Example:  Pricing Service

This is a simple web / REST app that returns information on Product Pricing when asked.  This service calls the product service, promotion service, and tax services.


The sales price of any product = (inventory price - promotional discount) + sales tax.
Inventory price is obtained from the product service by supplying the product code.  The promotion service will obtain any promotional discount to subtract.  The tax service is used to calculate the tax given the product code and working price.



This code also demonstrates:
- Basic usage of Spring Boot
- Spring Cloud is included, but not really used.
- Spring Cloud AWS is included, but not really used.
- Runs nicely on or off AWS.
- Actuator endpoints are present.
