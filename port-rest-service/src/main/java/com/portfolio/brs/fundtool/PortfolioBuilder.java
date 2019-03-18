package com.portfolio.brs.fundtool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.portfolio.brs.fundtool.ModelBuilder.FundType;
import com.portfolio.brs.fundtool.ModelBuilder.ModelPortfolio;

public class PortfolioBuilder {

	public PortfolioBuilder()
	{

	}

	/**************************************************************************
	 *  @author B Stanley
	 *  
	 *  Test the ModelBuilder and other supporting classes
	 *  
	 *  This class and the main can be used to test the classes in the fundtool 
	 *  package as a Java Application.
	 */
	public static void main(String args[]) {
		PortfolioBuilder myPortBuilder = new PortfolioBuilder();
		myPortBuilder.buildCustomerPortfolioList();	

		myPortBuilder.buildCustomerPortfolio("Brian", "Jones", "1944-02-12", 100000);
	}

	// Used to test the Post method
	public Customer buildCustomerPortfolio(String first, String last, String dob, double assets) {

		ModelPortfolio modelP = ModelPortfolio.INVALIDPORTFOLIO;	

		ModelBuilder myModelBuilder = new ModelBuilder();

		/**
		 * Create the customer record 
		 * 1. Capturing relevant Customer information : First, Last Name, DOB, 
		 * Total Assets and calculate their age
		 * 2. The customer will be used to evaluate their age so they can be 
		 * 	a. associated with the correct portfolio model  
		 * 	b. their asset allocation caqn be calculated 
		 */

		Customer customer = new Customer(first, last,dob,assets);

		/****************************************************************************
		 *  Iterate through the customer list and assign the models for the 
		 *  customers. 
		 *  
		 *  The method accepts a Customer list, but that method in turn calls a 
		 *  method that operates on a single customer. 
		 ***************************************************************************/

		myModelBuilder.performModelAssignment(customer);

		/**************************************************************************
		 * Create maps for Allocation Models and Portfolios
		 * The maps will be used to store the customer information along with the
		 ***************************************************************************/

		Map<ModelPortfolio,AllocationModel> allocMap;
		Map<ModelPortfolio, Portfolio> portMap = null;

		// Allocating the proper portfolio for the customer
		allocMap = myModelBuilder.prepareAllocationModeMap();

		/**
		 * Assign customer to their portfolio by obtaining a map of the portfolios 
		 * and allocation models. 
		 */
		portMap = preparePortfolioMap();	

		/**************************************************************************
		 * 1. Identify which Model the customer will be assigned to
		 * 2. Allocate the customer's assets according to the model
		 * 3. Assign customers to their appropriate portfolio's shareholder list
		 **************************************************************************/ 

		// Allocate the customer's funds based on their portfolio
		myModelBuilder.allocatePortfoliotoModel(customer,allocMap.get(customer.model)); 

		// Perform the customer, portfolio assignment
		myModelBuilder.assignPortfolioShareholders(customer, portMap, allocMap);

		return customer;

	} // End Main/buildPortfolio

