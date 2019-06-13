package com.portfolio.brs.fundtool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author B Stanley
 *
 * Model Builder performs the following functions
 * 1. Assigns a Customer to their respective PortfolioModel (AggressiveGrowth, Growth, etc.)
 * 2. Calculates the allocation amounts for each fund based on the portfolio model and total assets
 * 3. Populates the Portfolio shareholder according to the assigned model (Growth, Income, etc.)
 */
public class ModelBuilder implements PortfolioModel {

    public void assignPortfolioModel(List<Customer> customerList) {

        if (!(customerList == null)) {
            for (Customer cust : customerList) {
                performModelAssignment(cust);
            }
        }
    }

    /*
     * performModelAssignment
     * @param Customer - contains the key customer information, name, dob, assets
     * @return ModelPortfolio identifies investment strategy based on customer's age
     * Assign the customer to the proper portfolio model based on their age.
     */
    ModelPortfolio performModelAssignment(Customer customer) {

        ModelPortfolio model = ModelPortfolio.INVALIDPORTFOLIO;
        int customerAge = customer.calculateAge();

        // Make sure the age value makes some kind of sense
        if (customerAge >= 0 && customerAge < 120) {

            // Set the Customer Model Name based on their age. Used chained ternary operator
            model = customerAge >= 0 && customerAge <= 40 ? ModelPortfolio.AGGRESSIVEGROWTH :
                    customerAge >= 41 && customerAge <= 55 ? ModelPortfolio.GROWTH : customerAge > 55 && customerAge < 66 ? ModelPortfolio.INCOME : ModelPortfolio.RETIREMENT;
        }

        // Return a potentially invalid portfolio. This could happen if an
        // invalid date date of birth is presented for the customer

        return model;
    }

    /*
     * allocatePortfoliotoModel
     * @param Customer - contains the key customer information, name, dob, assets
     * @param AllocationModel identifies allocation % for each investment option
     * @param ModelPortfolio the portfolio model for which allocations are applied
     *
     * Calculate Customer Asset allocations based on their Model Portfolio
     * assignment
     */
    public void allocatePortfoliotoModel(Customer cust, AllocationModel allocModel, ModelPortfolio portfolioModel) {
        // Extract the total asset from the customer and allocate the percentage according to the fundModel
        BigDecimal totalAssets = BigDecimal.valueOf(cust.getTotalAssets());

        // Initialize prior to assignment
        double runningTotal = 0.0;

        if (portfolioModel != ModelPortfolio.INVALIDPORTFOLIO) {
            cust.setModel(portfolioModel);
            // for each allocation model, allocate the appropriate amount of funds for each customer's portfolio
            for (FundModel fm : allocModel.getFundList()) {

                fm.setAllocationAmount(totalAssets.doubleValue());
                runningTotal += fm.getAllocationAmount();

                System.out.println("Total Amount Allocated = " + runningTotal + " Current Percentag= " + fm.getAllocationPercent() +
                        " Amount Allocated " + fm.getAllocationAmount());
            }
        } else {
            System.out.println("Cannot allocate funds for " + cust.getLastName() + "Due to invalid portfolio model assignment.");
        }

    }

