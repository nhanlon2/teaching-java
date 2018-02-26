package uk.gov.ros.teaching.people;

import java.util.Date;

public class PeopleTestHarness {
	
	public static void main (String [] args){
		//########### Code to interfaces not to concrete classes ########### 
		IPerson p = new Person("Sue",2);
		System.out.println(p.toString());
		
		//########### Anonymous classes ########### 
		IPerson testPerson = new IPerson() {
			
			@Override
			public String getName() { // you have to provide implementations for all non-default methods on the interface
				return "anonymous...";
			}
			
			@Override
			public int getAge() {
				return 0;
			}

			@Override
			public Date getCreatedOn() {
				return new Date();
			}
		};
		
		//########### Copy Constructor ###########
		//We only want to expose IPerson to client code therefore we use the copy/convert constructor of Person
		// to achieve this.
		PersonWithConfidentialAge discretePerson = new PersonWithConfidentialAge("Dave", 53);
		IPerson anIPerson = new Person(discretePerson);
		System.out.println("IPerson has age of:"+anIPerson.getAge());
		//outside the package 'getAgeInConfidence' is not visible
		System.out.println("discretePerson has age of:"+discretePerson.getAgeInConfidence());
		
		//########### Overriding Equals() and Hashcode() - problem: our implementation is not transitive or symmetrical ###########
		
		//########### breaks symmetry ###########
		Person aPerson = new Person("Dave",53);
		System.out.println("Using inheritance A==B is:" + discretePerson.equals(aPerson));
		System.out.println("Using inheritance B==A is:" + aPerson.equals(discretePerson));
		
		//########### breaks transitivity ###########
		Person quickCopy = new Person(discretePerson);
		sleep();//advance the createdOn value
		Person slowCopy = new Person(discretePerson);
		//A==B
		System.out.println("QuickCopy and  discretePerson represent the same thing: "+discretePerson.equals(quickCopy));
		//A==C
		System.out.println("SlowCopy and  discretePerson  represent the same thing: "+discretePerson.equals(slowCopy));
		//but B!=C, they have a different date value
		System.out.println("QuickCopy and SlowCopy represent the same thing: "+quickCopy.equals(slowCopy));
		
		//########### using WellDesignedPerson ###########
		//########### symmetry ###########
		WellDesignedPerson wellPerson = new WellDesignedPerson(aPerson);
		System.out.println("Using composition A==B are both:" + discretePerson.equals(wellPerson));
		System.out.println("Using composition B==A are both:" + wellPerson.equals(discretePerson));
		WellDesignedPerson sameWellPerson = new WellDesignedPerson(aPerson);
		System.out.println("Using composition wellPerson and sameWellPerson represent the same thing is:" + wellPerson.equals(sameWellPerson));
		
		//########### transitivity ###########
		WellDesignedPerson wellPersonQuick = new WellDesignedPerson(quickCopy);
		WellDesignedPerson wellPersonSlow = new WellDesignedPerson(slowCopy);
		
		//A!=B
		System.out.println("WellQuickCopy and  discretePerson represent the same thing: "+discretePerson.equals(wellPersonQuick));
		//A!=C
		System.out.println("WellSlowCopy and  discretePerson  represent the same thing: "+discretePerson.equals(wellPersonSlow));
		//and B!=C, they have a different date value
		System.out.println("WellQuickCopy and WellSlowCopy represent the same thing: "+wellPersonQuick.equals(wellPersonSlow));
		
		//########### using the PersonWithBuilder class ###########
		PersonWithBuilder pwb = new PersonWithBuilder.PersonBuilder().setAge(10).setName("Dave").build();
		
	}
	
	private static void sleep() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {

		}

	}
	
	
}
