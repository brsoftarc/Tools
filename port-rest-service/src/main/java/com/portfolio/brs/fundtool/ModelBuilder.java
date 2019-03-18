package com.portfolio.brs.fundtool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************************************
 * @author B Stanley
 * 
 * Model Builder performs the following functions
 * 1. Assigns a Customer to their respective PortfolioModel (AggressiveGrowth, Growth, etc.)
 * 2. Calculates the allocation amounts for each fund based on the portfolio model and total assets
 * 3. Populates the Portfolio shareholder according to the assigned model (Growth, Income, etc.) 
 */
public class ModelBuilder implements PortfolioModel {

	@Override
	public void assignPortfolioModel(List<Customer> customerList) {
		// TODO Auto-generated method stub
		if (!(customerList==null)) {
			for(Customer cust : customerList){
				performModelAssignment(cust);
			}
		}
	}

	@Override
	public void updatePortfolioModel(List<?> Portfolio) {
		// TODO Auto-generated method stub

	}

	/**************************************************************************
	 * performModelAssignment
	 * @param cust	
	 * Assign the customer to the proper portfolio model based on their age.
	 **************************************************************************/
	public  ModelPortfolio performModelAssignment(Customer customer) {

		ModelPortfolio model = ModelPortfolio.INVALIDPORTFOLIO;		

		// Make sure the age value makes some kind of sense
		if (customer.getAge() >=0 && customer.getAge() < 120) {

			// Set the Customer Model Name based on their age. Used chained ternary operator
			customer.model = customer.getAge() >=0 & customer.getAge() <= 40? ModelPortfolio.AGGRESSIVEGROWTH : 
				customer.getAge() >= 41 && customer.getAge() <= 55 ? ModelPortfolio.GROWTH : customer.getAge() > 55 && customer.getAge() < 66 ? ModelPortfolio.INCOME : ModelPortfolio.RETIREMENT;
		}

		// Return a potentially invalid portfolio. This could happen if an 
		// invalid date date of birth is presented for the customer

		return model;
	}

	/**************************************************************************
	 * allocatePortfoliotoModel
	 * @param cust
	 * @param allocModel
	 * 
	 * Calculate Customer Asset allocations based on their Model Portfolio 
	 * assignment
	 **************************************************************************/
	public void allocatePortfoliotoModel(Customer cust, AllocationModel allocModel)
	{
		// Extract the total assest from the customer and allocate the percentage according to the fundModel
		BigDecimal totalAssets = BigDecimal.valueOf(cust.getAssets());

		// Initialize prior to assignment
		double runningTotal = 0.0;

		if (cust.model != ModelPortfolio.INVALIDPORTFOLIO) {
			// for each allocation model, allocate the appropriate amount of funds for each customer's portfolio
			for (FundModel fm : allocModel.getFundList()) {

				fm.setAllocationAmount(totalAssets.doubleValue());
				runningTotal += fm.getAllocationAmount();

				System.out.println("Total Amount Allocated = " + runningTotal + " Current Percentag= "  + fm.getAllocationPercent() +
						" Amount Allocated " + fm.getAllocationAmount());
			}
		}
		else {
			System.out.println("Cannot allocate funds for " + cust.getLastName() + "Due to invalid portfolio model assignment.");
		}

	}

	/************************************************************************** 
	 * AssignPortfolioShareholders
	 * 
	 * 1. Iterate through the customer list
	 * 2. Identify the proper portfolio alignment for the customer
	 * 3. Add the customer to the Portfolio's list of Shareholders
	 *  
	 * @param cust
	 * @param models
	 **************************************************************************/
	public void assignPortfolioShareholders(Customer cust, Map<ModelPortfolio,Portfolio> portMap, 
			Map<ModelPortfolio,AllocationModel> allocMap)
	{

		if (cust.model ==  ModelPortfolio.AGGRESSIVEGROWTH)
		{ 
			portMap.get(ModelPortfolio.AGGRESSIVEGROWTH).addShareHolder(cust, allocMap.get(ModelPortfolio.AGGRESSIVEGROWTH));
			//  portMap.get(ModelPortfolio.AGGRESSIVEGROWTH).displayPortfolio();
		}
		else if (cust.model ==  ModelPortfolio.GROWTH)
		{ 
			portMap.get(ModelPortfolio.GROWTH).addShareHolder(cust, allocMap.get(ModelPortfolio.GROWTH));
			//portMap.get(ModelPortfolio.GROWTH).displayPortfolio(); 
		}
		else if (cust.model ==  ModelPortfolio.INCOME)
		{ 
			portMap.get(ModelPortfolio.INCOME).addShareHolder(cust, allocMap.get(ModelPortfolio.INCOME));
			//portMap.get(ModelPortfolio.INCOME).displayPortfolio(); 
		}
		else if (cust.model ==  ModelPortfolio.RETIREMENT)
		{ 
			portMap.get(ModelPortfolio.RETIREMENT).addShareHolder(cust, allocMap.get(ModelPortfolio.RETIREMENT));
			//portMap.get(ModelPortfolio.RETIREMENT).displayPortfolio();
		}
		else if (cust.model ==  ModelPortfolio.INVALIDPORTFOLIO)
		{ 
			System.out.println("Customer " + cust.getLastName() + " Cannot be assigned to a portfolio" ); 
		}
	}

	/***************************************************************************
	 * prepareAllocationModeMap
	 * 
	 * The map in the method below will be used to allocate customer funds 
	 * according to their model portfolio. 
	 * A temporary hack, this should be read from a file or DB, or configured 
	 * through Spring
	 ***************************************************************************/

	public Map<ModelPortfolio,AllocationModel> prepareAllocationModeMap(){

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

	// Available Portfolio Models - should be configurable
	protected static enum ModelPortfolio {AGGRESSIVEGROWTH, GROWTH, INCOME, RETIREMENT, INVALIDPORTFOLIO}

	// Available Portfolio Models - should be configurable
	protected static enum FundType {EQUITY,BOND,CASH}

}
