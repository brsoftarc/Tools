package com.portfolio.brs.fundtool;

public class Greeting {
	
	private final long id;
	private final String first;
	private final String last; 
	private final String dateOfBirth;
	private final double assets;
	
	public Greeting(long id, String first, String last, String dob, double assets) {
		this.id = id;
		this.first = first;
		this.last=last;
		this.dateOfBirth = dob;
		this.assets = assets;
	}
	
//	public Greeting(long id,String content) {
//		this.id = id;
//		this.content = content;
//		
//		
//	}

	public long getId() {
		return id;

	}

	public String getFirst() {
		return first;

	}
	
	public String getLast() {
		return last;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public double getAssets() {
		return assets;
	}
	
	
}

