package uk.gov.ros.teaching.people;
/*
 * Instances of this class should not expose any age related information to client code outside the package
 * The class has been made public deliberately to expose flaws in its design - it should actually be package scoped
 * as it does not implement the interface (IPerson) which this package intends for client use
 */
public class PersonWithConfidentialAge {
	int age;
	private String name;
	
	public PersonWithConfidentialAge(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}
	
	//This class is public - client code outside of the package can therefore extend it.
	//For this reason IT IS NOT SAFE to expose age, using protected scope. 
	//A child class can inherit the method and then widen accessibility
	protected int getAgeInConfidence() {
		return age;
	}
	
}
