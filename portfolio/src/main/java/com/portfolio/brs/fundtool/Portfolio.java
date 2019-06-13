package com.portfolio.brs.fundtool;

import java.util.ArrayList;
import java.util.List;

import com.portfolio.brs.fundtool.ModelBuilder.ModelPortfolio;

/**
 * @author B. Stanley
 * <p>
 * This class is the container for the portfolio, customers and their
 * allocation model. The available models supported by this class are
 * specified by an enumerator in the ModelPortfolio class.
 * Add a comment to see if it gets recognized as modified.
 */
class Portfolio {

    private ModelPortfolio portfolioName;
    private List<PortfolioMember> shareHolders;

    Portfolio(ModelPortfolio name) {
        this.portfolioName = name;
        shareHolders = new ArrayList<>();
    }

    // Maintain the list of shareholders associated with the portfolio
    void addShareHolder(Customer cust, AllocationModel allocMod) {
        shareHolders.add(new PortfolioMember(cust, allocMod));
    }

    /**
     * Each member of the portfolio will be added to the shareHolder's list.
     * This nested class provides the association so their fund allocations
     * can be calculated.
     */
    public class PortfolioMember {
        private Customer customer;
        private AllocationModel allocModel;

        PortfolioMember(Customer cust, AllocationModel allocMod) {
            customer = cust;
            allocModel = allocMod;
        }
    }

    /**
     * Display the Portfolio information
     */
    void displayPortfolio() {

        for (PortfolioMember pm : shareHolders) {

            System.out.println(" Customer Name: " + pm.customer.getLastName() + ", " + pm.customer.getFirstName() +
                    "|" + " Portfolio Name: ");
        }
    }

}
