package uk.gov.ros.teaching.people;

import java.util.Date;

/*
 * The class has been made public deliberately to expose flaws in its design - it should actually be package scoped
 * thus forcing client code to depend on the interface (IPerson) which this package intends for client use. By exposing
 * this class we allow client code to depend on it and any implementation details which it leaks - we can no longer
 * be sure that only the methods on the IPerson interface are being used by client code
 */
public class Person extends PersonWithConfidentialAge implements IPerson{
	private Date createdOn;
	public Person(String name, int age){
		super(name,age);
		this.createdOn = new Date();
	}
	
	private Person(String name, int age, Date createdOn){
		super(name,age);
		this.createdOn = createdOn;
	}
	
	//########### Copy Constructor - really conversion constructor###########
	//actually this is a conversion constructor since it transforms parent instance to child instance
	public Person(PersonWithConfidentialAge ageToBeRealed){
		//protected (and in this case package) scoped members of the parent class are directly visible
		this(ageToBeRealed.getName(),ageToBeRealed.age);
	}
	
	//########### Copy Constructor reading private fields of same class###########
	public Person(Person anOther){
		//private scoped members of this class are visible directly
		this(anOther.getName(),anOther.age, anOther.createdOn);
	}
	

	@Override
	public String toString() {//inherited from Object
		return print();// inherited from IPerson
	}

	@Override
	public int getAge() {
		return age;// inherited from PersonWithConfidentialAge
	}
}