	// Used to test the Get method returs a list of customers
	public List<Customer> buildCustomerPortfolioList() {

		//ModelPortfolio modelP = ModelPortfolio.INVALIDPORTFOLIO;	

		ModelBuilder myModelBuilder = new ModelBuilder();

		/**
		 * Create the customer record 
		 * 1. Capturing relevant Customer information : First, Last Name, DOB, 
		 * Total Assets and calculate their age
		 * 2. The customer will be used to evaluate their age so they can be 
		 * 	a. associated with the correct portfolio model  
		 * 	b. their asset allocation can be calculated 
		 */

		List<Customer> customers = prepareCustomerMap();
		//Customer customer = new Customer(first, last, dob, assets);

		/****************************************************************************
		 *  Iterate through the customer list and assign the models for the 
		 *  customers. 
		 *  
		 *  The method accepts a Customer list, but that method in turn calls a 
		 *  method that operates on a single customer. 
		 ***************************************************************************/

		myModelBuilder.assignPortfolioModel(customers);

		/**************************************************************************
		 * Create maps for Allocation Models and Portfolios
		 * The maps will be used to store the customer information along with the
		 ***************************************************************************/

		Map<ModelPortfolio,AllocationModel> allocMap;
		Map<ModelPortfolio, Portfolio> portMap = null;

		// Allocating the proper portfolio for the customer
		allocMap = myModelBuilder.prepareAllocationModeMap();

		/**
		 * Assign customers to their portfolios by obtaining a map of the portfolios 
		 * and allocation models. 
		 */
		portMap = preparePortfolioMap();	

		/**************************************************************************
		 * Iterate through the customer list and
		 * 1. Identify which Model the customer will be assigned to
		 * 2. Allocate the customer's assets according to the model
		 * 3. Assign customers to their appropriate portfolio's shareholder list
		 **************************************************************************/ 

		for(Customer cust : customers){			

			// Allocate the customer's funds based on their portfolio
			myModelBuilder.allocatePortfoliotoModel(cust,allocMap.get(cust.model)); 

			// Perform the customer, portfolio assignment
			myModelBuilder.assignPortfolioShareholders(cust, portMap, allocMap);

		} // end of customer processing

		return customers;

	} // End Main/buildPortfolio
	/**************************************************************************
	 * Methods below are Clutter - needs to be cleaned up, but added so code 
	 * could be validated. 
	 * 
	 * 1. Create a list of customers
	 * 2. Create a map of the allocation models
	 * 3. Create a map of portfolios for customers to be assigned
	 **************************************************************************/

	public Map dumpPortfolio(Map<ModelPortfolio, Portfolio> portMap)
	{
		for (ModelPortfolio mapKey : portMap.keySet())
		{
			Portfolio port = portMap.get(mapKey);
			port.displayPortfolio();

		}
		return portMap;

	}
	private List<Customer> prepareCustomerMap()
	{
		List<Customer> customers = new ArrayList<Customer>();

		//Add some customers
		customers.add(new Customer("Judy", "Simms", "1988-03-03",3000000.10));
		customers.add(new Customer("Hugo", "Als", "1953-06-12",10000.12));
		customers.add(new Customer("Sally", "Birch", "1997-04-12",1330));
		customers.add(new Customer("Wendy", "Jones", "7/12/19",300000));
		customers.add(new Customer("Malcolm", "Young", "1951-02-12",7500.43));
		customers.add(new Customer("Andy", "Rhoney", "1989-01-15",20000));
		customers.add(new Customer("Eric", "Loughlin", "1964-07-12",300000));	

		return customers;

	}

	// TODO Another situation where configuration will have to be added

	private List<Portfolio> preparePortfolios()
	{
		// Create a list to hold the portfolios so custoers can be assigned
		List<Portfolio> portfolioList = new ArrayList<Portfolio>(); 

		portfolioList.add(new Portfolio(ModelPortfolio.AGGRESSIVEGROWTH) );
		portfolioList.add(new Portfolio(ModelPortfolio.GROWTH) );
		portfolioList.add(new Portfolio(ModelPortfolio.INCOME) );
		portfolioList.add(new Portfolio(ModelPortfolio.RETIREMENT) );

		return portfolioList;

	}

	private Map<ModelPortfolio, Portfolio> preparePortfolioMap()
	{
		// Create a list to hold the portfolios so customers can be assigned
		Map<ModelPortfolio,Portfolio> portfolioMap = new HashMap<ModelPortfolio,Portfolio>(); 

		portfolioMap.put(ModelPortfolio.AGGRESSIVEGROWTH, new Portfolio(ModelPortfolio.AGGRESSIVEGROWTH) );
		portfolioMap.put(ModelPortfolio.GROWTH, new Portfolio(ModelPortfolio.GROWTH) );
		portfolioMap.put(ModelPortfolio.INCOME, new Portfolio(ModelPortfolio.INCOME) );
		portfolioMap.put(ModelPortfolio.RETIREMENT, new Portfolio(ModelPortfolio.RETIREMENT) );

		return portfolioMap;

	}

}