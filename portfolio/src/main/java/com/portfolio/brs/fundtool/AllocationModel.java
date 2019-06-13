package com.portfolio.brs.fundtool;

import java.util.List;

/*
 * @author B Stanley
 *
 * This class supports the concept and metadata of allocation units associated
 * with the fund allocations for each of the portfolio models. The current
 * allocation models fall under 3 categories: Equity, Bond and Cash. Allocation
 * percents are determined based on an investors age and time to retirement.
 *
 * So each Portfolio Model will have a list of FundModels to represent the
 * allocation % to support that specific Portfolio model
 * e.g. Portfolio Model = Income: Equity 50%, Bond 30%, Cash 20%
 */
public class AllocationModel {

    //Aggressive Growth, Growth, Income, Retirement
    private String modelName;

    //Equity %, Bond %, Cash %
    private List<FundModel> fundModels;

    AllocationModel(String name, List<FundModel> modelList) {
        modelName = name;
        fundModels = modelList; // may want to copy this to prevent possible data modification.
    }

    public String getModelName() {
        return modelName;
    }

    List<FundModel> getFundList() {
        return fundModels;
    }
}
