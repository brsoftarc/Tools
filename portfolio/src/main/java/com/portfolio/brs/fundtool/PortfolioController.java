package com.portfolio.brs.fundtool;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class PortfolioController {

    @Autowired
    private PortfolioBuilder portBuilder;

    @RequestMapping("/portfolioList")
    //Create a list of Customers
    public List<Customer> presentCustomerList(@RequestParam Map<String, String> requestParams) {

        return portBuilder.buildCustomerPortfolioList();
    }

    // Creates a customer, validates their data and assigns a portfolio
    @RequestMapping("/portfolio")
    public Customer presentCustomer(@RequestParam Map<String, String> requestParams) {
        String first = requestParams.get("first");
        String last = requestParams.get("last");
        String dob = requestParams.get("dob");
        double assets = Double.parseDouble(requestParams.get("assets"));

        return portBuilder.buildCustomerPortfolio(first, last, dob, assets);
    }

    // Creates a Customer from data that is posted from a (currently hard coded) web form
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/newCustomer")
    @ResponseBody
    public Customer createCustomer(@RequestBody Customer cust) {
        return portBuilder.buildCustomerPortfolio(cust.getFirstName(), cust.getLastName(), cust.getDateOfBirth(), cust.getTotalAssets());

    }

}
