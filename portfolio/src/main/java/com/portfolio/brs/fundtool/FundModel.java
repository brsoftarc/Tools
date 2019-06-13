package com.portfolio.brs.fundtool;

import java.math.BigDecimal;

/**
 * @author B Stanley
 * <p>
 * This class contains the individual allocations to support the Equity, Bond
 * and Cash Allocations for each Model: AggressiveGrowth, Growth, Income, Retirement
 */
class FundModel {
    private String fundCategory;    // Equity, Bond, Cash
    private double allocationPercentage;
    private BigDecimal allocationAmount;

    //protected FundModel(String name, String type, double percent)
    FundModel(String name, double percent) {
        fundCategory = name;

        // Need to make sure it does not exceed 100 for all allocations
        allocationPercentage = percent;
        allocationAmount = BigDecimal.ZERO;
    }

    double getAllocationPercent() {
        return allocationPercentage;
    }

    double getAllocationAmount() {
        return allocationAmount.doubleValue();
    }

    /**
     * Calculate the amount to allocate to each of the funds according
     * to the allocation percentage for each of the asset classes.
     * <p>
     * Used BigDecimal to perform the calculation in order to avoid
     * potential rounding errors.
     *
     * @param amount set the amount to be allocated for the specific investment.
     */

    void setAllocationAmount(double amount) {
        BigDecimal bdAmount = new BigDecimal(amount);
        BigDecimal allocPercent = new BigDecimal(allocationPercentage);
        allocationAmount = bdAmount.multiply(allocPercent);
    }

}
