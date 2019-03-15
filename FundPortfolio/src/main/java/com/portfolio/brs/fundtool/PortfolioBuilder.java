package com.portfolio.brs.fundtool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.portfolio.brs.fundtool.ModelBuilder.ModelPortfolio;
import com.portfolio.brs.fundtool.ModelBuilder.FundType;

public class PortfolioBuilder {
	
	/**************************************************************************
	 *  @author B Stanley
	 *  
	 *  Test the ModelBuilder ansd other supporting classes
	 *  
	 *  This class and the main can be used to test the classes in the fundtool 
	 *  package as a Java Application.
	 */
	public static void main(String args[]) {
		
		ModelPortfolio modelP = ModelPortfolio.INVALIDPORTFOLIO;
		
		PortfolioBuilder myPortBuilder = new PortfolioBuilder();
		ModelBuilder myModelBuilder = new ModelBuilder();
		
		/**
		 * Build initial Customer list
		 * 1. Capturing relevant Customer information : First, Last Name, DOB, 
		 * Total Assets and calculate their age
		 * 
		 * 2. The customer list will be used to evaluate each customer to
		 * 	a. be associated with the correct portfolio model  
		 * 	b. calculate their asset allocation
		 * 	c. be assigned to the proper Portfolio  
		 */

		List<Customer> customers = myPortBuilder.prepareCustomerMap();
		
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
		allocMap = myPortBuilder.prepareAllocationModeMap();
		
		/**
		 * Assign customers to their portfolios by obtaining a map of the portfolios 
		 * and allocation models. 
		 */
		portMap = myPortBuilder.preparePortfolioMap();	
		
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
		
		// Finally let's dump out relevant portfolio information, provided 
		// Processing was successful.
		if (portMap != null) {
			for (ModelPortfolio mapKey : portMap.keySet())
			{
				Portfolio port = portMap.get(mapKey);
				port.displayPortfolio();
				
			}
		}
	} // End Main
	
	/**************************************************************************
	 * Methods below are Clutter - needs to be cleaned up, but added so code 
	 * could be validated. 
	 * 
	 * 1. Create a list of customers
	 * 2. Create a map of the allocation models
	 * 3. Create a map of portfolios for customers to be assigned
	 **************************************************************************/
	
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
	/***************************************************************************
	 * prepareAllocationModeMap
	 * 
	 * The map in the method below will be used to allocate customer funds 
	 * according to their model portfolio.
	 ***************************************************************************/
	 // A temporary hack, should be read from file or DB, or configured through Spring
		private Map<ModelPortfolio,AllocationModel> prepareAllocationModeMap(){
			
			// Iterate through this instead of 
			//Needs to be Spring Configured!
			List<FundModel> agrgrwthFundModelList = new ArrayList<FundModel>();
			List<FundModel> grwthFundModelList = new ArrayList<FundModel>();
			List<FundModel> incomeFundModelList = new ArrayList<FundModel>();
			List<FundModel> retirementFundModelList = new ArrayList<FundModel>();
			
			HashMap<ModelPortfolio,AllocationModel> allocMap = new HashMap<ModelPortfolio,AllocationModel>();
			
			// AggressiveGrowth
			agrgrwthFundModelList.add(new FundModel(FundType.EQUITY.toString(),.8));
			agrgrwthFundModelList.add(new FundModel(FundType.BOND.toString(),.1));
			agrgrwthFundModelList.add(new FundModel(FundType.CASH.toString(),.1));
			
			//ModelPortfolio.AGGRESSIVEGROWTH, GROWTH, INCOME, RETIREMENT, INVALIDPORTFOLIO}
			allocMap.put(ModelPortfolio.AGGRESSIVEGROWTH, new AllocationModel(ModelPortfolio.AGGRESSIVEGROWTH.toString(),agrgrwthFundModelList));
			
			//Growth
			grwthFundModelList.add(new FundModel(FundType.EQUITY.toString(),.7));
			grwthFundModelList.add(new FundModel(FundType.BOND.toString(),.2));
			grwthFundModelList.add(new FundModel(FundType.CASH.toString(),.1));
			
			allocMap.put(ModelPortfolio.GROWTH, new AllocationModel(ModelPortfolio.GROWTH.toString(),grwthFundModelList));
			
			//Income
			incomeFundModelList.add(new FundModel(FundType.EQUITY.toString(),.50));
			incomeFundModelList.add(new FundModel(FundType.BOND.toString(),.3));
			incomeFundModelList.add(new FundModel(FundType.CASH.toString(),.2));
			
			allocMap.put(ModelPortfolio.INCOME, new AllocationModel(ModelPortfolio.INCOME.toString(),incomeFundModelList));
			
			//Income
			retirementFundModelList.add(new FundModel(FundType.EQUITY.toString(),.50));
			retirementFundModelList.add(new FundModel(FundType.BOND.toString(),.3));
			retirementFundModelList.add(new FundModel(FundType.CASH.toString(),.2));
			
			allocMap.put(ModelPortfolio.RETIREMENT, new AllocationModel(ModelPortfolio.RETIREMENT.toString(),retirementFundModelList));
			
			// TODO return HashMap
			return allocMap;
			
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
