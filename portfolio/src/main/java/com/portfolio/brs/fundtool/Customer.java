package com.portfolio.brs.fundtool;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class Customer {

    private String firstName;
    private String lastName;
    private String dateOfBirth;    //use Calendar or whatever is appropriate eventually
    private BigDecimal totalAssets;    // This number will be allocated by % to the appropriate portfolio based on age
    private boolean dobIsValid;
    private ModelBuilder.ModelPortfolio model; // Maybe move out of Modeler, but keep for now

    /**
     * Customer Constructor
     * Ensure the DOB is in the following format "yyy-mm-dd"
     */
    // Determine whether or not defensive copies need to be made
    public Customer(String first, String last, String dob, double assets) {
        this.firstName = first;
        this.lastName = last;

        this.totalAssets = BigDecimal.valueOf(assets);
        this.dateOfBirth = dob;
        this.model = ModelBuilder.ModelPortfolio.INVALIDPORTFOLIO;

    }

    /**
     * In an attempt to calculate age, but preserve key customer content for
     * further processing in the event an invalid DOB is provided, the
     * isDateofBirthValid flag will be set to false if the date string sent
     * in for DOB cannot be parsed.
     */
    int calculateAge() {
        LocalDate dob = null;
        dobIsValid = true;
        try {
            // DOB string format supported for the LocalDate.parse method is "1988-03-03"
            dob = LocalDate.parse(dateOfBirth);
        } catch (DateTimeParseException e) {
            // log this issue, but retain the customer. So the issue can
            // be resolved
            dobIsValid = false;
        }

        // perform the age calculation if the dateOfBirth is not null.
        if ((dobIsValid && dob != null)) {
            return Period.between(dob, LocalDate.now()).getYears();
        } else {
            return -1;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getTotalAssets() {
        return totalAssets.doubleValue();
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getModel() {
        return model.toString();
    }

    public void setModel(ModelBuilder.ModelPortfolio model) {
        this.model = model;
    }
}
