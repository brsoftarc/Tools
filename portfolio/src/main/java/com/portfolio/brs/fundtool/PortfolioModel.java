package com.portfolio.brs.fundtool;

import java.util.List;

/**
 * @author B Stanley
 * A simple interface to support portfolio /customer assignment.
 */

public interface PortfolioModel {
    void assignPortfolioModel(List<Customer> customerList);
}
