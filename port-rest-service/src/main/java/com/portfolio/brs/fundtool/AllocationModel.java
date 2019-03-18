package com.portfolio.brs.fundtool;

import java.util.List;

/******************************************************************************
 * 
 * @author B Stanley
 *
 * This class supports the concept and metadata of allocation units associated 
 * with the fund allocations for each of the portfolio models. The current 
 * allocation models fall under 3 categories: Equity, Bond and Cash. Allocation 
 * percents are determined based on an investors age and time to retirement. 
 /*****************************************************************************/
public class AllocationModel {

	private String modelName;  //Aggressive Growth, Growth, Income, Retirement

	private List<FundModel> fundModels; 

	public AllocationModel(String name, List<FundModel> modelList)
	{
		modelName = name;
		fundModels = modelList; // may want to copy this to prevent possible data modification.
	}

	public String getModelName() {return modelName;} 
	public List<FundModel> getFundList() {return fundModels;}
}
