package com.portfolio.brs.fundtool;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

/******************************************************************************
 * 
 * @author B Stanley
 *
 ******************************************************************************/
public class Customer {

	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth; 	//use Calendar or whatever is appropriate eventually
	private int age; 				// This will be set based on date of birth, but do that after the rest of the pluming is working
	private BigDecimal totalAssets;	// This number will be allocated by % to the appropriate portfolio based on age
	private boolean isDateofBirthValid;
	protected ModelBuilder.ModelPortfolio model; // Maybe move out of Modeler, but keep for now

	// Determine whether or not defensive copies need to be made
	public Customer(String first, String last, String dob, double assets)
	{

		this.firstName = first;
		this.lastName = last;

		this.totalAssets = BigDecimal.valueOf(assets);
		this.model = ModelBuilder.ModelPortfolio.INVALIDPORTFOLIO;

		// calculate the persons age, perhaps somewhere else 
		this.age = calculateAge(dob);
		isDateofBirthValid = age > -1 ? true : false; 
	}


	// Probably should use BigDecimal when performing calculations for accuracy, but return double for ease of use when reading.
	public double getAssets() {return totalAssets.doubleValue();}

	/**************************************************************************
	 *  In an attempt to calculate age, but preserve key customer content for 
	 *  further processing in the event an invalid DOB is provided, the 
	 *  isDateofBirthValid flag will be set to false if the date string sent 
	 *  in for DOB cannot be parsed.
	 *  
	 *  Changed to protected so it can be called in the future with an 
	 *  updated DOB.
	 **************************************************************************/
	protected int calculateAge(String dob) {

		try 
		{
			// DOB string format supported for the LocalDate.parse method is "1988-03-03"
			dateOfBirth = LocalDate.parse(dob);
		}
		catch (DateTimeParseException e)
		{
			// HACK - log this issue, but retain the customer. So the issue can
			// be resolved
			dateOfBirth=null;

		}

		// perform the age calculation if the dateOfBirth is not null.
		if ((dateOfBirth != null)) {
			return Period.between(dateOfBirth, LocalDate.now()).getYears();
		} else {
			return -1;
		}
	}


	// Convenience method for class users.
	public int getAge() {return this.age;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public boolean isDateofBirthValid() {return isDateofBirthValid;}
	public LocalDate getDateOfBirth() {	return dateOfBirth;}
	public BigDecimal getTotalAssets() {return totalAssets;}
	public ModelBuilder.ModelPortfolio getModel() {return model;}

}
