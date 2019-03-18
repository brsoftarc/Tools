package com.portfolio.brs.fundtool;

import java.util.List;
import java.util.Map;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PortfolioController {

    
	@GetMapping("/portfolioList")
    public List<Customer> presentCustomer(@RequestParam Map<String,String> requestParams) {
    	String first=requestParams.get("first");
    	String last=requestParams.get("last"); 	
    	String dob=requestParams.get("dob");

    
    	PortfolioBuilder pb = new PortfolioBuilder();
    	
    	return pb.buildCustomerPortfolioList();
    }

	// Need to submit a Post for this to work
	@PostMapping("/newCustomer")
	 public Customer createCustomer(@RequestParam Map<String,String> requestParams) {
	    	String first=requestParams.get("first");
	    	String last=requestParams.get("last"); 	
	    	String dob=requestParams.get("dob");
	    
	    	double assets=Double.parseDouble(requestParams.get("assets"));
	    
	    	PortfolioBuilder pb = new PortfolioBuilder();
	    	
	    	return pb.buildCustomerPortfolio(first,last,dob,assets);
	    }
}