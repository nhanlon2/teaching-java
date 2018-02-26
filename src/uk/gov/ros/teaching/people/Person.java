package uk.gov.ros.teaching.people;

import java.util.Date;

/*
 * The class has been made public deliberately to expose flaws in its design - it should actually be package scoped
 * thus forcing client code to depend on the interface (IPerson) which this package intends for client use. By exposing
 * this class we allow client code to depend on it and any implementation details which it leaks - we can no longer
 * be sure that only the methods on the IPerson interface are being used by client code - look at the Util class to see this
 * in action.
 */
public class Person extends PersonWithConfidentialAge implements IPerson{
	
	//###########Using final ###########
	private final Date createdOn;
	
	//###########Initialisation blocks ###########
	// we could have used an initialisation block to set up createdOn. This would remove responsibility for doing so from the constructors.
	// However - it would now not be possible to use createdOn in the copy constructor - final fields can only be initialised once!
//	{
//		createdOn = new Date();
//	}
	public Person(String name, int age){
		super(name,age);
		this.createdOn = new Date();
	}
	
	//########### Copy Constructor - really conversion constructor###########
	//actually this is a conversion constructor since it transforms parent instance to child instance
	public Person(PersonWithConfidentialAge ageToBeRealed){
		//protected (and in this case package) scoped members of the parent class are directly visible
		this(ageToBeRealed.getName(),ageToBeRealed.age);
	}
	
	//########### Copy Constructor reading private fields of same class###########
	public Person(Person anOther){
		super(anOther.getName(),anOther.age);
		//private scoped members of this class are visible directly
		this.createdOn = anOther.createdOn;
	}
	
	//########### Overriding Equals() and Hashcode() ###########
	
	//Note this violates symmetry and transitivity with the parent class - can you see why?
	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		Person anOther = null;
		if (obj instanceof Person){
			anOther = (Person) obj;
			return super.equals(obj) && createdOn.equals(anOther.createdOn);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + createdOn.hashCode();
	}
	
	@Override
	public String toString() {//inherited from Object
		return print();// inherited from IPerson
	}

	@Override //we reveal age now to the public, with a small adjustment....
	public int getAge() {// implementing IPerson
		return age>30?age-10:age;// age instance variable inherited from PersonWithConfidentialAge
	}

	@Override
	public Date getCreatedOn() {
		return createdOn;
	}
	
}
