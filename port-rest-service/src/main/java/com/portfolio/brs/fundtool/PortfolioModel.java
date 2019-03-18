package com.portfolio.brs.fundtool;
import java.util.List;

/******************************************************************************
 * 
 * @author B Stanley
 * A simple interface to support portfolio /customer assignment.
 */

public interface PortfolioModel {

	//public void assignPortfolioModel(List<?> customerList);
	public void updatePortfolioModel(List<?> Portfolio);
	void assignPortfolioModel(List<Customer> customerList);

}