    /*
     * AssignPortfolioShareholders
     *
     * 1. Iterate through the customer list
     * 2. Identify the proper portfolio alignment for the customer
     * 3. Add the customer to the Portfolio's list of Shareholders
     *
     * @param cust
     * @param models
     */
    public void assignPortfolioShareholders(Customer cust, Map<ModelPortfolio, Portfolio> portMap,
                                            Map<ModelPortfolio, AllocationModel> allocMap, ModelPortfolio portfolioModel) {

        if (portfolioModel == ModelPortfolio.AGGRESSIVEGROWTH) {
            portMap.get(ModelPortfolio.AGGRESSIVEGROWTH).addShareHolder(cust, allocMap.get(ModelPortfolio.AGGRESSIVEGROWTH));
            //  portMap.get(ModelPortfolio.AGGRESSIVEGROWTH).displayPortfolio();
        } else if (portfolioModel == ModelPortfolio.GROWTH) {
            portMap.get(ModelPortfolio.GROWTH).addShareHolder(cust, allocMap.get(ModelPortfolio.GROWTH));
            //portMap.get(ModelPortfolio.GROWTH).displayPortfolio();
        } else if (portfolioModel == ModelPortfolio.INCOME) {
            portMap.get(ModelPortfolio.INCOME).addShareHolder(cust, allocMap.get(ModelPortfolio.INCOME));
            //portMap.get(ModelPortfolio.INCOME).displayPortfolio();
        } else if (portfolioModel == ModelPortfolio.RETIREMENT) {
            portMap.get(ModelPortfolio.RETIREMENT).addShareHolder(cust, allocMap.get(ModelPortfolio.RETIREMENT));
            //portMap.get(ModelPortfolio.RETIREMENT).displayPortfolio();
        } else if (portfolioModel == ModelPortfolio.INVALIDPORTFOLIO) {
            System.out.println("Customer " + cust.getLastName() + " Cannot be assigned to a portfolio");
        }
    }

    /***************************************************************************
     * prepareAllocationModeMap
     *
     * The map in the method below will be used to allocate customer funds
     * according to their model portfolio. Their total assets will be allocated
     * to each fund based on their age.
     ***************************************************************************/
    // A temporary hack, should be read from file or DB, or configured through Spring
    public Map<ModelPortfolio, AllocationModel> prepareAllocationModelMap() {

        // Iterate through this instead of
        //Needs to be Spring Configured!
        List<FundModel> agrgrwthFundModelList = new ArrayList<>();
        List<FundModel> grwthFundModelList = new ArrayList<>();
        List<FundModel> incomeFundModelList = new ArrayList<>();
        List<FundModel> retirementFundModelList = new ArrayList<>();

        HashMap<ModelPortfolio, AllocationModel> allocMap = new HashMap<>();

        // AggressiveGrowth
        agrgrwthFundModelList.add(new FundModel(FundType.EQUITY.toString(), .8));
        agrgrwthFundModelList.add(new FundModel(FundType.BOND.toString(), .1));
        agrgrwthFundModelList.add(new FundModel(FundType.CASH.toString(), .1));

        //ModelPortfolio.AGGRESSIVEGROWTH, GROWTH, INCOME, RETIREMENT, INVALIDPORTFOLIO}
        allocMap.put(ModelPortfolio.AGGRESSIVEGROWTH, new AllocationModel(ModelPortfolio.AGGRESSIVEGROWTH.toString(), agrgrwthFundModelList));

        //Growth
        grwthFundModelList.add(new FundModel(FundType.EQUITY.toString(), .7));
        grwthFundModelList.add(new FundModel(FundType.BOND.toString(), .2));
        grwthFundModelList.add(new FundModel(FundType.CASH.toString(), .1));

        allocMap.put(ModelPortfolio.GROWTH, new AllocationModel(ModelPortfolio.GROWTH.toString(), grwthFundModelList));

        //Income
        incomeFundModelList.add(new FundModel(FundType.EQUITY.toString(), .50));
        incomeFundModelList.add(new FundModel(FundType.BOND.toString(), .3));
        incomeFundModelList.add(new FundModel(FundType.CASH.toString(), .2));

        allocMap.put(ModelPortfolio.INCOME, new AllocationModel(ModelPortfolio.INCOME.toString(), incomeFundModelList));

        //Income
        retirementFundModelList.add(new FundModel(FundType.EQUITY.toString(), .50));
        retirementFundModelList.add(new FundModel(FundType.BOND.toString(), .3));
        retirementFundModelList.add(new FundModel(FundType.CASH.toString(), .2));

        allocMap.put(ModelPortfolio.RETIREMENT, new AllocationModel(ModelPortfolio.RETIREMENT.toString(), retirementFundModelList));

        // TODO return HashMap
        return allocMap;
    }

    // Available Portfolio Models - should be configurable
    protected enum ModelPortfolio {AGGRESSIVEGROWTH, GROWTH, INCOME, RETIREMENT, INVALIDPORTFOLIO}

    // Available Portfolio Models - should be configurable
    protected enum FundType {EQUITY, BOND, CASH}
}