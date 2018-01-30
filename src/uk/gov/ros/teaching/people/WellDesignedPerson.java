package uk.gov.ros.teaching.people;

import java.util.Date;

//############# Use Composition over Inheritance ###############
class WellDesignedPerson implements IPerson {
	
	//We do not use the 'Person' class as it has a 'broken' equals method 
	//this contrived example attempts to show what a re-write of the Person class would look like
	//so imagine a class hierarchy where the Person class does not exist
	private PersonWithConfidentialAge person;
	private Date createdOn;

	public WellDesignedPerson(IPerson p) {//code to interfaces
		this.person = new PersonWithConfidentialAge(p.getName(),p.getAge());
		this.createdOn = p.getCreatedOn();
	}
	
	@Override
	public int getAge() {
		return person.getAgeInConfidence();
	}

	@Override
	public String getName() {
		return person.getName();
	}
	
	//########### Overriding Equals() and Hashcode() ###########
	//this respects symmetry and transitivity. 
	@Override
	public boolean equals(Object obj) {
		if(this==obj){
			return true;
		}
		if(obj instanceof WellDesignedPerson){
			WellDesignedPerson anOther = (WellDesignedPerson) obj;
			return createdOn.equals(anOther.createdOn) && person.equals(anOther.person);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return person.hashCode() + createdOn.hashCode();
	}

	@Override
	public Date getCreatedOn() {
		return createdOn;
	}

}
