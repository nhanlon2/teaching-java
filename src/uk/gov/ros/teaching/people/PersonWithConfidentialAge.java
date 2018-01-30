package uk.gov.ros.teaching.people;

/*
 * Instances of this class should not expose any age related information to client code outside the package
 * The class has been deliberately flawed in its design - it should not have a protected scoped method that
 * does not implement the interface (IPerson) which this package intends for client use
 */
 class PersonWithConfidentialAge {
	int age;
	private String name;
	
	public PersonWithConfidentialAge(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}
	
	//This class is not public - but child classes might be - and client code outside of the package can extend child classes.
	//For this reason IT IS NOT SAFE to expose age, using protected scope. 
	//A child class can inherit the method and then widen accessibility. See the Util class.
	protected int getAgeInConfidence() {
		return age;
	}
	
	//########### Overriding Equals() and Hashcode() ###########
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		PersonWithConfidentialAge anOther = null;
		if (obj instanceof PersonWithConfidentialAge) {
			anOther = (PersonWithConfidentialAge) obj;
			return this.name.equals(anOther.name) && this.age == anOther.age;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		//two objects that are equals() MUST have the same hashcode
		//two objects that are not equals() can have the same hashcode, that is OK
		//for example: 'Dave' age 17 will have the same hashcode and 'Dave' age 34.
		//these two objects will not be equals but they will map to the same bucket in any HashMap used to store them.
		//Such hashing collisions, if kept rare, do not affect performance of HashMaps
		return name.hashCode() + age%17;
	}
	
}
